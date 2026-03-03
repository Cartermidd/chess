package server.handlers;

import exceptions.ImproperRequestException;
import models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.IncorrectPasswordException;
import exceptions.UserDoesNotExistException;
import requests.LoginRequest;
import results.LoginResult;
import service.UserService;

public class LoginHandler {
    UserService service;

    public LoginHandler(UserService service){
        this.service = service;
    }


    public void login(Context ctx) {
        try {
            UserData user = new Gson().fromJson(ctx.body(), UserData.class);
            LoginResult result = service.login(new LoginRequest(user.getUsername(),user.getPassword()));
            ctx.result(new Gson().toJson(result));
        }catch (DataAccessException _){
            ctx.status(400);
            ctx.result("Data Access Error");
        } catch (UserDoesNotExistException e){
            ctx.status(400);
            ctx.result("Error: bad request");
        } catch (IncorrectPasswordException e){
            ctx.status(400);
            ctx.result("Error: Incorrect Password");
        } catch (ImproperRequestException e) {
            ctx.status(400);
            ctx.result(e.toString());
        }
    }


}
