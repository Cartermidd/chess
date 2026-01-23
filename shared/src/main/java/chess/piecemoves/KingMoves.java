package chess.piecemoves;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ChessPiece;

import java.util.ArrayList;



public class KingMoves {
    public ArrayList<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPosition, ChessPiece piece) {
        ArrayList<ChessMove> moves_Array = new ArrayList<>();
        int curr_row = myPosition.getRow();
        int curr_col = myPosition.getColumn();
        ChessGame.TeamColor myColor = piece.getTeamColor();

        //append to list up_right
        if (ValidMoveCheck(board,curr_row+1,curr_col, myColor)){//up
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row+1, curr_col),null));
        }
        if (ValidMoveCheck(board,curr_row+1,curr_col+1, myColor)){//up_right
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row+1, curr_col+1),null));
        }
        if (ValidMoveCheck(board,curr_row,curr_col+1, myColor)){//right
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row, curr_col+1),null));
        }
        if (ValidMoveCheck(board,curr_row-1,curr_col+1, myColor)){//down_right
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row-1, curr_col+1),null));
        }
        if (ValidMoveCheck(board,curr_row-1,curr_col, myColor)){//down
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row-1, curr_col),null));
        }
        if (ValidMoveCheck(board,curr_row-1,curr_col-1, myColor)){//down_left
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row-1, curr_col-1),null));
        }
        if (ValidMoveCheck(board,curr_row,curr_col-1, myColor)){//left
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row, curr_col-1),null));
        }
        if (ValidMoveCheck(board,curr_row+1,curr_col-1, myColor)){//up_left
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row+1, curr_col-1),null));
        }

        return moves_Array;
    }


    private boolean ValidMoveCheck(ChessBoard board, int row, int col, ChessGame.TeamColor myColor) {
        ChessPosition curr_pos;
        if (row > 8 | row < 1 | col > 8 | col < 1) {
            return false;
        } else {
            curr_pos = new ChessPosition(row, col);
        }
        if (board.getPiece(curr_pos) == null) {
            return true;
        } else {
            if (board.getPiece(curr_pos).getTeamColor() != myColor){
                return true;
            } else {
                return false;
            }
        }
    }
}
//
//
//    private ChessGame.TeamColor myColor;
//    private ChessPosition myPosition;
//    private ChessBoard board;
//
//
//    //up
//    //up_right
//    //right
//    //down_right
//    //down
//    //down_left
//    //left
//    //up_left
//
//    //someone there?
//    //into check (someday)
//
//    //checks and checkmate to be added later
//}
