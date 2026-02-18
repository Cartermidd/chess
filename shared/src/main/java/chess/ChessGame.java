package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessBoard board;
    TeamColor turnHaver;

    public ChessGame() {
        this.board = new ChessBoard();
        setBoard(board);
        this.turnHaver = TeamColor.WHITE;
        board.resetBoard();
    }

    public ChessGame(ChessGame original){
        this.board = original.board;
        this.turnHaver = original.turnHaver;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turnHaver;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turnHaver = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = getBoard().getPiece(startPosition);
        if (piece == null){
            return null;
        } else {
            Collection<ChessMove> initialList = piece.pieceMoves(getBoard(), startPosition);
            return ValidMover.validateMoves(getBoard(), initialList, piece.getTeamColor());
        }

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = getBoard().getPiece(move.getStartPosition());
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if (validMoves == null) {
            throw new InvalidMoveException("Invalid Move");
        } else if (!validMoves.contains(move)) {
            throw new InvalidMoveException("Invalid Move");
        } else if(getTeamTurn() != piece.getTeamColor()){
            throw new InvalidMoveException("Wrong turn");
        } else {
            setBoard(ValidMover.moveMaker(board, move, turnHaver, piece));
            if (turnHaver == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else if (turnHaver == TeamColor.BLACK) {
                setTeamTurn(TeamColor.WHITE);
            }
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return CheckChecker.inCheck(getBoard(), teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) { //broken
        if (!isInCheck(teamColor)){return false;}
        if (GameOverChecker.inCheckmate(getBoard(), teamColor)){return true;} else {return false;}
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (isInCheck(teamColor)){return false;}
        ChessBoard safeboard = new ChessBoard(getBoard());
        if (GameOverChecker.inStalemate(getBoard(), teamColor)){setBoard(safeboard); return true;} else {setBoard(safeboard); return false;}
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the New Board to use
     */
    public void setBoard(ChessBoard board) {this.board = board;}

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return chessGame.board.equals(board) && turnHaver == chessGame.turnHaver;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBoard(), turnHaver);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", turnHaver=" + turnHaver +
                '}';
    }
}
