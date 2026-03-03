package results;

import models.GameData;

import java.util.Collection;

public class GetGamesResult {
    Collection<GameData> games;

    public GetGamesResult(Collection<GameData> games) {
        this.games = games;
    }

    public Collection<GameData> getGames() {
        return games;
    }

    public void setGames(Collection<GameData> games) {
        this.games = games;
    }
}
