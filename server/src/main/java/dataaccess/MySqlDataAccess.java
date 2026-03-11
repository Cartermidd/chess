package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import models.AuthData;
import models.GameData;
import models.UserData;
import results.CreateGameResult;
import results.ListGamesResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlDataAccess implements GameDAO, UserDAO, AuthDAO {

    public MySqlDataAccess() throws DataAccessException {
        configureDatabase();
    }


    @Override
    public void create(AuthData authData) throws DataAccessException {
        var statement = "INSERT INTO authtokens (userName, authToken) VALUES (?, ?)";
        String json = new Gson().toJson(authData);
        int id = updateAuthTokens(statement, authData.userName(), authData.authToken(), json);
    }

    @Override
    public AuthData findByAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public CreateGameResult createGame(String name) throws DataAccessException {//I need to serialize game
        var statement = "INSERT INTO authtokens (userName, authToken) VALUES (?, ?)";
//        String json = new Gson().toJson(authData);
//        int id = updateAuthtokens(statement, authData.userName(), authData.authToken(), json);
        return new CreateGameResult(1);
    }

    @Override
    public ListGamesResult listGames() throws DataAccessException {//I need to deserialize games
        return null;
    }

    @Override
    public GameData findByID(Integer id) throws DataAccessException {//I need to deserialize game
        return null;
    }

    @Override
    public void updateGame(Integer id, ChessGame.TeamColor color, String username) throws DataAccessException {

    }

    @Override
    public void create(UserData user) throws DataAccessException {//I need to hash the password
        var statement = "INSERT INTO users (userName, password, email) VALUES (?, ?, ?)";
//        String json = new Gson().toJson(authData);
//        int id = updateAuthTokens(statement, authData.userName(), authData.authToken(), json);
    }

    @Override
    public UserData findByUsername(String username) throws DataAccessException {//I need to unhash the password
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }

// let's make an update function for each database type!
    private int updateAuthTokens(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    ps.setString(i + 1, param.toString());
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private int updateUsers(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
//                    else if (param instanceof PetType p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }

    private int updateGames(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
//                    else if (param instanceof PetType p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }


    private final String[] createAuthStatements = { //edit to auth table
            """
            CREATE TABLE IF NOT EXISTS  authtoken (
              `authtoken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`authtoken`),
              INDEX(username),
              INDEX(authtoken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private final String[] createUserStatements = { //edit to user table
            //password will be hashed idk if varchar(256) will work for that
            """
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`username`),
              INDEX(username),
              INDEX(password),
              INDEX(email)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private final String[] createGameStatements = { //edit to user table
            //password will be hashed idk if varchar(256) will work for that
            """
            CREATE TABLE IF NOT EXISTS  user (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteusername` varchar(256),
              `blackusername` varchar(256),
              `gamename` varchar(256) NOT NULL,
              `game` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`id`),
              INDEX(whiteusername),
              INDEX(blackusername),
              INDEX(gamename),
              INDEX(game)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureTable(String[] statements) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : statements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }


    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try{
            configureTable(createGameStatements);
            configureTable(createAuthStatements);
            configureTable(createUserStatements);
        } catch (DataAccessException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }


}
