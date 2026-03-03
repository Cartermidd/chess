package service;

import Models.*;
import chess.*;
import dataaccess.*;
import java.util.UUID;

public class Service {

    private final DataAccess dataAccess;

    public Service(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }


    public void clearDB() throws DataAccessException {
        dataAccess.clear();
    }

}