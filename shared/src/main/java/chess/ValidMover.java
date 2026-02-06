package chess;

import java.util.Collection;
import java.util.List;

public class ValidMover {

    public static Collection<ChessMove> validateMoves(ChessBoard board, Collection<ChessMove> initial_list, ChessGame.TeamColor teamColor){
        Collection<ChessMove> validMoves = new java.util.ArrayList<>(List.of());
        ChessGame game = new ChessGame();
        game.setBoard(new ChessBoard(board));
        for (ChessMove move : initial_list){
            game.board = board;
            moveMaker(game.board, move, teamColor, game.board.getPiece(move.getStartPosition()));
            if (!game.isInCheck(teamColor)){validMoves.add(move);}
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
