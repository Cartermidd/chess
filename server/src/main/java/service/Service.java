package service;

import dataaccess.*;

public class Service {

    private final DataAccess dataAccess;

    public Service(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }


    public void clearDB() throws DataAccessException {
        dataAccess.clear();
    }

}