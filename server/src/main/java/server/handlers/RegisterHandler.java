package server.handlers;


import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import requests.RegisterRequest;
import results.LoginResult;
import service.UserService;

public class RegisterHandler {
    Service service;
    UserService userService;

    public RegisterHandler(Service service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public void register(Context ctx) {
        try {
            RegisterRequest request = new Gson().fromJson(ctx.body(), RegisterRequest.class);
            LoginResult result = userService.register(request);
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
