package chess.piecemoves;
import java.util.ArrayList;
import chess.*;


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


    private ArrayList<ChessMove> moveUntil(ChessBoard board, ChessPosition myPosition, int rowDirection, int colDirection, ChessGame.TeamColor myColor){
        int currRow = myPosition.getRow() + rowDirection;
        int currCol = myPosition.getColumn() + colDirection;
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        while (currRow >= 1 & currRow <=8 & currCol >= 1 & currCol <= 8){
            chess.ChessPosition currPos = new ChessPosition(currRow, currCol);
            if (board.getPiece(currPos) == null){
                moves.add(new ChessMove(myPosition,currPos,null));
            } else if (board.getPiece(currPos).getTeamColor() != myColor && board.getPiece(currPos).getPieceType() != ChessPiece.PieceType.KING){
                moves.add(new ChessMove(myPosition,currPos,null));
                break;
            } else {
                break;
            }
            currRow = currRow + rowDirection;
            currCol = currCol + colDirection;
        }
        return moves;
    }

}
