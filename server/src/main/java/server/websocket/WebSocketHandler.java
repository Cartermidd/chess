package server.websocket;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import exceptions.ResponseException;
import io.javalin.websocket.*;
import models.AuthData;
import models.GameData;
import models.chess.ChessGame;
import models.chess.ChessMove;
import server.Server;
import server.ServerFacade;
import websocket.State;
import websocket.commands.*;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.rmi.RemoteException;

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
            connections.add(ctx.session);
            GameData gameData = gameDAO.findByID(command.getGameID());

            ctx.send(new Gson().toJson(new LoadGameMessage(
                    ServerMessage.ServerMessageType.LOAD_GAME,
                    gameData.game()
            )));

            String role = roleString(command.getState());
            AuthData user = authDAO.findByAuth(command.getAuthToken());
            var message = String.format("%s has joined the game as %s", user.userName(), role);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(ctx.session, notification); // send notification
        } catch (DataAccessException ex){
            ctx.send(new Gson().toJson(new ErrorMessage(ServerMessage.ServerMessageType.ERROR, "Failed to connect: " + ex.getMessage())));
        }

    }

    private void disconnect(WsMessageContext ctx, UserGameCommand command) throws IOException{
        try{
            String username = authDAO.findByAuth(command.getAuthToken()).userName();
            String role = roleString(command.getState());
            var message = String.format("%s (%s) has left the game", username, role);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(ctx.session, notification);
            if (command.getState() != State.OBSERVER) {
                gameDAO.clearPlayer(command.getGameID(), stateToColor(command.getState()));
            }
            connections.remove(ctx.session);
        } catch (DataAccessException ex){
            throw new IOException("Unable to query database.");
        }
    }

    private void resign(WsMessageContext ctx, UserGameCommand command) throws IOException{
        try{
            String username = authDAO.findByAuth(command.getAuthToken()).userName();
            String role = roleString(command.getState());
            var message = String.format("%s (%s) has resigned the game", username, role);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(ctx.session, notification);
        } catch (DataAccessException ex){
            throw new IOException("Unable to query database.");
        }
    }

    private void makeMove(WsMessageContext ctx, MakeMoveCommand command) throws IOException {
        try {
            GameData gameData = command.move;
             = gameDAO.findByID(command.getGameID());

            ctx.send(new Gson().toJson(new LoadGameMessage(
                    ServerMessage.ServerMessageType.LOAD_GAME,
                    gameData.game()
            )));



            String username = authDAO.findByAuth(command.getAuthToken()).userName();
            String role = roleString(command.getState());
            String move = command.move.toString();
            var message = String.format("%s (%s) moved %s", username, role, move);
            var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            connections.broadcast(null, notification);
        } catch (DataAccessException ex) {
            throw new IOException("Unable to query database.");
        }
    }

//    ws.sendNotification("\n" + formatCheck(String.format("%s (black) is in check", gameData.blackUsername())));
//        ws.sendNotification("\n" + formatCheck(String.format("%s (white) is in check", gameData.whiteUsername())));
//        ws.sendNotification("\n" + formatCheck(String.format("%s (black) has been checkmated", gameData.blackUsername()) + "\n" + String.format("%s wins!", gameData.whiteUsername())));
//         ws.sendNotification("\n" + formatCheck(String.format("%s (white) has been checkmated", gameData.whiteUsername()) + "\n" + String.format("%s wins!", gameData.blackUsername())));
//           ws.sendNotification("\n" + formatStalemate("Stalemate! No one wins!"));
//

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

}
