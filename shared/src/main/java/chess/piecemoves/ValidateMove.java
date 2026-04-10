package chess.piecemoves;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class ValidateMove {

    public static boolean validateMove(ChessBoard board, int row, int col, ChessGame.TeamColor myColor){
        if (col <= 8 & col >= 1 & row <= 8 & row >= 1){
            ChessPosition currPos = new ChessPosition(row, col);
            if (board.getPiece(currPos) == null){
                return true;
            } else {
                if (board.getPiece(currPos).getTeamColor() != myColor &&
                        board.getPiece(currPos).getPieceType() != ChessPiece.PieceType.KING){
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
