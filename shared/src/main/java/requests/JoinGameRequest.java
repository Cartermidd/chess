package requests;

import chess.ChessGame;

public class JoinGameRequest {
    ChessGame.TeamColor playerColor;
    Integer gameID;

    public JoinGameRequest(ChessGame.TeamColor playerColor, Integer gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }


    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }

    public int getGameId() {
        return gameID;
    }

    public static boolean misformatted(JoinGameRequest request){
        return request.playerColor == null | request.gameID == null;
    }
}
