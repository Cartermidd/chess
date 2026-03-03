package requests;

import chess.ChessGame;

public class JoinGameRequest {
    String authToken;
    ChessGame.TeamColor teamColor;
    int gameId;

    public JoinGameRequest(String authToken, ChessGame.TeamColor teamColor, int gameId) {
        this.authToken = authToken;
        this.teamColor = teamColor;
        this.gameId = gameId;
    }


    public String getAuthToken() {
        return authToken;
    }

    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    public int getGameId() {
        return gameId;
    }

    public static boolean misformatted(JoinGameRequest request){
        return request.authToken == null | request.teamColor == null;
    }
}
