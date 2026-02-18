package chess;

import java.util.Collection;
import java.util.List;

public class ValidMover {

    public static Collection<ChessMove> validateMoves(ChessBoard board, Collection<ChessMove> initialList, ChessGame.TeamColor teamColor){
        Collection<ChessMove> validMoves = new java.util.ArrayList<>(List.of());

        for (ChessMove move : initialList){
            ChessBoard testBoard = new ChessBoard(board);
            ChessGame testGame = new ChessGame();
            ChessPiece piece = testBoard.getPiece(move.getStartPosition());
            testBoard = moveMaker(testBoard,move,teamColor,piece);
            testGame.setBoard(testBoard);
            if (!testGame.isInCheck(teamColor)){validMoves.add(move);}
        }
        return validMoves;
    }




    /**
     * returns a copy board with move made
     * @param board original board object (will be copied not edited)
     * @param move chess move on copy board
     */
    public static ChessBoard moveMaker(ChessBoard board, ChessMove move, ChessGame.TeamColor teamColor, ChessPiece piece){
        ChessBoard copyBoard = new ChessBoard(board);
        if (move.getPromotionPiece() == null){
            copyBoard.addPiece(move.getEndPosition(), piece);
            copyBoard.addPiece(move.getStartPosition(), null);//not removing the piece
        } else {
            copyBoard.addPiece(move.getEndPosition(), new ChessPiece(teamColor, move.getPromotionPiece()));
            copyBoard.addPiece(move.getStartPosition(), null);//not removing the piece
        }
        return copyBoard;
    }
}
