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
    TeamColor turn_haver;

    public ChessGame() {
        this.board = new ChessBoard();
        setBoard(board);
        this.turn_haver = TeamColor.WHITE;
        board.resetBoard();
    }

    public ChessGame(ChessGame original){
        this.board = original.board;
        this.turn_haver = original.turn_haver;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return turn_haver;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        turn_haver = team;
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
//            if (getTeamTurn() != piece.getTeamColor()){return null;}
            Collection<ChessMove> initial_list = piece.pieceMoves(getBoard(), startPosition);
            return ValidMover.validateMoves(getBoard(), initial_list, piece.getTeamColor());
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
            setBoard(ValidMover.moveMaker(board, move, turn_haver, piece));
            if (turn_haver == TeamColor.WHITE) {
                setTeamTurn(TeamColor.BLACK);
            } else if (turn_haver == TeamColor.BLACK) {
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
        if (gameOverChecker.inCheckmate(getBoard(), teamColor)){return true;} else {return false;}
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
        if (gameOverChecker.inStalemate(getBoard(), teamColor)){return true;} else {return false;}
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
        return chessGame.board.equals(board) && turn_haver == chessGame.turn_haver;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBoard(), turn_haver);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", turn_haver=" + turn_haver +
                '}';
    }
}
