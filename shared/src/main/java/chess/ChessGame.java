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
            if (getTeamTurn() != piece.getTeamColor()){return null;}
            Collection<ChessMove> initial_list = piece.pieceMoves(getBoard(), startPosition);
            return ValidMover.validateMoves(getBoard(), startPosition, initial_list);
        }

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        if (validMoves == null){
            throw new InvalidMoveException("Invalid Move");
        } else if (!validMoves.contains(move)) {
            throw new InvalidMoveException("Invalid Move");
        } else{
            ChessBoard copyBoard = new ChessBoard(board);
            ChessGame.TeamColor moverColor = copyBoard.getPiece(move.getStartPosition()).getTeamColor();
            if (move.getPromotionPiece() == null){
                copyBoard.addPiece(move.getEndPosition(), copyBoard.getPiece(move.getStartPosition()));
                copyBoard.addPiece(move.getStartPosition(), null);
            } else {
                copyBoard.addPiece(move.getEndPosition(), new ChessPiece(moverColor, move.getPromotionPiece()));
                copyBoard.addPiece(move.getStartPosition(), null);
            }
            this.board = copyBoard;
            if (moverColor == TeamColor.WHITE){setTeamTurn(TeamColor.BLACK);}
            else if (moverColor == TeamColor.BLACK){setTeamTurn(TeamColor.WHITE);}
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return CheckChecker.inCheck(board, teamColor);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)){return false;}
        ChessGame copy = new ChessGame(this);
        if (gameOverChecker.inCheckmate(copy, copy.board, teamColor)){return true;} else {return false;}
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
        ChessGame copy = new ChessGame(this);
        if (gameOverChecker.inStalemate(copy, copy.board, teamColor)){return true;} else {return false;}
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param NewBoard the New Board to use
     */
    public void setBoard(ChessBoard NewBoard) {NewBoard.resetBoard();}

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
        return Objects.equals(getBoard(), chessGame.getBoard()) && turn_haver == chessGame.turn_haver;
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
