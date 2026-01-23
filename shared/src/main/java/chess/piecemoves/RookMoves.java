package chess.piecemoves;


import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.ChessPiece;

import java.util.ArrayList;



public class RookMoves {

    public ArrayList<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPosition, ChessPiece piece) {
        ArrayList<ChessMove> moves_Array = new ArrayList<>();
        int curr_row = myPosition.getRow();
        int curr_col = myPosition.getColumn();
        ChessGame.TeamColor myColor = piece.getTeamColor();

        //append to list up list
        moves_Array.addAll(up(board, curr_row, curr_col, myColor));
        //append to list down list
        moves_Array.addAll(down(board, curr_row, curr_col, myColor));
        //append to list right list
        moves_Array.addAll(right(board, curr_row, curr_col, myColor));
        //append to list left list
        moves_Array.addAll(left(board, curr_row, curr_col, myColor));

        return moves_Array;
    }

    private ArrayList<ChessMove> up(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> up_array = new ArrayList<>();
        int curr_row = og_row;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_row < 8) {
            curr_row++;//go up
            ChessPosition curr_pos = new ChessPosition(curr_row, og_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {//if my color is the same as piece ///value based equality .equals()
                    break;//break
                } else {//else (opponent color)
                    up_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            up_array.add(new ChessMove(og_pos, curr_pos, null));
        }
        return up_array;
    }

    private ArrayList<ChessMove> down(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> down_array = new ArrayList<>();
        int curr_row = og_row;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_row > 1) {
            curr_row--;//go down
            ChessPosition curr_pos = new ChessPosition(curr_row, og_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {//if my color is the same as piece ///value based equality .equals()
                    break;//break
                } else {//else (opponent color)
                    down_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            down_array.add(new ChessMove(og_pos, curr_pos, null));
        }
        return down_array;
    }

    private ArrayList<ChessMove> right(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> right_array = new ArrayList<>();
        int curr_col = og_col;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_col < 8) {
            curr_col++;//go right
            ChessPosition curr_pos = new ChessPosition(og_row, curr_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {//if my color is the same as piece ///value based equality .equals()
                    break;//break
                } else {//else (opponent color)
                    right_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            right_array.add(new ChessMove(og_pos, curr_pos, null));
        }
        return right_array;
    }

    private ArrayList<ChessMove> left(ChessBoard board, int og_row, int og_col, ChessGame.TeamColor myColor) {
        ArrayList<ChessMove> left_array = new ArrayList<>();
        int curr_col = og_col;
        ChessPosition og_pos = new ChessPosition(og_row, og_col);
        while (curr_col > 1) {
            curr_col--;//go right
            ChessPosition curr_pos = new ChessPosition(og_row, curr_col);
            if (board.getPiece(curr_pos) != null) {//if there is a piece there
                if (board.getPiece(curr_pos).getTeamColor() == myColor) {//if my color is the same as piece ///value based equality .equals()
                    break;//break
                } else {//else (opponent color)
                    left_array.add(new ChessMove(og_pos, curr_pos, null));//add to list
                    break;//break
                }
            }
            left_array.add(new ChessMove(og_pos, curr_pos, null));
        }
        return left_array;
    }


}


//package chess.piecemoves;
//
//import chess.ChessBoard;
//import chess.ChessPosition;
//
//public class RookMoves {
//
//    public getRookMoves() {
//        //append to list horizontal_right
//        //append to list horizontal_left
//        //append to list vertical_up
//        //append to list vertical_down
//
//
//    }
//
//
//    private horizontal_right(ChessBoard board, ChessPosition myPosition) {
//        int curr_col = myPosition.getColumn();
//        while (curr_col < 9) {
//            //if piece
//            //if my piece
//            //break
//            //else
//            //append position
//            //break
//            //append position
//            curr_col++;
//        }
//        return;//list
//    }
//
//    private horizontal_left(ChessBoard board, ChessPosition myPosition) {
//        int curr_col = myPosition.getColumn();
//        while (curr_col > 0) {
//            //if piece
//            //if my piece
//            //break
//            //else
//            //append position
//            //break
//            //append position
//            curr_col--;
//        }
//        return;
//    }
//
//    private vertical_up(ChessBoard board, ChessPosition myPosition) {
//        int curr_row = myPosition.getRow();
//        while (curr_row < 9) {
//            //if piece
//            //if my piece
//            //break
//            //else
//            //append position
//            //break
//            //append position
//            curr_row++;
//        }
//        return;//list
//    }
//
//    private vertical_down(ChessBoard board, ChessPosition myPosition) {
//        int curr_row = myPosition.getRow();
//        while (curr_row > 0) {
//            //if piece
//            //if my piece
//            //break
//            //else
//            //append position
//            //break
//            //append position
//            curr_row--;
//        }
//        return;//list
//    }
//}
