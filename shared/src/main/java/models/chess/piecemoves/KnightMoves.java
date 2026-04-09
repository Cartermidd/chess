package models.chess.piecemoves;

import models.chess.*;
import models.chess.ChessBoard;
import models.chess.ChessGame;
import models.chess.ChessMove;
import models.chess.ChessPosition;

import java.util.ArrayList;
import static models.chess.piecemoves.ValidateMove.validateMove;

public class KnightMoves {

    public ArrayList<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> knightMoves = new ArrayList<ChessMove>();
        int row = myPostion.getRow();
        int col = myPostion.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPostion).getTeamColor();

        if (validateMove(board, row+2, col+1, myColor)){//up two right one
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row+2,col+1),null));
        }
        if (validateMove(board, row+1, col+2, myColor)){//up one right two
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row+1,col+2),null));
        }
        if (validateMove(board, row-1, col+2, myColor)){
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row-1,col+2),null));
        }
        if (validateMove(board, row-2, col+1, myColor)){
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row-2,col+1),null));
        }
        if (validateMove(board, row-2, col-1, myColor)){
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row-2,col-1),null));
        }
        if (validateMove(board, row-1, col-2, myColor)){
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row-1,col-2),null));
        }
        if (validateMove(board, row+1, col-2, myColor)){
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row+1,col-2),null));
        }
        if (validateMove(board, row+2, col-1, myColor)){
            knightMoves.add(new ChessMove(myPostion, new ChessPosition(row+2,col-1),null));
        }
        return knightMoves;

    }


}