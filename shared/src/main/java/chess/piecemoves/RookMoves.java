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


    private ArrayList<ChessMove> moveUntil(ChessBoard board, ChessPosition myPosition, int row_direction, int col_direction, ChessGame.TeamColor myColor){
        int curr_row = myPosition.getRow() + row_direction;
        int curr_col = myPosition.getColumn() + col_direction;
        ArrayList<ChessMove> moves = new ArrayList<ChessMove>();

        while (onBoard(curr_row,curr_col)){
            chess.ChessPosition currPos = new ChessPosition(curr_row, curr_col);
            if (board.getPiece(currPos) == null){
                moves.add(new ChessMove(myPosition,currPos,null));
            } else if (board.getPiece(currPos).getTeamColor() != myColor){
                moves.add(new ChessMove(myPosition,currPos,null));
                break;
            } else {
                break;
            }
            curr_row = curr_row + row_direction;
            curr_col = curr_col + col_direction;
        }
        return moves;
    }

    private boolean onBoard(int row, int col){
        if(row >= 1 & row <=8 & col >= 1 & col <= 8){return true;}
        else {return false;}
    }


}