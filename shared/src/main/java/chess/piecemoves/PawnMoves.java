package chess.piecemoves;

import chess.*;

import java.util.ArrayList;

public class PawnMoves {

    public ArrayList<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();

        //black pawn moves
        if (myColor == ChessGame.TeamColor.BLACK){
            moves.addAll(blackPawnMoves(board, myPosition));
        }else{
            moves.addAll(whitePawnMoves(board, myPosition));
        }

        return moves;
    }


    private ArrayList<ChessMove> blackPawnMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (onBoard(row-1,col)){
            if (board.getPiece(new ChessPosition(row-1,col)) == null){
                if(promotionCheck(new ChessPosition(row-1,col), ChessGame.TeamColor.BLACK)){
                    moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col), ChessPiece.PieceType.KNIGHT));
                } else{
                    moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col), null));
                }
                if (row == 7){
                    if (board.getPiece(new ChessPosition(row-2,col)) == null){
                        moves.add(new ChessMove(myPosition,new ChessPosition(row-2, col), null));
                    }
                }
            }
        }


        if (diagonalAttack(board,myPosition,-1,1, ChessGame.TeamColor.BLACK)){
            if (promotionCheck(new ChessPosition(row-1, col+1), ChessGame.TeamColor.BLACK)){
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col+1), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col+1), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col+1), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col+1), ChessPiece.PieceType.KNIGHT));
            } else {
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col+1), null));
            }
        }
        if (diagonalAttack(board,myPosition,-1,-1, ChessGame.TeamColor.BLACK)){
            if (promotionCheck(new ChessPosition(row-1, col-1), ChessGame.TeamColor.BLACK)){
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col-1), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col-1), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col-1), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col-1), ChessPiece.PieceType.KNIGHT));
            } else {
                moves.add(new ChessMove(myPosition,new ChessPosition(row-1, col-1), null));
            }
        }

        return moves;
    }


    private ArrayList<ChessMove> whitePawnMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (onBoard(row+1,col)){
            if (board.getPiece(new ChessPosition(row+1,col)) == null) {
                if (promotionCheck(new ChessPosition(row + 1, col), ChessGame.TeamColor.WHITE)) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.ROOK));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), ChessPiece.PieceType.KNIGHT));
                } else {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
                }
                if (row == 2) {
                    if (board.getPiece(new ChessPosition(row + 2, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
                    }
                }
            }
        }

        if (diagonalAttack(board,myPosition,1,1, ChessGame.TeamColor.WHITE)){
            if (promotionCheck(new ChessPosition(row+1, col+1), ChessGame.TeamColor.WHITE)){
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col+1), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col+1), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col+1), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col+1), ChessPiece.PieceType.KNIGHT));
            } else {
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col+1), null));
            }
        }
        if (diagonalAttack(board,myPosition,1,-1, ChessGame.TeamColor.WHITE)){
            if (promotionCheck(new ChessPosition(row+1, col-1), ChessGame.TeamColor.WHITE)){
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col-1), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col-1), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col-1), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col-1), ChessPiece.PieceType.KNIGHT));
            } else {
                moves.add(new ChessMove(myPosition,new ChessPosition(row+1, col-1), null));
            }
        }

        return moves;
    }




    private boolean promotionCheck(ChessPosition pos, ChessGame.TeamColor myColor){
        if(myColor == ChessGame.TeamColor.BLACK & pos.getRow() == 1){
            return true;
        } else if(myColor == ChessGame.TeamColor.WHITE & pos.getRow() == 8){
            return true;
        } else {
            return false;
        }
    }



    private boolean diagonalAttack(ChessBoard board, ChessPosition currPos, int row_direction, int col_direction, ChessGame.TeamColor myColor){
        int new_row = currPos.getRow() + row_direction;
        int new_col = currPos.getColumn() + col_direction;

        if (onBoard(new_row,new_col)){
            if(board.getPiece(new ChessPosition(new_row,new_col)) != null){
                if(board.getPiece(new ChessPosition(new_row,new_col)).getTeamColor() != myColor) {
                    return true;
                } else {
                    return false;
                }
            }else {
                return false;
            }
        } else{
            return false;
        }
    }


    private boolean onBoard(int row, int col){
        if(row >= 1 & row <=8 & col >= 1 & col <= 8){return true;}
        else {return false;}
    }
}
