package websocket.commands;

import models.chess.ChessMove;
import websocket.State;

public class MakeMoveCommand extends UserGameCommand{
    public final ChessMove move;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move, State state) {
        super(commandType, authToken, gameID, state);
        this.move = move;
    }
}
