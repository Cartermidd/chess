package websocket.messages;

import models.chess.ChessGame;

public class LoadGameMessage extends ServerMessage{
    ChessGame game;

    public LoadGameMessage(ServerMessageType type, ChessGame game) {
        super(type);
        this.game = game;
    }

    public ChessGame getGame(){
        return game;
    }
}
