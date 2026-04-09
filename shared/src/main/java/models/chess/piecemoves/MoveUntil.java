package models.chess.piecemoves;

import chess.*;
import models.chess.*;

import java.util.ArrayList;

public class MoveUntil {

    public static ArrayList<ChessMove> moveUntil(ChessBoard board, ChessPosition myPosition,
                                                 int rowDirection, int colDirection, ChessGame.TeamColor myColor){
        int currRow = myPosition.getRow() + rowDirection;
        int currCol = myPosition.getColumn() + colDirection;
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        while (currRow >= 1 & currRow <=8 & currCol >= 1 & currCol <= 8){
            ChessPosition currPos = new ChessPosition(currRow, currCol);
            if (board.getPiece(currPos) == null){
                moves.add(new ChessMove(myPosition,currPos,null));
            } else if (board.getPiece(currPos).getTeamColor() != myColor && board.getPiece(currPos).getPieceType() != ChessPiece.PieceType.KING){
                moves.add(new ChessMove(myPosition,currPos,null));
                break;
            } else {
                break;
            }
            currRow = currRow + rowDirection;
            currCol = currCol + colDirection;
        }
        return moves;
    }


}
