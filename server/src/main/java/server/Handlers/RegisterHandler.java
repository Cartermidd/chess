package server.Handlers;

import Models.AuthData;
import Models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import server.exceptions.AlreadyTakenException;
import server.exceptions.ImproperRequestException;
import service.Service;

public class RegisterHandler {
    Service service;

    public RegisterHandler(Service service) {
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
