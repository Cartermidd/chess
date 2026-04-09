package client;

import client.websocket.ServerMessageHandler;
import client.websocket.WebSocketFacade;
import exceptions.ImproperRequestException;
import exceptions.MisformattedChessPositionException;
import exceptions.ResponseException;
import models.GameData;
import models.chess.*;
import server.ServerFacade;
import websocket.State;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;


import java.util.*;

import static ui.EscapeSequences.*;


public class GameplayClient implements ServerMessageHandler {
        private ServerFacade server;
        private String serverUrl;
        private State state;
        private GameData gameData;
        private WebSocketFacade ws;
        private Boolean gameOver = false;
        private String authToken;
        private int id;

        public GameplayClient(ServerFacade server, String serverUrl, String authToken, int id, State state) {
            try {
                this.server = server;
                this.serverUrl = serverUrl;
                this.authToken = authToken;
                this.id = id;
                this.ws = new WebSocketFacade(serverUrl, this);
                this.state = state;
                this.ws.makeConnection(authToken,id,this.state);
            }catch (Exception ex){
                System.out.print(formatError("Failed to connect to server with the web socket"));
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

        public void run(String userName, State state, GameData gameData){
            //open a websocket connection using /ws endpoint, CONNECT websocket message to server
            this.gameData = gameData; //This should be received with the LOAD_GAME server message
            System.out.print(help());
            System.out.print(printBoard(state, gameData.game().getBoard(), null));

            Scanner scannr = new Scanner(System.in);
            var result = "";
            while (!result.equals("q")&&!result.equals("quit")){

                printPrompt();
                String line = scannr.nextLine();

                try {
                    result = eval(line);
                    System.out.print(result);

                } catch (Exception ex){
                    throw new RuntimeException(ex.getMessage());
                }

            }
        }


        public String highlight(String[] params) {
            //Highlight legal moves: 'highlight' <position> (e.g. g3)
            try {
                if(params.length != 1){
                    throw new ImproperRequestException("Misformatted Request - Expected: 'highlight' <position> (e.g. g3)");
                }
                ChessPosition position = positionParse(params[0]);
                if (gameData.game().getBoard().getPiece(position) == null){
                    return printBoard(state, gameData.game().getBoard(), null) + "\n" + formatError("Select a square with a piece on it to highlight available moves!");
                }
                Collection<ChessPosition> moveEndPositions = new ArrayList<>(List.of());
                moveEndPositions.add(position);
                Collection<ChessMove> validmoves = gameData.game().validMoves(position);
                if(validmoves != null){
                    for (ChessMove move : validmoves){
                        moveEndPositions.add(move.getEndPosition());
                    }
                }
                return printBoard(state, gameData.game().getBoard(), moveEndPositions);
            } catch (Exception ex){
                return formatError(ex.getMessage());
            }
        }

        public String move(String[] params){
            //updates board after successful move //needs websocket implementation
            //'move' <current position> <destination> <promotion (if needed)> (e.g. g7 h8 q)
            if(gameOver){return formatError("Can't make moves when the game is over");}
            try {
                if(params.length < 2 | params.length > 3){
                    throw new ImproperRequestException("Misformatted Request - Expected: 'move' <current position> <destination> <promotion (if needed)> (e.g. g7 h8 q)");
                }

                ChessPosition startPosition = positionParse(params[0]);
                ChessPosition endPosition = positionParse(params[1]);
                ChessPiece.PieceType promotion = null;
                if (params.length == 3) {
                    promotion = promotionPieceParce(params[2]);
                }
                ChessMove move = new ChessMove(startPosition,endPosition,promotion);
                try {
                    gameData.game().makeMove(move);
                }catch (InvalidMoveException ex){
                    return formatError(ex.getMessage());
                }
                ws.makeMove(authToken,id,move, state);
                if (gameData.game().isInCheck(ChessGame.TeamColor.BLACK)){
                    return printBoard(state, gameData.game().getBoard(), null);
                } else if (gameData.game().isInCheck(ChessGame.TeamColor.WHITE)){
                    return printBoard(state, gameData.game().getBoard(), null);
                }
                if (gameData.game().isInCheckmate(ChessGame.TeamColor.BLACK)){
                    gameOver = true;
                    return printBoard(state, gameData.game().getBoard(), null);
                } else if (gameData.game().isInCheckmate(ChessGame.TeamColor.WHITE)){
                    gameOver = true;
                    return printBoard(state, gameData.game().getBoard(), null);
                }
                if (gameData.game().isInStalemate(ChessGame.TeamColor.BLACK)){
                    gameOver = true;
                    return printBoard(state, gameData.game().getBoard(), null);
                }
                return printBoard(state, gameData.game().getBoard(), null);
            } catch (Exception ex){
                return formatError(ex.getMessage());
            }
        }



    public String redraw(ChessBoard board) {
            return (state == State.BLACK) ? printBoardBlack(board, null) : printBoardWhite(board, null);
        }

        private String printBoard(State view, ChessBoard board, Collection<ChessPosition> positions){
            if (view == State.BLACK){
                return printBoardBlack(board, positions);
            } else {
                return printBoardWhite(board, positions);
            }
        }

        private String printBoardWhite(ChessBoard board, Collection<ChessPosition> positions){


            String[] columnLabels = {EMPTY," a "," b  "," c ","  d  "," e "," f  "," g "," h ",EMPTY};
            String[] rowLabels = {" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "};

            StringBuilder printBoard = new StringBuilder();

            for (String string : columnLabels){
                printBoard.append(SET_BG_COLOR_BLUE).append(SET_TEXT_COLOR_BLACK).append(string);
            }

            for (int i = 8; i>0; i--){
                printBoard.append(RESET_BG_COLOR).append("\n").append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(rowLabels[i-1]);
                for (int j=1; j<9; j++){
                    printBoard.append(pieceChecker(board,i,j,positions));
                }
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE)
                        .append(rowLabels[i-1]).append(RESET_BG_COLOR);
            }
            printBoard.append("\n");
            for (String string : columnLabels){
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(string).append(RESET_BG_COLOR);
            }

            return printBoard.toString();
        }

        private String printBoardBlack(ChessBoard board, Collection<ChessPosition> positions){
            String[] columnLabels = {EMPTY," h  "," g ","  f "," e ","  d "," c ","  b "," a ",EMPTY};
            String[] rowLabels = {" 1 "," 2 "," 3 "," 4 "," 5 "," 6 "," 7 "," 8 "};

            StringBuilder printBoard = new StringBuilder();
            for (String string : columnLabels){
                printBoard.append(SET_BG_COLOR_BLUE).append(SET_TEXT_COLOR_BLACK).append(string);
            }

            for (int i = 1; i<9; i++){
                printBoard.append(RESET_BG_COLOR).append("\n").append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(rowLabels[i-1]);
                for (int j=8; j>0; j--){
                    printBoard.append(pieceChecker(board,i,j,positions));
                }
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE)
                        .append(rowLabels[i-1]).append(RESET_BG_COLOR);
            }

            printBoard.append("\n");
            for (String string : columnLabels){
                printBoard.append(RESET_BG_COLOR).append(SET_TEXT_COLOR_BLACK).append(SET_BG_COLOR_BLUE).append(string).append(RESET_BG_COLOR);
            }
            return printBoard.toString();
        }

