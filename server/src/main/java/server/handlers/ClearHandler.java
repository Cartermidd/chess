package server.handlers;

import service.ClearService;

public class ClearHandler {
    ClearService clearService;

    public ClearHandler(ClearService clearService) {
        this.clearService = clearService;
    }
}
