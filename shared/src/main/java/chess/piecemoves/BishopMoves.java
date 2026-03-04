package chess.piecemoves;
import java.util.ArrayList;
import chess.*;
import static chess.piecemoves.MoveUntil.moveUntil;


public class BishopMoves {
    public ArrayList<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor myColor = board.getPiece(myPostion).getTeamColor();

        moves.addAll(moveUntil(board,myPostion,1,1,myColor));
        moves.addAll(moveUntil(board,myPostion,-1,1,myColor));
        moves.addAll(moveUntil(board,myPostion,-1,-1,myColor));
        moves.addAll(moveUntil(board,myPostion,1,-1,myColor));

        return moves;

    }

}
