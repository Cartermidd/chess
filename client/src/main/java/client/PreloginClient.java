package client;

import exceptions.ImproperRequestException;
import results.LoginResult;
import results.RegisterResult;
import server.ServerFacade;

import java.util.Arrays;
import java.util.Scanner;
import requests.*;


public class PreloginClient {
    private final ServerFacade server;

    public PreloginClient(String serverUrl) throws Exception {
        server = new ServerFacade(serverUrl);

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
                throw new RuntimeException(ex);
            }
        }
    }


    public String eval(String input){
        try {
            String[] tokens = input.toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "login", "l" -> login(params);
                case "quit", "q" -> quit();
                case "register", "r" -> register(params);
                default -> help();
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String login(String[] params) throws Exception {
        try{
            LoginRequest request = new LoginRequest(params);
            if(LoginRequest.misformatted(request)){
                throw new ImproperRequestException("Misformatted Request - Expected: login <username> <password>");
            }
            LoginResult result = server.login(request);
            LoggedinClient logged = new LoggedinClient(server);
            logged.run(result.getAuthToken(), result.getUsername());
            return "";
        }catch (Exception ex){
            throw new RuntimeException("Misformatted Request - Expected: login <username> <password>");
        }
    }

    private String register(String[] params) throws Exception {
        try{
            RegisterRequest request = new RegisterRequest(params);
            if(RegisterRequest.misformatted(request)){
                throw new ImproperRequestException("Misformatted Request - Expected: login <username> <password>");
            }
            RegisterResult result = server.register(request);
            LoggedinClient logged = new LoggedinClient(server);
            logged.run(result.getAuthToken(), result.getUsername());
            return "";
        }catch (Exception ex){
            throw new RuntimeException("Misformatted Request - Expected: login <username> <password>");
        }
    }



    private String help(){
        return """
                Options:
                Register a new user: 'register' <USERNAME> <PASSWORD> <EMAIL>
                Login as an existing user: 'login' <USERNAME> <PASSWORD>
                Exit program: 'quit'
                To print a list of possible commands: 'help'
                """;
    }


    private static void printPrompt() {
        System.out.print("\n" + "\u001b[" + "0m" + ">>> " + "\u001b[" + "32m");
    }


    private static String quit(){
        System.exit(0);
        return "Exited";
    }

}
