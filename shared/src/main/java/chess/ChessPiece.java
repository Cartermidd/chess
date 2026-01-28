package chess;

import chess.piecemoves.*;

import java.util.Collection;

import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);

        if (piece == null){
            return null;
            //throw new RuntimeException("no piece :-{");
        } else if (piece.type == PieceType.PAWN){
            chess.piecemoves.PawnMoves moves = new PawnMoves();
            return moves.getPawnMoves(board, myPosition);
        } else if (piece.type == PieceType.ROOK){
            chess.piecemoves.RookMoves moves = new RookMoves();
            return moves.getRookMoves(board, myPosition);
        } else if (piece.type == PieceType.KNIGHT) {
            chess.piecemoves.KnightMoves moves = new KnightMoves();
            return moves.getKnightMoves(board, myPosition);
        }else if (piece.type == PieceType.BISHOP){
            chess.piecemoves.BishopMoves moves = new BishopMoves();
            return moves.getBishopMoves(board, myPosition);
        } else if (piece.type == PieceType.QUEEN){
            chess.piecemoves.QueenMoves moves = new QueenMoves();
            return moves.getQueenMoves(board, myPosition);
        } else if (piece.type == PieceType.KING){
            chess.piecemoves.KingMoves moves = new KingMoves();
            return moves.getKingMoves(board, myPosition);
        } else {
            return null;
            //throw new RuntimeException("piecetype not found bro lol");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
