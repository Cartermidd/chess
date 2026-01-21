package chess.piecemoves;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ChessPiece;

import java.util.ArrayList;



public class BishopMoves {

    public ArrayList<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPosition, ChessPiece piece) {
        ArrayList<ChessMove> moves_Array = new ArrayList<>();
        int curr_row = myPosition.getRow();
        int curr_col = myPosition.getColumn();
        ChessGame.TeamColor myColor = piece.getTeamColor();

        //append to list up_right list
        moves_Array.addAll(up_right(board, curr_row, curr_col, myColor));
        //append to list down_right list
        moves_Array.addAll(down_right(board, curr_row, curr_col, myColor));
        //append to list up_left list
        moves_Array.addAll(up_left(board, curr_row, curr_col, myColor));
        //append to list down_left list
        moves_Array.addAll(down_left(board, curr_row, curr_col, myColor));

        return moves_Array;
    }

    private ArrayList<ChessMove> up_right(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> up_right_array = new ArrayList<>();
        int curr_col = og_col;
        int curr_row = og_row;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_row < 8 & curr_col < 8) {
            curr_row++; //curr_row++ curr_col++(go up and right)
            curr_col++;
            ChessPosition curr_pos = new ChessPosition(curr_row, curr_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {
                    //if my color is the same as piece ///value based equality .equals()
                    //break
                    break;
                } else {//else (opponent color)
                    up_right_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            up_right_array.add(new ChessMove(og_pos, curr_pos, null)); //add to list
        }
        return up_right_array;
    }

    private ArrayList<ChessMove> down_right(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> down_right_array = new ArrayList<>();
        int curr_col = og_col;
        int curr_row = og_row;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_row > 1 & curr_col < 8) {
            curr_row--;
            curr_col++;
            //curr_row-- curr_col++(go down and right)
            ChessPosition curr_pos = new ChessPosition(curr_row, curr_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {//if my color is the same as piece ///value based equality .equals()
                    break;//break
                } else {//else (opponent color)
                    down_right_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            down_right_array.add(new ChessMove(og_pos, curr_pos, null));
        }
        return down_right_array;
    }


    private ArrayList<ChessMove> up_left(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> up_left_array = new ArrayList<>();
        int curr_col = og_col;
        int curr_row = og_row;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_row < 8 & curr_col > 1) {
            curr_row++;
            curr_col--;
            //curr_row-- curr_col++(go down and right)
            ChessPosition curr_pos = new ChessPosition(curr_row, curr_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {//if my color is the same as piece ///value based equality .equals()
                    break;//break
                } else {//else (opponent color)
                    up_left_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            up_left_array.add(new ChessMove(og_pos, curr_pos, null));
        }
        return up_left_array;
    }

    private ArrayList<ChessMove> down_left(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> down_left_array = new ArrayList<>();
        int curr_col = og_col;
        int curr_row = og_row;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_row > 1 & curr_col > 1) {
            curr_row--;
            curr_col--;
            //curr_row-- curr_col++(go down and right)
            ChessPosition curr_pos = new ChessPosition(curr_row, curr_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {//if my color is the same as piece ///value based equality .equals()
                    break;//break
                } else {//else (opponent color)
                    down_left_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            down_left_array.add(new ChessMove(og_pos, curr_pos, null));
        }
        return down_left_array;
    }
}


//    @Override
//    public boolean equals(Object o) {
//        if (o == null) {
//            return false;
//        }
//        if (o == this){
//            return true;
//        }
//        if (this.getClass() != o.getClass()){
//            return false;
//        }
//
//        BishopMoves cast = (BishopMoves)o; //casting o object to a BishopMoves object
//
//        return (myColor.equals(cast.myColor) && myPosition == cast.myPosition && board == cast.board);
//    }
//

//static variables and functions should be class level processes
