package websocket.commands;

import websocket.State;

public class ConnectCommand extends UserGameCommand{
    public final State state;

    public ConnectCommand(CommandType commandType, String authToken, Integer gameID, State state) {
        super(commandType, authToken, gameID, state);
        this.state = state;
    }
}
