package server.websocket;

import com.google.gson.Gson;
import io.javalin.websocket.WsContext;
import websocket.messages.ServerMessage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    private final ConcurrentHashMap<Integer, ArrayList<WsContext>> connections = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    public void add(int gameID, WsContext ctx) {
        connections.computeIfAbsent(gameID, k -> new ArrayList<>()).add(ctx);
    }

    public void remove(int gameID, WsContext ctx) {
        var game = connections.get(gameID);
        if (game != null) {
            game.removeIf(c -> c.session.equals(ctx.session));
        }
    }

    public void broadcast(int gameID, WsContext excludeCtx, ServerMessage message) {
        var game = connections.get(gameID);
        if (game == null) { return; }
        String json = gson.toJson(message);
        for (WsContext c : game) {
            if (c.session.isOpen()) {
                if (excludeCtx == null || !c.session.equals(excludeCtx.session)) {
                    c.send(json);
                }
            }
        }
    }
}