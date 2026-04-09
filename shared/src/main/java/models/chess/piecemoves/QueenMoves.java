package models.chess.piecemoves;
import java.util.ArrayList;
import chess.*;
import models.chess.ChessBoard;
import models.chess.ChessMove;
import models.chess.ChessPosition;


public class QueenMoves {
    public ArrayList<ChessMove> getQueenMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        RookMoves rook = new RookMoves();
        BishopMoves bishop = new BishopMoves();

        moves.addAll(rook.getRookMoves(board,myPostion));
        moves.addAll(bishop.getBishopMoves(board,myPostion));

        return moves;

    }
}