        private String pieceChecker(ChessBoard board, int row, int col, Collection<ChessPosition> positions) {
            boolean lightSquare = (row + col) % 2 != 1;
            String bgColor;
            ChessPiece piece = board.getPiece(new ChessPosition(row, col));
            if (positions == null) {
                bgColor = lightSquare ? SET_BG_COLOR_DARK_GREY : SET_BG_COLOR_LIGHT_GREY;
            } else {
                if (positions.contains(new ChessPosition(row, col))){
                    bgColor = lightSquare ? SET_BG_COLOR_DARK_GREEN : SET_BG_COLOR_GREEN;
                } else {
                    bgColor = lightSquare ? SET_BG_COLOR_DARK_GREY : SET_BG_COLOR_LIGHT_GREY;
                }
            }


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
            if(gameOver){return formatError("Can't resign when the game is over");}
            if (state == State.OBSERVER) {
                return "An observer can't resign!!";
            }
            try{//asks user if they want to resign, if yes, forfeit game
            System.out.print("Are you sure you want to resign?\n('yes' will end the game, 'no' will continue the game)");
            Scanner scannr = new Scanner(System.in);
            var result = "";
            while (!result.equals("Continuing game")&&!result.equals("game over.")){
                printPrompt();
                String line = scannr.nextLine();

                try {
                    result = resignEval(line);
                    System.out.print(result);
                } catch (Exception ex) {
                    System.out.print(formatError(ex.getMessage()));
                }
            }}catch (Exception ex) {
                return formatError(ex.getMessage());
            }
            return "\n" + redraw(gameData.game().getBoard());
            //needs websocket implementation
        }

