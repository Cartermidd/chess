package server.Handlers;

import Models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import server.exceptions.IncorrectPasswordException;
import server.exceptions.UserDoesNotExistException;
import service.Service;

public class LoginHandler {
    Service service;

    public LoginHandler(Service service){
        this.service = service;
    }


    public void login(Context ctx) {
        try {
            UserData user = new Gson().fromJson(ctx.body(), UserData.class);
            user = service.login(user);
            ctx.result(new Gson().toJson(user));
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        } catch (UserDoesNotExistException e){
            ctx.status(400);
            ctx.result("Error: bad request");
        } catch (IncorrectPasswordException e){
            ctx.status(400);
            ctx.result("Error: Incorrect Password");
        }
    }


}
