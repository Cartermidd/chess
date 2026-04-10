package server.websocket;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import io.javalin.websocket.*;
import models.AuthData;
import models.GameData;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.InvalidMoveException;
import websocket.State;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;

public class WebSocketHandler implements WsConnectHandler, WsMessageHandler, WsCloseHandler {

    private final ConnectionManager connections = new ConnectionManager();
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public WebSocketHandler(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    @Override
    public void handleConnect(WsConnectContext ctx) {
        System.out.println("Websocket connected");
        ctx.enableAutomaticPings();
    }

    @Override
    public void handleMessage(WsMessageContext ctx) {
        try {
            UserGameCommand command = new Gson().fromJson(ctx.message(), UserGameCommand.class);
            switch (command.getCommandType()) {
                case CONNECT -> connect(ctx, command);
                case MAKE_MOVE -> {
                    MakeMoveCommand cmd = new Gson().fromJson(ctx.message(), MakeMoveCommand.class);
                    makeMove(ctx, cmd);
                }
                case RESIGN -> resign(ctx, command);
                case LEAVE -> disconnect(ctx, command);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void handleClose(WsCloseContext ctx) {
        System.out.println("Websocket closed");
    }


    private void connect(WsMessageContext ctx, UserGameCommand command) throws IOException {
        try {
            GameData gameData = gameDAO.findByID(command.getGameID());
            if (gameData == null){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Game not found")));
                return;
            }
            connections.add(gameData.gameID(), ctx);
            AuthData user = authDAO.findByAuth(command.getAuthToken());
            if (user == null){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Invalid auth token")));
                return;
            }
            ctx.send(new Gson().toJson(new LoadGameMessage(
                    ServerMessage.ServerMessageType.LOAD_GAME,
                    gameData.game()
            )));


            String role = roleString(determineRole(user.userName(), gameData));
            var message = String.format("%s has joined the game as %s", user.userName(), role);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(command.getGameID(), ctx, notification); // send notification
        } catch (DataAccessException ex){
            ctx.send(new Gson().toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Failed to connect: " + ex.getMessage())));
        }

    }

    private void disconnect(WsMessageContext ctx, UserGameCommand command) throws IOException{
        try{
            String username = authDAO.findByAuth(command.getAuthToken()).userName();
            GameData gameData = gameDAO.findByID(command.getGameID());
            State state = determineRole(username, gameData);
            String role = roleString(state);
            var message = String.format("%s (%s) has left the game", username, role);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(command.getGameID(), ctx, notification);
            if (role != "observer") {
                gameDAO.clearPlayer(command.getGameID(), stateToColor(state));
            }
            connections.remove(command.getGameID(), ctx);
        } catch (DataAccessException ex){
            throw new IOException("Unable to query database.");
        }
    }

    private void resign(WsMessageContext ctx, UserGameCommand command) throws IOException{
        try{
            String username = authDAO.findByAuth(command.getAuthToken()).userName();
            GameData gameData = gameDAO.findByID(command.getGameID());
            State state = determineRole(username, gameData);
            if (state == State.OBSERVER){
                ctx.send(new Gson().toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Observers can't resign")));
                return;
            }
            if (gameData.game().isGameOver()){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Game is over")));
                return;
            }

            var message = String.format("%s (%s) has resigned the game", username, state);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(command.getGameID(), null, notification);
            gameData.game().setGameOver();
            gameDAO.updateGame(command.getGameID(),gameData.game());
        } catch (DataAccessException ex){
            throw new IOException("Unable to query database.");
        }
    }

    private void makeMove(WsMessageContext ctx, MakeMoveCommand command) throws IOException {
        try {
            AuthData user = authDAO.findByAuth(command.getAuthToken());
            if (user == null){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Invalid auth token")));
                return;
            }

            GameData gameData = gameDAO.findByID(command.getGameID());
            if (gameData == null){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Game not found")));
                return;
            }
            if (gameData.game().isGameOver()){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Game is over")));
                return;
            }
            State role = determineRole(user.userName(), gameData);
            if (role == State.OBSERVER){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Observers cannot make moves")));
                return;
            }

            if (gameData.game().getTeamTurn() != stateToColor(role)){
                ctx.send(new Gson().toJson(new ErrorMessage(
                        ServerMessage.ServerMessageType.ERROR, "Not your turn")));
                return;
            }


            ChessGame game = gameData.game();

            try{
                game.makeMove(command.move);
            }catch (InvalidMoveException ex){
                ctx.send(new Gson().toJson( new ErrorMessage(ServerMessage.ServerMessageType.ERROR, ex.getMessage())));
                return;
            }
//comment for resubmit
            gameDAO.updateGame(command.getGameID(), game);

            connections.broadcast(command.getGameID(), null, new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game));

            String username = authDAO.findByAuth(command.getAuthToken()).userName();

            String move = parceMove(command.move);
            var message = String.format("%s (%s) moved %s", username, role, move);
            connections.broadcast(command.getGameID(), ctx, new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message));
            if (gameData.game().isInCheckmate(ChessGame.TeamColor.BLACK) | (gameData.game().isInCheckmate(ChessGame.TeamColor.WHITE))){
                if (gameData.game().isInCheckmate(ChessGame.TeamColor.BLACK)) {
                    var update = String.format("%s (black) is in checkmate",gameData.blackUsername());
                    connections.broadcast(command.getGameID(), null,
                            new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, update + "White wins!"));
                    gameData.game().setGameOver();
                    gameDAO.updateGame(command.getGameID(),gameData.game());
                } else if (gameData.game().isInCheckmate(ChessGame.TeamColor.WHITE)){
                    var update = String.format("%s (white) is in checkmate",gameData.whiteUsername());
                    connections.broadcast(command.getGameID(), null,
                            new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, update + "Black wins!"));
                    gameData.game().setGameOver();
                    gameDAO.updateGame(command.getGameID(),gameData.game());
                }
            } else if (gameData.game().isInCheck(ChessGame.TeamColor.BLACK)) {
                var update = String.format("%s (black) is in check",gameData.blackUsername());
                connections.broadcast(command.getGameID(), null, new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, update));
            } else if (gameData.game().isInCheck(ChessGame.TeamColor.WHITE)){
                var update = String.format("%s (white) is in check",gameData.whiteUsername());
                connections.broadcast(command.getGameID(), null, new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, update));
            } else if (gameData.game().isInStalemate(ChessGame.TeamColor.BLACK)) {
                connections.broadcast(command.getGameID(), null, new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, "Stalemate!"));
                gameData.game().setGameOver();
            }

        } catch (DataAccessException ex) {

            throw new IOException("Unable to query database.");
        }
    }

    private String roleString(State state){
        return switch (state){
            case BLACK -> "black";
            case WHITE -> "white";
            case OBSERVER -> "observer";
        };
    }

    private ChessGame.TeamColor stateToColor(State state){
        return switch (state){
            case BLACK -> ChessGame.TeamColor.BLACK;
            case WHITE -> ChessGame.TeamColor.WHITE;
            case OBSERVER -> null;
        };
    }

    private String pieceToString(ChessPiece.PieceType pieceType){
        return switch(pieceType){
            case QUEEN -> "queen";
            case KNIGHT -> "knight";
            case BISHOP -> "bishop";
            case ROOK -> "rook";
            default -> "what???";
        };
    }

    private String parceMove(ChessMove move){
        if (move.getPromotionPiece() == null){
            return String.format("from %s to %s", move.getStartPosition(), move.getEndPosition());
        } else {
            return String.format("from %s to %s, promoting to a %s", move.getStartPosition(),
                    move.getEndPosition(), pieceToString(move.getPromotionPiece()));
        }
    }

    private State determineRole(String username, GameData gameData){
        if (username.equals(gameData.whiteUsername())){return State.WHITE;}
        if (username.equals(gameData.blackUsername())){return State.BLACK;}
        return State.OBSERVER;
    }

}
