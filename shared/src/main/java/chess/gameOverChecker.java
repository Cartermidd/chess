package chess;

import java.util.Collection;
import java.util.List;

public class gameOverChecker {
    
    public static boolean inStalemate(ChessBoard board, ChessGame.TeamColor teamColor) {
        ChessGame game = new ChessGame();
        game.setBoard(new ChessBoard(board));
        Collection<ChessPosition> teamPieces = findTeamPieces(game.getBoard(), teamColor);
        Collection<ChessMove> availableMoves = new java.util.ArrayList<>(List.of());
        for (ChessPosition piece : teamPieces){
            availableMoves.addAll(game.validMoves(piece));
        }
        if (availableMoves.isEmpty()){return true;}else{return false;}
    }

    public static boolean inCheckmate(ChessBoard board, ChessGame.TeamColor teamColor) {
        ChessGame game = new ChessGame();
        game.setBoard(new ChessBoard(board));
        Collection<ChessPosition> teamPieces = findTeamPieces(game.board, teamColor);
        Collection<ChessMove> escapeMoves = new java.util.ArrayList<>(List.of());
        for (ChessPosition piece : teamPieces){
            escapeMoves.addAll(game.validMoves(piece));
        }
        if (escapeMoves.isEmpty()){return true;}else{return false;}
    }


    private static Collection<ChessPosition> findTeamPieces(ChessBoard board, ChessGame.TeamColor teamColor) {
        Collection<ChessPosition> teamPieces = new java.util.ArrayList<>(List.of());
        for (int i = 1; i <=8; i++){
            for (int j = 1; j <=8; j++){
                ChessPiece piece = board.getPiece(new ChessPosition(i,j));
                if (piece != null){
                    if (piece.getTeamColor() == teamColor){
                        teamPieces.add(new ChessPosition(i,j));
                    }
                }
            }
        }
        if (teamPieces.isEmpty()) {
            throw new RuntimeException("no pieces of teamColor found");
        } else {
            return teamPieces;
        }
    }
}
