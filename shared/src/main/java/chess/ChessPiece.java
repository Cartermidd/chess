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
        if (this.getPieceType() == PieceType.BISHOP){
            BishopMoves bishopMoves = new BishopMoves();
            return bishopMoves.getBishopMoves(board, myPosition, piece);
        }
        else if (this.getPieceType() == PieceType.QUEEN){
            QueenMoves queenMoves = new QueenMoves();
            return queenMoves.getQueenMoves(board, myPosition, piece);
        }
        else if (this.getPieceType() == PieceType.PAWN){
            PawnMoves pawnMoves = new PawnMoves();
            return pawnMoves.getPawnMoves(board, myPosition, piece);
        }
        else if (this.getPieceType() == PieceType.ROOK){
            RookMoves rookMoves = new RookMoves();
            return rookMoves.getRookMoves(board, myPosition, piece);
        }
        else if (this.getPieceType() == PieceType.KNIGHT){
            KnightMoves knightMoves = new KnightMoves();
            return knightMoves.getKnightMoves(board, myPosition, piece);
        }
        else if (this.getPieceType() == PieceType.KING){
            KingMoves kingMoves = new KingMoves();
            return kingMoves.getKingMoves(board, myPosition, piece);
        }
        else{
            throw new RuntimeException("piece was none of the pieces lol");
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
