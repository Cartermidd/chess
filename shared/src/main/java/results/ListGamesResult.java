package results;

import models.GameData;

import java.util.Collection;

public class ListGamesResult {
    Collection<GameData> games;

    public ListGamesResult(Collection<GameData> games) {
        this.games = games;
    }

    public Collection<GameData> getGames() {
        return games;
    }

    public void setGames(Collection<GameData> games) {
        this.games = games;
    }
}
