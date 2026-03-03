package server.handlers;

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
            ctx.status(400);
            ctx.result("Data Access Error");
        }
    }
}
