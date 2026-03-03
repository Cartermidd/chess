package server.Handlers;

import dataaccess.DataAccessException;
import io.javalin.http.Context;
import server.exceptions.UnauthorizedException;
import service.Service;

public class LogoutHandler {
    Service service;

    public LogoutHandler(Service service){
        this.service = service;
    }

    public void logout(Context ctx) {
        try {
            ctx.result(service.logout().toString());
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        } catch (UnauthorizedException e){
            ctx.status(401);
            ctx.result("Error: unauthorized");
        }
    }
}
