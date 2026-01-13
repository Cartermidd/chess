package chess;

public class BishopMoves {

    public getBishopMoves(ChessBoard board, ChessPosition myPosition){

        private final playercolor = ChessPiece.getTeamColor();
        int [][] moves_list;



        return moves_list;
    }

    private up_right(ChessBoard board, ChessPosition myPosition){
        curr_row=;
        curr_col=;
        while (curr_row < 8 & curr_col < 8){
            //curr_row++ curr_col++(go up and right)
            //if there is a piece there
                //if my color is the same as piece
                    //break
                //else (opponent color)
                    //add to list
                    //break
            //add to list
        }
    }

    private down_right(ChessBoard board, ChessPosition myPosition){
        curr_row=;
        curr_col=;
        while (curr_row > 0 & curr_col < 8){
            //curr_row-- curr_col++(go down and right)
            //if there is a piece there
                //if my color is the same as piece
                    //break
                //else (opponent color)
                    //add to list
                    //break
            //add to list
        }
    }

    private up_left(ChessBoard board, ChessPosition myPosition){
        curr_row=;
        curr_col=;
        while (curr_row < 8 & curr_col > 0){
            //curr_row++ curr_col--(go up and left)
            //if there is a piece there
                //if my color is the same as piece
                    //break
                //else (opponent color)
                    //add to list
                    //break
            //add to list
        }
    }

    private down_left(ChessBoard board, ChessPosition myPosition){
        curr_row=;
        curr_col=;
        while (curr_row > 0 & curr_col > 0){
            //curr_row-- curr_col--(go up and left)
            //if there is a piece there
                //if my color is the same as piece
                    //break
                //else (opponent color)
                    //add to list
                    //break
            //add to list
        }
    }

}
