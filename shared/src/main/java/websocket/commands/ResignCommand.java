package websocket.commands;

import websocket.State;

public class ResignCommand extends UserGameCommand{
    public final State state;

    public ResignCommand(CommandType commandType, String authToken, Integer gameID, State state) {
        super(commandType, authToken, gameID, state);
        this.state = state;
    }
}
