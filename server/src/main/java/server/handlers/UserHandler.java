package server.handlers;

import models.AuthData;
import models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import io.javalin.http.Context;
import service.*;


public class UserHandler {
    Service service;

    public UserHandler(Service service){
        this.service = service;
    }

    public void register(Context ctx) {
        try {
            UserData user = new Gson().fromJson(ctx.body(), UserData.class);
            AuthData result = service.register(user);
            ctx.result(new Gson().toJson(result));
        } catch(DataAccessException e) {
            ctx.status(400);
            ctx.result("Data Access Error");
        } catch(ImproperRequestException e){
            ctx.status(400);
            ctx.result("Error: bad request");
        } catch(AlreadyTakenException e){
            ctx.status(403);
            ctx.result("Error: username already taken");
        }
    }





}
