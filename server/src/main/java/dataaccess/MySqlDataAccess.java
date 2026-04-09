package dataaccess;

import models.chess.ChessBoard;
import models.chess.ChessGame;
import models.AuthData;
import models.GameData;
import models.UserData;
import org.mindrot.jbcrypt.BCrypt;
import results.CreateGameResult;
import results.ListGamesResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlDataAccess implements GameDAO, UserDAO, AuthDAO {

    public MySqlDataAccess() {
        try {
        configureDatabase();
        } catch (Exception e){ throw new RuntimeException("SQL Database configuration error");}
    }

    //AuthData functions
    @Override
    public void create(AuthData authData) throws DataAccessException {
        if(authData.authToken() == null | authData.userName() == null){
            throw new DataAccessException("Database shouldn't accept NULL as an AuthToken or Username");}
        var statement = "INSERT INTO authtokens (username, authtoken) VALUES (?, ?)";
        int id = updateAuthTokens(statement, authData.userName(), authData.authToken());
    }

    @Override
    public AuthData findByAuth(String authToken) throws DataAccessException {
        try (Connection con = DatabaseManager.getConnection()) {
            var statement = "SELECT authtoken, username FROM authtokens WHERE authtoken=?";
            try (PreparedStatement ps = con.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readAuth(rs);
                    }
                }

            }

        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (findByAuth(authToken) == null){throw new DataAccessException("No Token Found");}
        var statement = "DELETE FROM authtokens WHERE authtoken=?";
        updateAuthTokens(statement, authToken);
    }

    //User functions
    @Override
    public void create(UserData user) throws DataAccessException {//I need to hash the password
        if(user.password() == null | user.userName() == null){
            throw new DataAccessException("Database shouldn't accept NULL as an AuthToken or Username");}
        String password = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        updateUsersGames(statement, user.userName(), password, user.email());
    }

    @Override
    public UserData findByUsername(String username) throws DataAccessException {//I need to unhash the password
        try (Connection con = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM users WHERE username=?";
            try (PreparedStatement ps = con.prepareStatement(statement)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readUser(rs);
                    }
                }

            }

        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    //Game functions
    @Override
    public CreateGameResult createGame(String name) throws DataAccessException {//I need to serialize game
        if (name == null){throw new DataAccessException("Can't pass null as name for string");}
        ChessGame game = new ChessGame();
        String serializedGame = ChessGame.serialize(game);
        var statement = "INSERT INTO games (gamename, game, whiteusername, blackusername) VALUES (?, ?, NULL, NULL)";
        int id = updateUsersGames(statement, name, serializedGame);
        return new CreateGameResult(id);
    }

    @Override
    public GameData findByID(Integer id) throws DataAccessException {//I need to deserialize game
        try (Connection con = DatabaseManager.getConnection()) {
            var statement = "SELECT id, gamename, game, whiteusername, blackusername FROM games WHERE id=?";
            try (PreparedStatement ps = con.prepareStatement(statement)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(String.format("Unable to read data: %s", e.getMessage()));
        }
        return null;
    }

    @Override
    public void updateGamePlayer(Integer id, ChessGame.TeamColor color, String username) throws DataAccessException {
        if (username == null){throw new DataAccessException("can't name user null");}
        if (color == ChessGame.TeamColor.BLACK){
            var statement = "UPDATE games SET blackusername = ? WHERE id=?";
            int newId = updateUsersGames(statement, username, id);
        }
        if (color == ChessGame.TeamColor.WHITE){
            var statement = "UPDATE games SET whiteusername = ? WHERE id=?";
            int newId = updateUsersGames(statement, username, id);
        }
    }

    @Override
    public void clearPlayer(Integer id, ChessGame.TeamColor color) throws DataAccessException{
        if (color == ChessGame.TeamColor.BLACK){
            var statement = "UPDATE games SET blackusername = ? WHERE id=?";
            int newId = updateUsersGames(statement, null, id);
        }
        if (color == ChessGame.TeamColor.WHITE){
            var statement = "UPDATE games SET whiteusername = ? WHERE id=?";
            int newId = updateUsersGames(statement, null, id);
        }
    }
    
    @Override
    public void updateGame(Integer id, ChessGame game) throws DataAccessException{
        var statement = "UPDATE games SET game = ? WHERE id=?";
        String serializedGame = ChessGame.serialize(game);
        updateUsersGames(statement, serializedGame, id);
    }


    @Override
    public ListGamesResult listGames() throws DataAccessException {//I need to deserialize games
        String statement = "SELECT * FROM games";
        ArrayList<GameData> games = new ArrayList<>();
        try (Connection con = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()){
                        GameData game = readGame(rs);
                        games.add(game);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
        return new ListGamesResult(games);
    }

    @Override
    public void clear() throws DataAccessException {
        try {
            var statement = "TRUNCATE users";
            updateUsersGames(statement);
            statement = "TRUNCATE authtokens";
            updateAuthTokens(statement);
            statement = "TRUNCATE games";
            updateUsersGames(statement);
        } catch (Exception e) {
            throw new DataAccessException(e + " Data Access Error");
        }
    }

//auth helper functions

    private AuthData readAuth(ResultSet rs) throws SQLException {
        var authToken = rs.getString("authtoken");
        var userName = rs.getString("username");
        return new AuthData(userName, authToken);
    }


    // let's make an update function for each database type!
    private int updateAuthTokens(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    ps.setString(i+1, param.toString());
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

    //user helper functions
    private UserData readUser(ResultSet rs) throws SQLException {
        var userName = rs.getString("username");
        var password = rs.getString("password");
        var email = rs.getString("email");
        return new UserData(userName, password, email);
    }


    private int updateUsersGames(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    if (param instanceof String p) {ps.setString(i + 1, p);}
                    else if (param instanceof Integer p) {ps.setInt(i + 1, p);}
                    else if (param == null) {ps.setNull(i + 1, NULL);}
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

    //game helper functions
    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var whiteusername = rs.getString("whiteusername");
        var blackusername = rs.getString("blackusername");
        var gamename = rs.getString("gamename");
        var gameString = rs.getString("game");
        ChessGame game = ChessGame.deserialize(gameString);
        return new GameData(id, whiteusername, blackusername, gamename, game);
    }


    //creation statements

    private final String[] createAuthStatements = { //edit to auth table
            """
            CREATE TABLE IF NOT EXISTS  authtokens (
              `authtoken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authtoken`),
              INDEX(username),
              INDEX(authtoken)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private final String[] createUserStatements = { //edit to user table
            //password will be hashed idk if varchar(256) will work for that
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
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
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `whiteusername` varchar(256),
              `blackusername` varchar(256),
              `gamename` varchar(256) NOT NULL,
              `game` TEXT NOT NULL,
              PRIMARY KEY (`id`),
              INDEX(whiteusername),
              INDEX(blackusername),
              INDEX(gamename)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureTable(String[] statements) throws DataAccessException {
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
        try {
            configureTable(createGameStatements);
            configureTable(createAuthStatements);
            configureTable(createUserStatements);
        } catch (DataAccessException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }


}