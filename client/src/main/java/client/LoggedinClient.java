package client;

import exceptions.ImproperRequestException;
import models.GameData;
import requests.AuthorizedRequest;
import requests.CreateGameRequest;
import requests.LoginRequest;
import results.CreateGameResult;
import results.GenericSuccessfulResult;
import results.ListGamesResult;
import results.LoginResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoggedinClient {
    ServerFacade server;
    String authToken = null;
    Map<Integer, GameData> gamesList = new HashMap<>();

    public LoggedinClient(ServerFacade server) throws Exception {
        this.server = server;
    }


    public void run(String authToken, String userName){
        this.authToken = authToken;
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
                case "join", "j" -> join(params);
                case "observe", "o" -> observe(params);
                case "create", "c" -> create(params);
                case "list", "i" -> list();
                case "logout", "l" -> logout();
                default -> help();
            };
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String join(String[] params) throws Exception {
        //print game
        //'join' <GAME NUMBER> <COLOR: "black" | "white">
        // Open GameplayClient
        return "";
    }

    public String observe(String[] params) throws Exception{
        //print game
        return "";
    }

    public String create(String[] params) throws Exception{
        try{
            CreateGameRequest request = new CreateGameRequest(params);
            request.setAuthToken(authToken);
            if(CreateGameRequest.misformatted(request)){
                throw new ImproperRequestException("Misformatted Request - Expected: 'create' <GAME NAME>");
            }
            server.createGame(request);
            return "New game created '" + request.getGameName() + "'";
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
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
            return gamesList.toString();
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
                Join game: 'join' <GAME NUMBER> <COLOR: "black" | "white">
                Observe game: 'observe' <GAME NUMBER>
                Create new game: 'create' <GAME NAME>
                List all current games: 'list'
                Logout of current account: 'logout'
                To print a list of possible commands: 'help'
                """;
    }


    private static void printPrompt() {
        System.out.print("\n" + "\u001b[" + "0m" + ">>> " + "\u001b[" + "32m");
    }

}
