package requests;

public class CreateGameRequest {
    String gameName;

    public CreateGameRequest(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }



    public static boolean misformatted(CreateGameRequest request){
        return request.gameName == null;
    }

}
