package server.Handlers;

import Models.AuthData;
import Models.UserData;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import server.exceptions.*;
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
