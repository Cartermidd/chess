package client;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import models.GameData;


import java.util.*;

import static ui.EscapeSequences.*;


public class GameplayClient {
        ServerFacade server;
        State state;
        GameData gameData;


        public GameplayClient(ServerFacade server) {
            this.server = server;
        }


        public void run(String userName, State state, GameData gameData){
            this.state = state;
            this.gameData = gameData;
            System.out.print(help());
            System.out.print(printBoard(state, gameData.game().getBoard()));

            Scanner scanner = new Scanner(System.in);
            var result = "";
            while (!result.equals("quit")&&!result.equals("q")){
                printPrompt();
                String line = scanner.nextLine();

                try {
                    result = eval(line);
                    System.out.print(result);

                } catch (Exception ex){
                    throw new RuntimeException(ex.getMessage());
                }

            }
        }

        public String eval(String input){
            try {
                String[] tokens = input.toLowerCase().split(" ");
                String cmd = (tokens.length > 0) ? tokens[0] : "help";
                String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
                return switch (cmd) {
                    case "highlight", "h" -> highlight(params);
                    case "move", "m" -> move(params);
                    case "redraw", "r" -> redraw(gameData.game().getBoard());
                    case "resign", "s" -> resign();
                    case "leave", "l" -> leave();
                    default -> help();
                };
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        public String highlight(String[] params){
            return "highlighting not yet implemented";
        }

        public String move(String[] params){
            return "making moves is not yet implemented";
        }

        public String redraw(ChessBoard board) {
            return (state == State.BLACK) ? printBoardBlack(board) : printBoardWhite(board);
        }

        private String printBoard(State view, ChessBoard board){
            if (view == State.WHITE){
                return printBoardWhite(board);
            } else {
                return printBoardBlack(board);
            }
        }

        private String printBoardWhite(ChessBoard board){


            String[] columnLabels = {EMPTY," a "," b  "," c ","  d  "," e "," f  "," g "," h ",EMPTY};
            String[] rowLabels = {" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "};

            StringBuilder printBoard = new StringBuilder();

            for (String string : columnLabels){
                printBoard.append(SET_BG_COLOR_BLUE).append(SET_TEXT_COLOR_BLACK).append(string);
            }

            for (int i = 8; i>0; i--){
                printBoard.append(RESET_BG_COLOR).append("\n").append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(rowLabels[i-1]);
                for (int j=8; j>0; j--){
                    printBoard.append(pieceChecker(board,i,j));
                }
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(rowLabels[i-1]).append(RESET_BG_COLOR);
            }
            printBoard.append("\n");
            for (String string : columnLabels){
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(string).append(RESET_BG_COLOR);
            }

            return printBoard.toString();
        }

        private String printBoardBlack(ChessBoard board){
            String[] columnLabels = {EMPTY," h  "," g ","  f "," e ","  d "," c ","  b "," a ",EMPTY};
            String[] rowLabels = {" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "};

            StringBuilder printBoard = new StringBuilder();
            for (String string : columnLabels){
                printBoard.append(SET_BG_COLOR_BLUE).append(SET_TEXT_COLOR_BLACK).append(string);
            }

            for (int i = 1; i<9; i++){
                printBoard.append(RESET_BG_COLOR).append("\n").append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(rowLabels[i-1]);
                for (int j=1; j<9; j++){
                    printBoard.append(pieceChecker(board,i,j));
                }
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(rowLabels[i-1]).append(RESET_BG_COLOR);
            }

            printBoard.append("\n");
            for (String string : columnLabels){
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(string).append(RESET_BG_COLOR);
            }
            return printBoard.toString();
        }

        private String pieceChecker(ChessBoard board, int row, int col){
            boolean lightSquare = (row+col) % 2 == 0;
            String bgColor = lightSquare ? SET_BG_COLOR_DARK_GREY : SET_BG_COLOR_LIGHT_GREY;
            ChessPiece piece = board.getPiece(new ChessPosition(row, col));

            if (piece == null){
                return bgColor + EMPTY;
            } else if (piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                var type = piece.getPieceType();
                if (type == ChessPiece.PieceType.PAWN){
                    return bgColor + SET_TEXT_COLOR_WHITE + WHITE_PAWN;
                } else if(type == ChessPiece.PieceType.KING){
                    return bgColor + SET_TEXT_COLOR_WHITE + WHITE_KING;
                } else if(type == ChessPiece.PieceType.QUEEN){
                    return bgColor + SET_TEXT_COLOR_WHITE + WHITE_QUEEN;
                } else if(type == ChessPiece.PieceType.BISHOP){
                    return bgColor + SET_TEXT_COLOR_WHITE + WHITE_BISHOP;
                } else if(type == ChessPiece.PieceType.KNIGHT){
                    return bgColor + SET_TEXT_COLOR_WHITE + WHITE_KNIGHT;
                } else {
                    return bgColor + SET_TEXT_COLOR_WHITE + WHITE_ROOK;
                }
            } else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                var type = piece.getPieceType();
                if (type == ChessPiece.PieceType.PAWN){
                    return bgColor + SET_TEXT_COLOR_BLACK + BLACK_PAWN;
                } else if(type == ChessPiece.PieceType.KING){
                    return bgColor + SET_TEXT_COLOR_BLACK + BLACK_KING;
                } else if(type == ChessPiece.PieceType.QUEEN){
                    return bgColor + SET_TEXT_COLOR_BLACK + BLACK_QUEEN;
                } else if(type == ChessPiece.PieceType.BISHOP){
                    return bgColor + SET_TEXT_COLOR_BLACK + BLACK_BISHOP;
                } else if(type == ChessPiece.PieceType.KNIGHT){
                    return bgColor + SET_TEXT_COLOR_BLACK + BLACK_KNIGHT;
                } else {
                    return bgColor + SET_TEXT_COLOR_BLACK + BLACK_ROOK;
                }
            }
            return "";
        }


        public String resign() {
            if (state == State.OBSERVER) {
                return "An observer can't resign!!";
            }
            return "resign functionality not yet implemented";
        }

        public String leave() throws Exception{
                return "quit";
            }

        private String help(){
            return """
                
                Options:
                Highlight legal moves: 'highlight' <position> (e.g. g3)
                Make a move: 'move' <current postion> <destination> <promotion (if needed)> (e.g. g7 h8 q)
                Redraw Chess Board: "redraw"
                Resign: "resign"
                Leave game: "leave"
                To print a list of possible commands: 'help'
                """;
        }

    private static void printPrompt() {
        System.out.print("\n" + "\u001b[" + "0m" + "Chess Game >>> " + "\u001b[" + "32m");
    }

}
