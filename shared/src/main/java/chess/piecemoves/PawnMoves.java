package chess.piecemoves;

import chess.*;

import java.util.ArrayList;

public class PawnMoves {

    public ArrayList<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition, ChessPiece piece) {
        ArrayList<ChessMove> moves_Array = new ArrayList<>();
        ChessGame.TeamColor myColor = piece.getTeamColor();
        ArrayList<ChessPiece.PieceType> PromotionPieces = new ArrayList<>();
        PromotionPieces.add(ChessPiece.PieceType.KNIGHT);
        PromotionPieces.add(ChessPiece.PieceType.QUEEN);
        PromotionPieces.add(ChessPiece.PieceType.BISHOP);
        PromotionPieces.add(ChessPiece.PieceType.ROOK);


        if (myColor == ChessGame.TeamColor.WHITE){
            moves_Array.addAll(whitePawnMoves(board, myPosition, PromotionPieces));
        }else {
            moves_Array.addAll(blackPawnMoves(board, myPosition, PromotionPieces));
        }
        return moves_Array;
    }


    private ArrayList<ChessMove> whitePawnMoves(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPiece.PieceType> PromotionPieces){
        ArrayList<ChessMove> white_move_array = new ArrayList<>();
        int curr_row = myPosition.getRow();
        int curr_col = myPosition.getColumn();
        white_move_array.addAll(white_diagonal(board,curr_row,curr_col,PromotionPieces));
        if (curr_row == 2){
            ChessPosition start_one_up_pos = new ChessPosition(3, curr_col);
            ChessPosition start_two_up_pos = new ChessPosition(4,curr_col);
            if(board.getPiece(start_one_up_pos) == null) {
                white_move_array.add(new ChessMove(myPosition,start_one_up_pos,null));
                if(board.getPiece(start_two_up_pos) == null){
                    white_move_array.add(new ChessMove(myPosition,start_two_up_pos,null));
                }
            }
        }else {
            if (ValidMoveCheck(board, curr_row + 1, curr_col)) {
                ChessPosition new_pos = new ChessPosition(curr_row + 1, curr_col);
                if (white_promotion_check(new_pos)) {
                    for (ChessPiece.PieceType promotion_piece : PromotionPieces) {
                        white_move_array.add(new ChessMove(myPosition, new_pos, promotion_piece));
                    }
                } else {
                    white_move_array.add(new ChessMove(myPosition, new_pos, null));
                }
            }
        }
        return white_move_array;
    }

    private ArrayList<ChessMove> white_diagonal(ChessBoard board, int curr_row, int curr_col, ArrayList<ChessPiece.PieceType> PromotionPieces){
        ChessPosition up_right_pos = null;
        ChessPosition up_left_pos = null;
        ArrayList<ChessMove> diagonal_moves = new ArrayList<>();

        if (curr_col+1 <= 8) {
            up_right_pos = new ChessPosition(curr_row + 1, curr_col + 1);
        }
        if (curr_col-1 >= 1) {
            up_left_pos = new ChessPosition(curr_row + 1, curr_col - 1);
        }
        if (up_right_pos != null){
            if (board.getPiece(up_right_pos) != null && board.getPiece(up_right_pos).getTeamColor() != ChessGame.TeamColor.WHITE){
                if (white_promotion_check(up_right_pos)){
                    for (ChessPiece.PieceType promotion_piece : PromotionPieces){
                        diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), up_right_pos, promotion_piece));
                    }
                }else {
                    diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), up_right_pos, null));
                }
            }
        }
        if (up_left_pos != null){
            if (board.getPiece(up_left_pos) != null && board.getPiece(up_left_pos).getTeamColor() != ChessGame.TeamColor.WHITE){
                if (white_promotion_check(up_left_pos)){
                    for (ChessPiece.PieceType promotion_piece : PromotionPieces){
                        diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), up_left_pos, promotion_piece));
                    }
                }else {
                    diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), up_left_pos, null));
                }
            }
        }
        return diagonal_moves;
    }

    private boolean white_promotion_check(ChessPosition pos){
        if(pos.getRow() == 8){
            return true;
        }else{
            return false;
        }
    }
    
    private ArrayList<ChessMove> blackPawnMoves(ChessBoard board, ChessPosition myPosition, ArrayList<ChessPiece.PieceType> PromotionPieces){
        ArrayList<ChessMove> black_move_array = new ArrayList<>();
        int curr_row = myPosition.getRow();
        int curr_col = myPosition.getColumn();

        black_move_array.addAll(black_diagonal(board,curr_row,curr_col,PromotionPieces));
        if (curr_row == 7) {
            ChessPosition start_one_up_pos = new ChessPosition(6, curr_col);
            ChessPosition start_two_up_pos = new ChessPosition(5, curr_col);
            if (board.getPiece(start_one_up_pos) == null) {
                black_move_array.add(new ChessMove(myPosition, start_one_up_pos, null));
                if (board.getPiece(start_two_up_pos) == null) {
                    black_move_array.add(new ChessMove(myPosition, start_two_up_pos, null));
                }
            }
        } else {
            if (ValidMoveCheck(board, curr_row - 1, curr_col)) {
                ChessPosition new_pos = new ChessPosition(curr_row - 1, curr_col);
                if (black_promotion_check(new_pos)) {
                    for (ChessPiece.PieceType promotion_piece : PromotionPieces) {
                        black_move_array.add(new ChessMove(myPosition, new_pos, promotion_piece));
                    }
                } else {
                    black_move_array.add(new ChessMove(myPosition, new_pos, null));
                }
            }
        }
        return black_move_array;
    }

    private ArrayList<ChessMove> black_diagonal(ChessBoard board, int curr_row, int curr_col, ArrayList<ChessPiece.PieceType> PromotionPieces){
        ChessPosition down_right_pos = null;
        ChessPosition down_left_pos = null;
        ArrayList<ChessMove> diagonal_moves = new ArrayList<>();

        if (curr_col+1 <= 8) {
            down_right_pos = new ChessPosition(curr_row - 1, curr_col + 1);
        }
        if (curr_col-1 >= 1) {
            down_left_pos = new ChessPosition(curr_row - 1, curr_col - 1);
        }
        if (down_right_pos != null){
            if (board.getPiece(down_right_pos) != null && board.getPiece(down_right_pos).getTeamColor() != ChessGame.TeamColor.BLACK){
                if (black_promotion_check(down_right_pos)){
                    for (ChessPiece.PieceType promotion_piece : PromotionPieces){
                        diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), down_right_pos, promotion_piece));
                    }
                }else {
                    diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), down_right_pos, null));
                }
            }
        }
        if (down_left_pos != null){
            if (board.getPiece(down_left_pos) != null && board.getPiece(down_left_pos).getTeamColor() != ChessGame.TeamColor.BLACK){
                if (black_promotion_check(down_left_pos)){
                    for (ChessPiece.PieceType promotion_piece : PromotionPieces){
                        diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), down_left_pos, promotion_piece));
                    }
                }else {
                    diagonal_moves.add(new ChessMove(new ChessPosition(curr_row, curr_col), down_left_pos, null));
                }
            }
        }
        return diagonal_moves;
    }

    private boolean black_promotion_check(ChessPosition pos){
        if(pos.getRow() == 1){
            return true;
        }else{
            return false;
        }
    }

    private boolean ValidMoveCheck(ChessBoard board, int row, int col) {
        ChessPosition curr_pos;
        if (row > 8 | row < 1 | col > 8 | col < 1) {
            return false;
        } else {
            curr_pos = new ChessPosition(row, col);
        }
        if (board.getPiece(curr_pos) == null) {
            return true;
        } else {
            return false;
            }
        }
    }
    // won't submit because it isn't tracking my latest commit, so here's another