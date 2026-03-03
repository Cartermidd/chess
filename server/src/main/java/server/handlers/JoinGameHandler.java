package server.handlers;

import service.GameService;

public class JoinGameHandler {
    GameService gameService;

    public JoinGameHandler(GameService gameService) {
        this.gameService = gameService;
    }
}
