package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import service.ClearService;

public class ClearHandler {
    ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }

    public void clear(Context ctx){
        try {
            clearService.clearDB();
        }catch(DataAccessException e) {
            ctx.status(500);
            ctx.result(new Gson().toJson(new ErrorResponse("Data Access Error")));
            ctx.contentType("application/json");
        }
    }
}
