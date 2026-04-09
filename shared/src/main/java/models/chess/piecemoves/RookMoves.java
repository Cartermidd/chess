package models.chess.piecemoves;
import java.util.ArrayList;
import models.chess.*;
import models.chess.ChessBoard;
import models.chess.ChessGame;
import models.chess.ChessMove;
import models.chess.ChessPosition;

import static models.chess.piecemoves.MoveUntil.moveUntil;


public class RookMoves {
    public ArrayList<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor myColor = board.getPiece(myPostion).getTeamColor();

        moves.addAll(moveUntil(board,myPostion,1,0,myColor));
        moves.addAll(moveUntil(board,myPostion,-1,0,myColor));
        moves.addAll(moveUntil(board,myPostion,0,1,myColor));
        moves.addAll(moveUntil(board,myPostion,0,-1,myColor));

        return moves;

    }

}