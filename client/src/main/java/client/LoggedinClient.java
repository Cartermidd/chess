package client;

import chess.ChessGame;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import exceptions.NoGameException;
import models.GameData;
import requests.AuthorizedRequest;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import results.ListGamesResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_RED;

public class LoggedinClient {
    ServerFacade server;
    String authToken = null;
    String userName = null;
    Map<Integer, GameData> gamesList = new HashMap<>();

    public LoggedinClient(ServerFacade server) {
        this.server = server;
    }


    public void run(String authToken, String userName){
        this.authToken = authToken;
        this.userName = userName;
        System.out.printf("Welcome %s!", userName);
        System.out.print(help());

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
                case "play", "p" -> play(params);
                case "observe", "o" -> observe(params);
                case "create", "c" -> create(params);
                case "list", "i" -> list();
                case "logout", "l" -> logout();
                default -> help();
            };
        } catch (Exception e) {
            return formatError(e.getMessage());
        }
    }

    public String play(String[] params) throws Exception {
        //print game
        //'join' <GAME NUMBER> <COLOR: "black" | "white">
        // Open GameplayClient
        JoinGameRequest request = new JoinGameRequest(params);
        if(JoinGameRequest.misformatted(request)){
            throw new ImproperRequestException("Misformatted Request - Expected: 'play' <GAME NUMBER> <'black' | 'white'>");
        }
        try {
            if (gamesList.containsKey(request.getGameId())){
                GameData data = gamesList.get(request.getGameId());
                int id = data.gameID();
                server.joinGame(new JoinGameRequest(request.getPlayerColor(), id));

                GameplayClient gameplay = new GameplayClient(server);
                if (request.getPlayerColor() == ChessGame.TeamColor.BLACK){
                    gameplay.run(userName, State.BLACK, data);
                } else if (request.getPlayerColor() == ChessGame.TeamColor.WHITE){
                    gameplay.run(userName, State.WHITE, data);
                } else {
                    return formatError("Something went wrong in gameplay request");
                }
            }else{
                return formatError("Game does not exist");
            }
        } catch (AlreadyTakenException ex){
            return formatError(ex.getMessage());
        } catch (NoGameException ex){
            return formatError(ex.getMessage());
        }
        return "Exiting Game" + help();
    }

    public String observe(String[] params) throws Exception{
//        Observe game: 'observe' <GAME NUMBER>
        if(params.length < 1){
            throw new ImproperRequestException("Misformatted Request - Expected: 'observe' <GAME NUMBER>");
        }
        GameplayClient gameplay = new GameplayClient(server);
        try {
            if (gamesList.containsKey(Integer.parseInt(params[0]))) {
                GameData data = gamesList.get(Integer.parseInt(params[0]));
                gameplay.run(userName, State.OBSERVER, data);
            } else {
                return formatError("Game does not exist");
            }
        } catch (Exception ex){
            return formatError(ex.getMessage());
        }
        return "";
    }

    public String create(String[] params) throws Exception{
        try{
            CreateGameRequest request = new CreateGameRequest(params);
            if(CreateGameRequest.misformatted(request)){
                throw new ImproperRequestException("Misformatted Request - Expected: 'create' <GAME NAME>");
            }
            server.createGame(request);
            return "New game created '" + request.getGameName() + "'";
        }catch (Exception ex){
            return formatError(ex.getMessage());
        }
    }

    public String list() throws Exception{
        try{
            AuthorizedRequest request = new AuthorizedRequest(authToken);
            if(AuthorizedRequest.misformatted(request)){
                throw new ImproperRequestException("Misformatted Request - Unauthorized");
            }
            ListGamesResult result = server.listGames(request);
            int i = 1;
            for (GameData game : result.getGames()){
                gamesList.put(i,game);
                i++;
            }
            String stringList = "Games:";
            for (Map.Entry<Integer, GameData> entry : gamesList.entrySet()){
                GameData data = entry.getValue();
                String white = (data.whiteUsername() != null) ? data.whiteUsername() : "empty";
                String black = (data.blackUsername() != null) ? data.blackUsername() : "empty";
                String game = "\n" + entry.getKey().toString() + ". Game name: " + data.gameName() + "      White: " + white + "     Black: " + black;
                stringList = stringList.concat(game);
            }
            return stringList;
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    public String logout() throws Exception{
        try{
            AuthorizedRequest request = new AuthorizedRequest(authToken);
            if(AuthorizedRequest.misformatted(request)){
                throw new ImproperRequestException("Misformatted Request - Unauthorized");
            }
            server.logout(request);
            return "quit";
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    private String help(){
        return """
                
                Options:
                Play game: 'play' <GAME NUMBER> <COLOR: "black" | "white">
                Observe game: 'observe' <GAME NUMBER>
                Create new game: 'create' <GAME NAME>
                List all current games: 'list'
                Logout of current account: 'logout'
                To print a list of possible commands: 'help'
                *HINT*
                Look at the list games before trying to join or observe!
                """;
    }

    private String formatError(String error){
        return SET_TEXT_COLOR_RED + error + RESET_TEXT_COLOR + "\n";
    }


    private static void printPrompt() {
        System.out.print("\n" + "\u001b[" + "0m" + "Chess >>> " + "\u001b[" + "32m");
    }

}
