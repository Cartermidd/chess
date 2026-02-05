package chess;

import java.util.Collection;
import java.util.List;

public class ValidMover {

    public static Collection<ChessMove> validateMoves(ChessGame game, Collection<ChessMove> initial_list, ChessGame.TeamColor teamColor){
        Collection<ChessMove> validMoves = new java.util.ArrayList<>(List.of());
        ChessGame copyGame = new ChessGame(game);
        for (ChessMove move : initial_list){
            copyGame.board = game.board;
            moveMaker(copyGame.board, move, teamColor);
            if (!copyGame.isInCheck(teamColor)){validMoves.add(move);}
        }
        return validMoves;
    }




    /**
     * returns a copy board with move made
     * @param board original board object (will be copied not edited)
     * @param move chess move on copy board
     */
    public static ChessBoard moveMaker(ChessBoard board, ChessMove move, ChessGame.TeamColor teamColor){
        ChessBoard copyBoard = new ChessBoard(board);
        ChessGame.TeamColor moverColor = teamColor;
        if (move.getPromotionPiece() == null){
            copyBoard.addPiece(move.getEndPosition(), copyBoard.getPiece(move.getStartPosition()));
            copyBoard.addPiece(move.getStartPosition(), null);
        } else {
            copyBoard.addPiece(move.getEndPosition(), new ChessPiece(moverColor, move.getPromotionPiece()));
            copyBoard.addPiece(move.getStartPosition(), null);
        }
        return copyBoard;
    }

}
