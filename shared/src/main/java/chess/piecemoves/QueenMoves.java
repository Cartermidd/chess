package chess.piecemoves;
import java.util.ArrayList;
import chess.*;


public class QueenMoves {
    public ArrayList<ChessMove> getQueenMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        chess.piecemoves.RookMoves rook = new RookMoves();
        chess.piecemoves.BishopMoves bishop = new BishopMoves();

        moves.addAll(rook.getRookMoves(board,myPostion));
        moves.addAll(bishop.getBishopMoves(board,myPostion));

        return moves;

    }
}