        private String resignEval(String input){
            try{
                String[] tokens = input.split(" ");
                String command = (tokens.length == 1) ? tokens[0] : "default";
                return switch (command) {
                    case "yes", "YES" -> gameLoss(state);
                    case "no", "NO" -> "Continuing game";
                    default -> "\nPlease type 'yes' to resign or 'no' to continue the game";
                };
                } catch (Exception ex){
                return formatError(ex.getMessage());
            }
        }

        private String gameLoss(State state){
            //Websocket -> teamColor has resigned
            gameOver = true;
            //no more games
            return "game over.";
        };


        public String leave() throws Exception{
                ws.leaveGame(authToken,id,state);
                return "quit";
            }

        private String help(){
            return """
                
                Options:
                Highlight legal moves: 'highlight' <position> (e.g. g3)
                Make a move: 'move' <current position> <destination> <promotion (if needed)> (e.g. g7 h8 q)
                Redraw Chess Board: "redraw"
                Resign: "resign"
                Leave game: "leave"
                To print a list of possible commands: 'help'
                """;
        }

    private String formatError(String error){
        return SET_TEXT_COLOR_RED + error + RESET_TEXT_COLOR + "\n";
    }

    private String formatCheck(String message){
            return SET_TEXT_COLOR_YELLOW  + message + RESET_TEXT_COLOR + "\n";
    }

    private String formatStalemate(String message){
        return SET_TEXT_COLOR_MAGENTA  + message + RESET_TEXT_COLOR + "\n";
    }

    private ChessPosition positionParse(String input) throws MisformattedChessPositionException {
        if (input.length() != 2){
            throw new MisformattedChessPositionException("Chess Position must be formatted column letter (a-h) row number (1-8) - (e.g. g7)");
        }
        int col = colLetterToInt(input);
        try {
            char rowNumber = input.charAt(1);
            int row = Integer.parseInt(String.valueOf(rowNumber));
            if (row > 8 | row < 1){throw new MisformattedChessPositionException("Chess Position must be formatted column letter (a-h) row number (1-8) - (e.g. g7)");}
            return new ChessPosition(row, col);
        }catch (NumberFormatException ex){
            throw new MisformattedChessPositionException("Chess Position must be formatted column letter (a-h) row number (1-8) - (e.g. g7)");
        }
    }

    private ChessPiece.PieceType promotionPieceParce(String piece) throws MisformattedChessPositionException {
        char pieceChar = piece.charAt(0);
        return switch (pieceChar) {
            case 'q', 'Q' -> ChessPiece.PieceType.QUEEN;
            case 'r', 'R' -> ChessPiece.PieceType.ROOK;
            case 'b', 'B' -> ChessPiece.PieceType.BISHOP;
            case 'k', 'K' -> ChessPiece.PieceType.KNIGHT;
            default -> throw new MisformattedChessPositionException("Promotion Chess Piece must be formatted with the first letter of a promotion piece (q,r,b,k)");
        };
    }

    private static int colLetterToInt(String input) throws MisformattedChessPositionException {
        char colLetter = input.charAt(0);
        return switch (colLetter) {
            case 'a', 'A' -> 1;
            case 'b', 'B' -> 2;
            case 'c', 'C' -> 3;
            case 'd', 'D' -> 4;
            case 'e', 'E' -> 5;
            case 'f', 'F' -> 6;
            case 'g', 'G' -> 7;
            case 'h', 'H' -> 8;
            default -> throw new MisformattedChessPositionException("Chess Position must be formatted column letter (a-h) row number (1-8) - (e.g. g7)");
        };
    }

    private static void printPrompt() {
        System.out.print("\n" + "\u001b[" + "0m" + "Chess Game >>> " + "\u001b[" + "32m");
    }


    @Override
    public void notify(ServerMessage message) {
        if (message.getServerMessageType() == ServerMessage.ServerMessageType.NOTIFICATION){
            NotificationMessage note = (NotificationMessage) message;
            System.out.println("\n" + SET_TEXT_COLOR_YELLOW + note.getMessage() + RESET_TEXT_COLOR);
            printPrompt();
        }
        if (message.getServerMessageType() == ServerMessage.ServerMessageType.LOAD_GAME){
            LoadGameMessage load = (LoadGameMessage) message;
            this.gameData = new GameData(
                    gameData.gameID(),
                    gameData.whiteUsername(),
                    gameData.blackUsername(),
                    gameData.gameName(),
                    load.getGame()
            );
            System.out.print(redraw(load.getGame().getBoard()));
            printPrompt();
        }
        if (message.getServerMessageType() == ServerMessage.ServerMessageType.ERROR){
            ErrorMessage error = (ErrorMessage) message;
            System.out.println(formatError(error.getErrorMessage()));
        }
    }
}
