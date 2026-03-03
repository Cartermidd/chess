package service;

import dataaccess.DataAccessException;
import exceptions.AlreadyTakenException;
import exceptions.ImproperRequestException;
import requests.RegisterRequest;
import results.LoginResult;

public class UserService {



    public LoginResult register(RegisterRequest request) throws DataAccessException, ImproperRequestException, AlreadyTakenException {
        if (RegisterRequest.misformatted(request)) {
            throw new ImproperRequestException("misformatted request");
        }



        if (/* username is already taken */)
        {
            throw new AlreadyTakenException("Username Taken");
        }

        return new LoginResult();
    }


}
