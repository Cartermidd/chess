package websocket.commands;

import chess.ChessMove;
import websocket.State;

public class MakeMoveCommand extends UserGameCommand{
    public final ChessMove move;

    public MakeMoveCommand(CommandType commandType, String authToken, Integer gameID, ChessMove move, State state) {
        super(commandType, authToken, gameID, state);
        this.move = move;
    }
}
