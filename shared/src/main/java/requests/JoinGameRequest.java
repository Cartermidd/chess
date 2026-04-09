package requests;

import models.chess.ChessGame;

public class JoinGameRequest {
    ChessGame.TeamColor playerColor;
    Integer gameID;

    public JoinGameRequest(ChessGame.TeamColor playerColor, Integer gameID) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public JoinGameRequest(String[] params) {

        if (params.length > 0) {
            this.gameID = Integer.parseInt(params[0]);
            if (params.length > 1){
                if (params[1].equals("BLACK") || params[1].equals("black")) {
                    this.playerColor = ChessGame.TeamColor.BLACK;
                } else if (params[1].equals("WHITE") || params[1].equals("white")) {
                    this.playerColor = ChessGame.TeamColor.WHITE;
                } else {
                    this.playerColor = null;
                }
            }
        }
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
