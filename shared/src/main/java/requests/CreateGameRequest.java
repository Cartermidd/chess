package requests;

public class CreateGameRequest {
    String gameName;

    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    public CreateGameRequest(String[] params) {
        this.gameName = (params.length > 0) ? params[0] : null;
    }


    public String getGameName() {
        return gameName;
    }



    public static boolean misformatted(CreateGameRequest request){
        return request.gameName == null;
    }

}
