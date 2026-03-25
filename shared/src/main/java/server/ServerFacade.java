package server;

import com.google.gson.Gson;
import requests.*;
import results.*;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

public class ServerFacade {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }


    //                    .post("/user", registerHandler::register)
    public RegisterResult register(RegisterRequest input) throws Exception {
        var request = buildRequest("POST", "/user", null, input);
        var response = sendRequest(request);
        return handleResponse(response, RegisterResult.class);
    }

    //                .post("/session", loginHandler::login)
    public LoginResult login(LoginRequest input) throws Exception {
        var request = buildRequest("POST", "/session", null, input);
        var response = sendRequest(request);
        return handleResponse(response, LoginResult.class);
    }

    //                .delete("/session", logoutHandler::logout)
    public GenericSuccessfulResult logout(AuthorizedRequest input) throws Exception {
        var request = buildRequest("DELETE", "/session", input.getAuthToken(), null);
        var response = sendRequest(request);
        return handleResponse(response, GenericSuccessfulResult.class);
    }

    //                .get("/game", listGamesHandler::listGames)
    public ListGamesResult listGames(AuthorizedRequest input) throws Exception {
        var request = buildRequest("GET", "/game", input.getAuthToken(), null);
        var response = sendRequest(request);
        return handleResponse(response, ListGamesResult.class);
    }

    //                .post("/game", createGameHandler::createGame)
    public CreateGameResult createGame(CreateGameRequest input) throws Exception {
        var request = buildRequest("POST", "/game", input.getAuthToken(), input);
        var response = sendRequest(request);
        return handleResponse(response, CreateGameResult.class);
    }

    //                .put("/game", joinGameHandler::joinGame)
    public GenericSuccessfulResult joinGame(JoinGameRequest input) throws Exception {
        var request = buildRequest("PUT", "/game", input.getAuthToken(), input);
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
                throw new Exception(body.toString());
            }

            throw new Exception(String.valueOf(status));
        }

        if (responseClass != null) {
            return new Gson().fromJson(response.body(), responseClass);
        }

        return null;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
