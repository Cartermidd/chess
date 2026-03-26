package client;

import org.junit.jupiter.api.*;
import server.Server;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ServerFacadeTests {

    private static Server server;
    static PreloginClient prelogClient;
    static LoggedinClient loggedClient;
    static GameplayClient gameClient;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);

        var url = "http://localhost:" + server.port();
        prelogClient = new PreloginClient(url);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void clear() throws Exception {
        prelogClient.quit();
    }

    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void runPreloginHelp() {
        String result = assertDoesNotThrow(() -> prelogClient.help());
        assertMatches("""
                Options:
                Register a new user: 'register' <USERNAME> <PASSWORD> <EMAIL>
                Login as an existing user: 'login' <USERNAME> <PASSWORD>
                Exit program: 'quit'
                To print a list of possible commands: 'help'
                """,result);
    }

    @Test
    public void registerOpensLoggedInClient() {
        String result = assertDoesNotThrow(() -> prelogClient.help());
    }



    private void assertMatches(String expected, String actual) {
        actual = actual.replace('"', '\'');

        assertTrue(actual.matches(expected), actual + "\n" + expected);
    }

}
