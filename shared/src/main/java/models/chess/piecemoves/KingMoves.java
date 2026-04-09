package models.chess.piecemoves;

import chess.*;
import models.chess.ChessBoard;
import models.chess.ChessGame;
import models.chess.ChessMove;
import models.chess.ChessPosition;

import java.util.ArrayList;
import static models.chess.piecemoves.ValidateMove.validateMove;

public class KingMoves {

    public ArrayList<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        int row = myPostion.getRow();
        int col = myPostion.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPostion).getTeamColor();

        if (validateMove(board, row+1, col, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row+1,col),null));
        }
        if (validateMove(board, row+1, col+1, myColor)){//up one right two
            moves.add(new ChessMove(myPostion, new ChessPosition(row+1,col+1),null));
        }
        if (validateMove(board, row, col+1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row,col+1),null));
        }
        if (validateMove(board, row-1, col+1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row-1,col+1),null));
        }
        if (validateMove(board, row-1, col, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row-1,col),null));
        }
        if (validateMove(board, row-1, col-1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row-1,col-1),null));
        }
        if (validateMove(board, row, col-1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row,col-1),null));
        }
        if (validateMove(board, row+1, col-1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row+1,col-1),null));
        }
        return moves;

    }

}


