package models.chess.piecemoves;
import java.util.ArrayList;
import chess.*;
import models.chess.ChessBoard;
import models.chess.ChessGame;
import models.chess.ChessMove;
import models.chess.ChessPosition;

import static models.chess.piecemoves.MoveUntil.moveUntil;


public class BishopMoves {
    public ArrayList<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor myColor = board.getPiece(myPostion).getTeamColor();

        moves.addAll(moveUntil(board,myPostion,1,1,myColor));
        moves.addAll(moveUntil(board,myPostion,-1,1,myColor));
        moves.addAll(moveUntil(board,myPostion,-1,-1,myColor));
        moves.addAll(moveUntil(board,myPostion,1,-1,myColor));

        return moves;

    }

}
