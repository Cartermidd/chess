package chess.piecemoves;
import java.util.ArrayList;
import chess.*;


public class RookMoves {
    public ArrayList<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPostion){
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
        ChessGame.TeamColor myColor = board.getPiece(myPostion).getTeamColor();

        moves.addAll(moveUntil(board,myPostion,1,0,myColor));
        moves.addAll(moveUntil(board,myPostion,-1,0,myColor));
        moves.addAll(moveUntil(board,myPostion,0,1,myColor));
        moves.addAll(moveUntil(board,myPostion,0,-1,myColor));

        return moves;

    }


    private ArrayList<ChessMove> moveUntil(ChessBoard board, ChessPosition myPosition, int rowDirection, int colDirection, ChessGame.TeamColor myColor){
        int currRow = myPosition.getRow() + rowDirection;
        int currCol = myPosition.getColumn() + colDirection;
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        while (onBoard(currRow,currCol)){
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

    private boolean onBoard(int row, int col){
        if(row >= 1 & row <=8 & col >= 1 & col <= 8){return true;}
        else {return false;}
    }


}