package Models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents the list of games data object
 */
public class GameList extends ArrayList<GameObject> {
    public GameList(){}

    public GameList(Collection<GameObject> games){super(games);}

    public String toString() {
        return new Gson().toJson(this.toArray());
    }

}
