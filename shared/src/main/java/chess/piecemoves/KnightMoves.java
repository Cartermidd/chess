package chess.piecemoves;

import chess.*;

import java.util.ArrayList;

public class KnightMoves {

    public ArrayList<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        int row = myPostion.getRow();
        int col = myPostion.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(myPostion).getTeamColor();

        if (validateMove(board, row+2, col+1, myColor)){//up two right one
            moves.add(new ChessMove(myPostion, new ChessPosition(row+2,col+1),null));
        }
        if (validateMove(board, row+1, col+2, myColor)){//up one right two
            moves.add(new ChessMove(myPostion, new ChessPosition(row+1,col+2),null));
        }
        if (validateMove(board, row-1, col+2, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row-1,col+2),null));
        }
        if (validateMove(board, row-2, col+1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row-2,col+1),null));
        }
        if (validateMove(board, row-2, col-1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row-2,col-1),null));
        }
        if (validateMove(board, row-1, col-2, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row-1,col-2),null));
        }
        if (validateMove(board, row+1, col-2, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row+1,col-2),null));
        }
        if (validateMove(board, row+2, col-1, myColor)){
            moves.add(new ChessMove(myPostion, new ChessPosition(row+2,col-1),null));
        }
        return moves;

    }

    private boolean validateMove(ChessBoard board, int row, int col, ChessGame.TeamColor myColor){
        if (col <= 8 & col >= 1 & row <= 8 & row >= 1){
            chess.ChessPosition currPos = new ChessPosition(row, col);
            if (board.getPiece(currPos) == null){
                return true;
            } else {
                if (board.getPiece(currPos).getTeamColor() != myColor){
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

}