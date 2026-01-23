package chess.piecemoves;

import chess.*;

import java.util.ArrayList;

public class KnightMoves {

    public ArrayList<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition, ChessPiece piece) {
        ArrayList<ChessMove> moves_Array = new ArrayList<>();
        int curr_row = myPosition.getRow();
        int curr_col = myPosition.getColumn();
        ChessGame.TeamColor myColor = piece.getTeamColor();

        //how does the horse even move lol

        //append to list two_down_left
        if (ValidMoveCheck(board,curr_row-2,curr_col-1, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row-2, curr_col-1),null));
        }
        //two_down_right
        if (ValidMoveCheck(board,curr_row-2,curr_col+1, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row-2, curr_col+1),null));
        }
        //down_two_left
        if (ValidMoveCheck(board,curr_row-1,curr_col-2, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row-1, curr_col-2),null));
        }
        if (ValidMoveCheck(board,curr_row-1,curr_col+2, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row-1, curr_col+2),null));
        }
        if (ValidMoveCheck(board,curr_row+1,curr_col-2, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row+1, curr_col-2),null));
        }
        if (ValidMoveCheck(board,curr_row+1,curr_col+2, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row+1, curr_col+2),null));
        }
        if (ValidMoveCheck(board,curr_row+2,curr_col-1, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row+2, curr_col-1),null));
        }
        if (ValidMoveCheck(board,curr_row+2,curr_col+1, myColor)){
            moves_Array.add(new ChessMove(myPosition,new ChessPosition(curr_row+2, curr_col+1),null));
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
//    int curr_row = myPosition.getRow();
//    int curr_col = myPosition.getColumn();
//
//    private two_up_left(ChessBoard board, ChessPosition myPosition){
//        if (curr_row + 2 > 8) {
//            return;
//        }
//        if (curr_col - 1 < 1){
//            return;
//        }
//        //if (curr... has piece){
//            //if piece = my color
//                //return
//        return [curr_row+2, curr_col-1];
//    }
//
//
//    private two_up_right(ChessBoard board, ChessPosition myPosition){
//        if (curr_row + 2 > 8) {
//            return;
//        }
//        if (curr_col + 1 > 8){
//            return;
//        }
//        //if (curr... has piece){
//            //if piece = my color
//            //return
//        return [curr_row+2, curr_col+1];
//    }
//
//
//    private two_right_up(ChessBoard board, ChessPosition myPosition){
//        if (curr_row + 1 > 8) {
//            return;
//        }
//        if (curr_col + 2 > 8){
//            return;
//        }
//        //if (curr... has piece){
//            //if piece = my color
//            //return
//        return [curr_row+1, curr_col+2];
//    }
//
//    private two_right_down(ChessBoard board, ChessPosition myPosition){
//        if (curr_row - 1 < 1) {
//            return;
//        }
//        if (curr_col + 2 > 8){
//            return;
//        }
//        //if (curr... has piece){
//        //if piece = my color
//        //return
//        return [curr_row-1, curr_col+2];
//    }
//
//    private two_down_right(ChessBoard board, ChessPosition myPosition){
//        if (curr_row - 2 < 1) {
//            return;
//        }
//        if (curr_col + 1 > 8){
//            return;
//        }
//        //if (curr... has piece){
//        //if piece = my color
//        //return
//        return [curr_row-2, curr_col+1];
//    }
//
//    private two_down_left(ChessBoard board, ChessPosition myPosition){
//        if (curr_row - 2 < 1) {
//            return;
//        }
//        if (curr_col - 1 < 1){
//            return;
//        }
//        //if (curr... has piece){
//        //if piece = my color
//        //return
//        return [curr_row-2, curr_col-1];
//    }
//
//    private two_left_down(ChessBoard board, ChessPosition myPosition){
//        if (curr_row - 1 < 1) {
//            return;
//        }
//        if (curr_col - 2 < 1){
//            return;
//        }
//        //if (curr... has piece){
//        //if piece = my color
//        //return
//        return [curr_row-1, curr_col-2];
//    }
//
//    private two_left_down(ChessBoard board, ChessPosition myPosition){
//        if (curr_row + 1 > 8) {
//            return;
//        }
//        if (curr_col - 2 < 1){
//            return;
//        }
//        //if (curr... has piece){
//        //if piece = my color
//        //return
//        return [curr_row+1, curr_col-2];
//    }
//
//}
