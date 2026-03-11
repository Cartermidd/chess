import chess.*;
import dataaccess.*;
import server.Server;
import service.ClearService;
import service.GameService;
import service.UserService;

public class ServerMain {
    public static void main(String[] args) {
        try {
            int port = 8080;
            if (args.length >= 1){
                port = Integer.parseInt(args[0]);
            }


            UserDAO userDAO = new MemoryDataAccess();
            GameDAO gameDAO = new MemoryDataAccess();
            AuthDAO authDAO = new MemoryDataAccess();
            if (args.length >= 2 && args[1].equals("sql")){
                userDAO = new MySqlDataAccess();
                gameDAO = new MySqlDataAccess();
                authDAO = new MySqlDataAccess();
            }

            var clearService = new ClearService(userDAO, gameDAO, authDAO);
            var gameService = new GameService(authDAO, gameDAO);
            var userService = new UserService(userDAO, authDAO);
            Server server = new Server(userService, gameService, clearService);
            server.run(port);

            port = server.port();
            System.out.printf("Server started on port %d with %s%n", port, authDAO.getClass());
            return;
        } catch (Throwable ex){
            System.out.printf("Unable to start Chess Server: %s%n", ex.getMessage());
        }
        System.out.println("♕ 240 Chess Server");
    }
}
