package client;

import com.google.gson.Gson;
import exceptions.IncorrectPasswordException;
import exceptions.UserDoesNotExistException;
import results.*;
import requests.*;
import server.ErrorTranslator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;
    private String authToken = null;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    //                    .post("/user", registerHandler::register)
    public RegisterResult register(RegisterRequest input) throws Exception {
        var request = buildRequest("POST", "/user", authToken, input);
        var response = sendRequest(request);
        RegisterResult result = handleResponse(response, RegisterResult.class);
        if (result != null){
            authToken = result.getAuthToken();
        }
        return result;
    }

    //                .post("/session", loginHandler::login)
    public LoginResult login(LoginRequest input) throws IncorrectPasswordException, UserDoesNotExistException, Exception {
        var request = buildRequest("POST", "/session", authToken, input);
        var response = sendRequest(request);
        LoginResult result = handleResponse(response, LoginResult.class);
        if (result != null){
            authToken = result.getAuthToken();
        }
        return result;
    }

    //                .delete("/session", logoutHandler::logout)
    public GenericSuccessfulResult logout(AuthorizedRequest input) throws Exception {
        var request = buildRequest("DELETE", "/session", authToken, null);
        var response = sendRequest(request);
        GenericSuccessfulResult result = handleResponse(response, GenericSuccessfulResult.class);
        authToken = null;
        return result;
    }

    //                .get("/game", listGamesHandler::listGames)
    public ListGamesResult listGames(AuthorizedRequest input) throws Exception {
        var request = buildRequest("GET", "/game", authToken, null);
        var response = sendRequest(request);
        return handleResponse(response, ListGamesResult.class);
    }

    //                .post("/game", createGameHandler::createGame)
    public CreateGameResult createGame(CreateGameRequest input) throws Exception {
        var request = buildRequest("POST", "/game", authToken, input);
        var response = sendRequest(request);
        return handleResponse(response, CreateGameResult.class);
    }

    //                .put("/game", joinGameHandler::joinGame)
    public GenericSuccessfulResult joinGame(JoinGameRequest input) throws Exception {
        var request = buildRequest("PUT", "/game", authToken, input);
        var response = sendRequest(request);
        return handleResponse(response, GenericSuccessfulResult.class);
    }

    //                .delete("/db", clearHandler::clear);
    public void clear() throws Exception {
        var request = buildRequest("DELETE", "/db", null, null);
        sendRequest(request);
    }


    private HttpRequest buildRequest(String method, String path, String authToken, Object input) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path));
        if (authToken != null){
            request.header("Authorization", authToken);
        }
        if (input != null){
            request.header("Content-Type", "application/json");
            request.method(method, BodyPublishers.ofString(new Gson().toJson(input)));
        } else {
            request.method(method, BodyPublishers.noBody());
        }

        return request.build();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) throws Exception {
        try {
            return client.send(request, BodyHandlers.ofString());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private <T> T handleResponse(HttpResponse<String> response, Class<T> responseClass) throws Exception {
        var status = response.statusCode();
        if (!isSuccessful(status)) {
            var body = response.body();
            if (body != null) {
                throw new Exception(parseMessage(body));
            }

            throw new Exception(String.valueOf(status));
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }

    private String parseMessage(String json){
        return new Gson().fromJson(json, ErrorTranslator.class).getMessage();
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
