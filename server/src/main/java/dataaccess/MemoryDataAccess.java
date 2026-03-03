package dataaccess;

import chess.*;
import java.util.HashMap;

public class MemoryDataAccess implements DataAccess {
    private int nextId = 1;
    final private HashMap<Integer, ChessGame> chessGames = new HashMap<>();

    public ChessGame newGame(ChessGame game){
        game = new ChessGame(nextId++, ....);

        chessGames.put(game.id(), game);
        return game;
    }

    public gameList listGames() {return new gameList(chessGames.values());}

    public ChessGame getGame(int id){return chessGames.get(id);}

    public void deleteGame(int id){chessGames.remove(id);}

    public void deleteAllGames(){chessGames.clear();}
}
