package chess;
import java.lang.Math;

public class CheckChecker {
    public static boolean inCheck(ChessBoard board, ChessGame.TeamColor teamColor) {
        ChessPosition king = findKing(board, teamColor);
        int kingRow = king.getRow();
        int kingCol = king.getColumn();

        if (checkHorses(board, teamColor, kingRow, kingCol)) {
            return true;
        }
        if (checkStraight(board, teamColor, kingRow, kingCol)) {
            return true;
        }
        if (checkDiagonal(board, teamColor, kingRow, kingCol)) {
            return true;
        }
        if (inRangeofOtherKing(board,teamColor, kingRow, kingCol)){
            return true;
        }
        return false;
    }

    private static boolean inRangeofOtherKing(ChessBoard board, ChessGame.TeamColor teamColor, int row, int col) {
        ChessGame.TeamColor otherTeam;
        if(teamColor == ChessGame.TeamColor.BLACK){otherTeam = ChessGame.TeamColor.WHITE;} else{otherTeam = ChessGame.TeamColor.BLACK;}
        ChessPosition otherKing = findKing(board, otherTeam);
        int otherRow = otherKing.getRow();
        int otherCol = otherKing.getColumn();

        if(Math.abs(row - otherRow) <= 1 & Math.abs(col - otherCol) <= 1){
            return true;
        } else {
            return false;
        }
    }

    private static ChessPosition findKing(ChessBoard board, ChessGame.TeamColor teamColor) throws RuntimeException {
        for (int i = 1; i <=8; i++){
            for (int j = 1; j <=8; j++){
                ChessPiece piece = board.getPiece(new ChessPosition(i,j));
                if (piece != null){
                    if (piece.getTeamColor() == teamColor && piece.getPieceType() == ChessPiece.PieceType.KING){
                    return new ChessPosition(i,j);
                    }
                }
            }
        }
        throw new RuntimeException("no king of teamColor found");
    }

    private static boolean checkHorses(ChessBoard board, ChessGame.TeamColor teamColor, int kingRow, int kingCol) {
        if (horseHelper(board, teamColor, kingRow+2,kingCol+1)|horseHelper(board, teamColor, kingRow+1,kingCol+2)|horseHelper(board, teamColor, kingRow-1,kingCol+2)|horseHelper(board, teamColor, kingRow-2,kingCol+1)|horseHelper(board, teamColor, kingRow-2,kingCol-1)|horseHelper(board, teamColor, kingRow-1,kingCol-2)|horseHelper(board, teamColor, kingRow+1,kingCol-2)|horseHelper(board, teamColor, kingRow+2,kingCol-1)) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean horseHelper(ChessBoard board, ChessGame.TeamColor teamColor, int row, int col){
        if (inBounds(row,col)){
            chess.ChessPosition currPos = new ChessPosition(row, col);
            if (board.getPiece(currPos) == null){
                return false;
            } else if (board.getPiece(currPos).getTeamColor() != teamColor & board.getPiece(currPos).getPieceType() == ChessPiece.PieceType.KNIGHT){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private static boolean checkStraight(ChessBoard board, ChessGame.TeamColor teamColor, int kingRow, int kingCol) {
        if (inBounds(kingRow+1,kingCol)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow+1,kingCol, 1,0), ChessPiece.PieceType.ROOK)){return true;}
        }
        if (inBounds(kingRow-1,kingCol)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow-1,kingCol, -1,0), ChessPiece.PieceType.ROOK)){return true;}
        }
        if (inBounds(kingRow,kingCol+1)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow,kingCol+1, 0,1), ChessPiece.PieceType.ROOK)){return true;}
        }
        if (inBounds(kingRow,kingCol-1)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow,kingCol-1, 0,-1), ChessPiece.PieceType.ROOK)){return true;}
        }
        return false;
    }


    private static boolean checkDiagonal(ChessBoard board, ChessGame.TeamColor teamColor, int kingRow, int kingCol) {
        if(pawnHelper(board, teamColor, kingRow, kingCol)){
            return true;
        }
        if (inBounds(kingRow+1,kingCol+1)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow+1,kingCol+1, 1,1), ChessPiece.PieceType.BISHOP)){return true;}
        }
        if (inBounds(kingRow+1,kingCol-1)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow+1,kingCol-1, 1,-1), ChessPiece.PieceType.BISHOP)){return true;}
        }
        if (inBounds(kingRow-1,kingCol-1)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow-1,kingCol-1, -1,-1), ChessPiece.PieceType.BISHOP)){return true;}
        }
        if (inBounds(kingRow-1,kingCol+1)){
            if (positionChecker(board, teamColor, moveDirection(board, kingRow-1,kingCol+1, -1,1), ChessPiece.PieceType.BISHOP)){return true;}
        }
        return false;
    }

    private static ChessPosition moveDirection(ChessBoard board, int row, int col, int rowIncrease, int colIncrease){
        //get the chess piece (or last square still on the board) in a specific direction
        while (board.getPiece(new ChessPosition(row, col)) == null){
            if (!inBounds(row + rowIncrease, col + colIncrease)){break;}
            row = row + rowIncrease;
            col = col + colIncrease;
        }
        return new ChessPosition(row, col);
    }

    private static boolean positionChecker(ChessBoard board, ChessGame.TeamColor teamColor, ChessPosition endPos, ChessPiece.PieceType directionPiece) {
        if (board.getPiece(endPos) == null){return false;}
        if(board.getPiece(endPos).getTeamColor() != teamColor & (board.getPiece(endPos).getPieceType() == directionPiece | board.getPiece(endPos).getPieceType() == ChessPiece.PieceType.QUEEN)){return true;} else {return false;}
    }


    private static boolean pawnHelper(ChessBoard board, ChessGame.TeamColor teamColor, int kingRow, int kingCol) {
        if (teamColor == ChessGame.TeamColor.WHITE){
            if (inBounds(kingRow+1, kingCol+1)){
                chess.ChessPosition currPos = new ChessPosition(kingRow+1, kingCol+1);
                if (board.getPiece(currPos) == null){}
                else if (board.getPiece(currPos).getTeamColor() != teamColor & board.getPiece(currPos).getPieceType() == ChessPiece.PieceType.PAWN){return true;}
            }
            if (inBounds(kingRow+1, kingCol-1)){
                chess.ChessPosition currPos = new ChessPosition(kingRow+1, kingCol-1);
                if (board.getPiece(currPos) == null){}
                else if (board.getPiece(currPos).getTeamColor() != teamColor & board.getPiece(currPos).getPieceType() == ChessPiece.PieceType.PAWN){return true;}
            }
        }
        if (teamColor == ChessGame.TeamColor.BLACK){
            if (inBounds(kingRow-1, kingCol+1)){
                chess.ChessPosition currPos = new ChessPosition(kingRow-1, kingCol+1);
                if (board.getPiece(currPos) == null){}
                else if (board.getPiece(currPos).getTeamColor() != teamColor & board.getPiece(currPos).getPieceType() == ChessPiece.PieceType.PAWN){return true;}
            }
            if (inBounds(kingRow-1, kingCol-1)){
                chess.ChessPosition currPos = new ChessPosition(kingRow-1, kingCol-1);
                if (board.getPiece(currPos) == null){}
                else if (board.getPiece(currPos).getTeamColor() != teamColor & board.getPiece(currPos).getPieceType() == ChessPiece.PieceType.PAWN){return true;}
            }
        }
        return false;
    }

    private static boolean inBounds(int row, int col){
        return col <= 8 & col >= 1 & row <= 8 & row >= 1;
    }

}