package websocket.commands;

import websocket.State;

public class LeaveCommand extends UserGameCommand{
    public final State state;


    public LeaveCommand(CommandType commandType, String authToken, Integer gameID, State state) {
        super(commandType, authToken, gameID, state);
        this.state = state;
    }
}
