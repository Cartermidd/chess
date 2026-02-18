package server;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class handlerExample implements Handler {
    public void handle(Context context){

        context.json("");
        context.header("");
        context.result("");

    }
}


endpoint handlers

        get("/", (ctx) -> {
        })

        server.before(context -> //I can use the before to handle authTokens
        )