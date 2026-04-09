package client;


import java.util.Arrays;
import java.util.Scanner;

import exceptions.AlreadyTakenException;
import exceptions.IncorrectPasswordException;
import requests.RegisterRequest;
import exceptions.UserDoesNotExistException;
import requests.*;
import results.*;
import server.ServerFacade;

import static ui.EscapeSequences.RESET_TEXT_COLOR;
import static ui.EscapeSequences.SET_TEXT_COLOR_RED;

public class PreloginClient {
    private final ServerFacade server;
    private final String serverUrl;

    public PreloginClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public void run(){
        System.out.println("♕ Welcome to Chess! ♕");
        System.out.print(help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")&!result.equals("q")){
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = eval(line);
                System.out.print(result);

            } catch (Exception ex){
                System.out.print(SET_TEXT_COLOR_RED + "Error: " + ex.getMessage() + RESET_TEXT_COLOR + "\n");
            }
        }
    }


    public String eval(String input){
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login", "l" -> {
                    yield login(params) + "\n \n" + help();
                }
                case "quit", "q" -> quit();
                case "register", "r" -> register(params);
                default -> help();
            };
        } catch (Exception e) {
            return formatError(e.getMessage());
        }

    }

    public String login(String[] params){
        try {
            if (params.length != 2) {
                return formatError("Misformatted Request - Expected: 'login' <USERNAME> <PASSWORD>");
            }
            LoginRequest request = new LoginRequest(params);
            if (LoginRequest.misformatted(request)) {
                return formatError("Misformatted Request - Expected: 'login' <USERNAME> <PASSWORD>");
            }
            var result = server.login(request);
            LoggedinClient logged = new LoggedinClient(server, serverUrl);
            logged.run(result.getAuthToken(), result.getUsername());
        } catch (IncorrectPasswordException ex) {
            return formatError("Incorrect Password");
        } catch (UserDoesNotExistException ex) {
            return formatError("User does not exist!");
        } catch (Exception ex) {
            return formatError(ex.getMessage());
        }
        return "\nLogging Out";
    }

    public String register(String[] params) throws Exception {
        try{
            if (params.length != 3){
                return formatError("Misformatted Request - Expected: 'register' <username> <password> <email>");
            }
            RegisterRequest request = new RegisterRequest(params);
            if (RegisterRequest.misformatted(request)){
                return formatError("Misformatted Request - Expected: 'register' <username> <password> <email>");
            }
            RegisterResult result = server.register(request);
            LoggedinClient logged = new LoggedinClient(server, serverUrl);
            logged.run(result.getAuthToken(), result.getUsername());
            return "";
        } catch (AlreadyTakenException ex){
            return formatError("Username already taken!");
        } catch (Exception ex) {
            return formatError(ex.getMessage());
        }
    }



    public String help(){
        return """
                Options:
                Register a new user: 'register' <USERNAME> <PASSWORD> <EMAIL>
                Login as an existing user: 'login' <USERNAME> <PASSWORD>
                Exit program: 'quit'
                To print a list of possible commands: 'help'
                """;
    }


    public static void printPrompt() {
        System.out.print("\n" + "\u001b[" + "0m" + "Chess Login >>> " + "\u001b[" + "32m");
    }

    private String formatError(String error){
        return SET_TEXT_COLOR_RED + error + RESET_TEXT_COLOR + "\n";
    }

    public static String quit(){
        System.exit(0);
        return "quit";
    }

}
