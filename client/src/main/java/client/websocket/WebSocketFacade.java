package client.websocket;

import com.google.gson.Gson;
import exceptions.ResponseException;
import models.chess.ChessGame;
import models.chess.ChessMove;
import websocket.State;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.*;

import jakarta.websocket.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

    Session session;
    ServerMessageHandler serverMessageHandler;

    public WebSocketFacade(String url, ServerMessageHandler serverMessageHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.serverMessageHandler = serverMessageHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    ServerMessage result = switch (serverMessage.getServerMessageType()){
                        case NOTIFICATION -> new Gson().fromJson(message, NotificationMessage.class);
                        case LOAD_GAME -> new Gson().fromJson(message, LoadGameMessage.class);
                        case ERROR -> new Gson().fromJson(message, ErrorMessage.class);
                    };
                    serverMessageHandler.notify(result);
                }
            });

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    // Required by Endpoint — no implementation needed
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void makeConnection(String authToken, int gameID, State state) throws ResponseException {
        try {
            var action = new UserGameCommand(UserGameCommand.CommandType.CONNECT, authToken, gameID, state);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    public void makeMove(String authToken, int gameID, ChessMove move, State state) throws ResponseException {
        try {
            var action = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE, authToken, gameID, move, state);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    public void leaveGame(String authToken, int gameID, State state) throws ResponseException {
        try {
            var action = new UserGameCommand(UserGameCommand.CommandType.LEAVE, authToken, gameID, state);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    public void resignGame(String authToken, int gameID, State state) throws ResponseException {
        try {
            var action = new UserGameCommand(UserGameCommand.CommandType.RESIGN, authToken, gameID, state);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }


    public void loadGame(ChessGame game) throws ResponseException {
        try {
            var action = new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }


    public void rootError(String error) throws ResponseException {
        try{
            var action = new ErrorMessage(ServerMessage.ServerMessageType.ERROR, error);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex){
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }

    public void sendNotification(String notification) throws ResponseException {
        try{
            var action = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, notification);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex){
            throw new ResponseException(ResponseException.Code.ServerError, ex.getMessage());
        }
    }


}