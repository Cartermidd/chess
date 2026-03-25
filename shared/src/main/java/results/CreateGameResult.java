package results;

import requests.AuthTokenCarrier;

public class CreateGameResult extends AuthTokenCarrier {
    int gameID;

    public CreateGameResult(int gameID) {
        this.gameID = gameID;
    }

    public int getGameID() {
        return gameID;
    }
}
