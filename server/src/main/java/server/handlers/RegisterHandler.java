package server.handlers;


import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import exceptions.RegisterRequest;
import results.LoginResult;
import service.UserService;

public class RegisterHandler {
    UserService userService;

    public RegisterHandler(UserService userService) {
        this.userService = userService;
    }

    public void register(Context ctx) {
        try {
            RegisterRequest request = new Gson().fromJson(ctx.body(), RegisterRequest.class);
            LoginResult result = userService.register(request);
            ctx.status(200);
            ctx.result(new Gson().toJson(result));
            ctx.contentType("application/json");
        } catch(DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(new ErrorResponse("Error:" + e)));
            ctx.contentType("application/json");
        } catch(ImproperRequestException e){
            ctx.status(400);
            ctx.result(new Gson().toJson(new ErrorResponse("Error: Misformatted Request")));
            ctx.contentType("application/json");
        } catch(AlreadyTakenException e){
            ctx.status(403);
            ctx.result(new Gson().toJson(new ErrorResponse(e.getMessage())));
            ctx.contentType("application/json");
        }
    }


}
