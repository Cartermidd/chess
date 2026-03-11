package dataaccess;

import chess.ChessGame;
import models.AuthData;
import models.GameData;
import models.UserData;
import results.CreateGameResult;
import results.ListGamesResult;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDataAccess implements GameDAO, UserDAO, AuthDAO {

    public MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }



    @Override
    public void create(AuthData authData) throws DataAccessException {

    }

    @Override
    public AuthData findByAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public CreateGameResult createGame(String name) throws DataAccessException {
        return null;
    }

    @Override
    public ListGamesResult listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData findByID(Integer id) throws DataAccessException {
        return null;
    }

    @Override
    public void updateGame(Integer id, ChessGame.TeamColor color, String username) throws DataAccessException {

    }

    @Override
    public void create(UserData user) throws DataAccessException {

    }

    @Override
    public UserData findByUsername(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }



    private final String[] createStatements = { //edit to my table needs
            """
            CREATE TABLE IF NOT EXISTS  pet (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `type` ENUM('CAT', 'DOG', 'FISH', 'FROG', 'ROCK') DEFAULT 'CAT',
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(type),
              INDEX(name)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }


}
