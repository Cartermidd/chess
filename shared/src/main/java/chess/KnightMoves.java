package chess;

public class KnightMoves {

    //how does the horse even move lol

    //append to list two_up_left
    //append to list two_up_right
    //append to list two_right_up
    //append to list two_right_down
    //append to list two_down_right
    //append to list two_down_left
    //append to list two_left_down
    //append to list two_left_up


    int curr_row = myPosition.getRow();
    int curr_col = myPosition.getColumn();

    private two_up_left(ChessBoard board, ChessPosition myPosition){
        if (curr_row + 2 > 8) {
            return;
        }
        if (curr_col - 1 < 1){
            return;
        }
        //if (curr... has piece){
            //if piece = my color
                //return
        return [curr_row+2, curr_col-1];
    }


    private two_up_right(ChessBoard board, ChessPosition myPosition){
        if (curr_row + 2 > 8) {
            return;
        }
        if (curr_col + 1 > 8){
            return;
        }
        //if (curr... has piece){
            //if piece = my color
            //return
        return [curr_row+2, curr_col+1];
    }


    private two_right_up(ChessBoard board, ChessPosition myPosition){
        if (curr_row + 1 > 8) {
            return;
        }
        if (curr_col + 2 > 8){
            return;
        }
        //if (curr... has piece){
            //if piece = my color
            //return
        return [curr_row+1, curr_col+2];
    }

    private two_right_down(ChessBoard board, ChessPosition myPosition){
        if (curr_row - 1 < 1) {
            return;
        }
        if (curr_col + 2 > 8){
            return;
        }
        //if (curr... has piece){
        //if piece = my color
        //return
        return [curr_row-1, curr_col+2];
    }

    private two_down_right(ChessBoard board, ChessPosition myPosition){
        if (curr_row - 2 < 1) {
            return;
        }
        if (curr_col + 1 > 8){
            return;
        }
        //if (curr... has piece){
        //if piece = my color
        //return
        return [curr_row-2, curr_col+1];
    }

    private two_down_left(ChessBoard board, ChessPosition myPosition){
        if (curr_row - 2 < 1) {
            return;
        }
        if (curr_col - 1 < 1){
            return;
        }
        //if (curr... has piece){
        //if piece = my color
        //return
        return [curr_row-2, curr_col-1];
    }

    private two_left_down(ChessBoard board, ChessPosition myPosition){
        if (curr_row - 1 < 1) {
            return;
        }
        if (curr_col - 2 < 1){
            return;
        }
        //if (curr... has piece){
        //if piece = my color
        //return
        return [curr_row-1, curr_col-2];
    }

    private two_left_down(ChessBoard board, ChessPosition myPosition){
        if (curr_row + 1 > 8) {
            return;
        }
        if (curr_col - 2 < 1){
            return;
        }
        //if (curr... has piece){
        //if piece = my color
        //return
        return [curr_row+1, curr_col-2];
    }

}
