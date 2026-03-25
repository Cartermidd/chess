package requests;

import chess.ChessGame;

import java.util.Objects;

public class JoinGameRequest {
    ChessGame.TeamColor playerColor;
    Integer gameID;

    public JoinGameRequest(ChessGame.TeamColor playerColor, Integer gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public JoinGameRequest(String[] params) {
        this.playerColor = (params.length > 0) ? (Objects.equals(params[0], "BLACK") | Objects.equals(params[0], "black")) ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE : null;
        this.gameID = (params.length > 1) ? Integer.valueOf(params[1]) : null;
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
