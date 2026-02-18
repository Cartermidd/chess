# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)


[Server Design Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+uB5afJCIJqTsXzQo8wHiVQSIwAgQnihignCQSRJgKSb6GLuNL7gyTJTspXI3l5d5LsKYoSm6MpymW7xKpgKrBhqbpGBAahoAA5MwVpooFvLBZZ1k9n224eZZ-oRSlqgAHIQGAUZolGMZxoUWlJpUolpnhBE5qoebzNBRYlq6WrSBVMDVcwdU5A29E5QuAqtR2Vkuhu7pbvF6owAAkmgIDQCi4AwMZALFSB1T+ttu3Fig4BNPofw7A1KCxgp6HwsmyCpjA6b4aMYzdb1BZjAN0D1Bde3Xcwt1bCZdFNu5grDvyY4Tigz7xOel7Xoji6VA+a4BpjW4nQtOmlo54oZKoAGYKTp0SWBhH6fMJGod8FFUfWLO0a9mEfdhMC4T9ekxcRpFs5eHPIVzaEzZ43h+P4XgoOgMRxIkSsq45vhYKJgqgfUDTSBG-ERu0EbdD0cmqApwzs4h6A8x+5m6WMdvUZCZmwhh+XLbZ9jaw5Qna85aiucTVK3kjMCMmAaMY-B9toHOQUOiForik+l7mIQeQFDAktxaqGpuyrHCjWgNUwFlOSzfudNLV2MCFf28MLfrY0QGjkuPc98Yte9JRgB1ACMXX8gD-XFiDCrTJe0BIAAXiguywwx2PzT7XZo6+zoeRHKf0hwKDcMel7x5RifJ7lqe48K0jH0yhjb1urdO17ZNB6elPU7TbdnWBml-680HjhTqoxV6MXlgEFE65-DYHFBqfiaIYAAHElQaF1qVUsDQUGmwtvYJUtsJaJ0dpUUmLxXbEOotLBsv9fS73qEgnIaCcwOTRCwtQIcSThxkJHek0cmRd0ThiIRSEr5zXvKFDOBME5IRgOKXh8g9AGELglSUs89TzyXuuJhzAOECi4Vgde9cCq9hbrveuZUNHxC0cvQxPcmqOwHp9dMo9fr-XzJPQaM8KK2JXo2NefCN70M7PUZ+8geHr3qCgpk+iMTiLrmnGAhjUHoMUcATQOgTHLQyGAHw7xUk5nScTN+X5omxPQd-BAgFnZvRCTUChBCcwLAaOMJpKBNrSALMPcIwRAggk2PEXUKA3Scj2N8PQCAQBqlGZBRY3x2mVSVPMwB9NgGfUFgRdpqgWltKVJ07pvT+nLEGcM2ZBkxgTJNNM85fVLkgkWcsy5FwYCdCuAEyBzF-AcAAOxuCcCgJwMQIzBDgFxAAbPAFGhTDBFD5mJepklWgdHwYQ6xkssyPLmKsxMZDakUJLlLMWKwxhYruWhOhi1rJwBRhwjENKjwoA4YYtyFjPLX34THUR6ARFUPQAk4Kt904Sm5WgeRYqeTKOVEXdRcgbFQEXsvZJKBCSh1rnleppiiqv0RaWZk1i-H2OjE9Rx-c2rwpHmPXMnjCxT1LD4A1CrtHTSbMYv+oSZFXhfmy-eHLRxQsZXSgVN8hRhPxhw4pOqHjv3qAyuQTKlRVJqe-SxDN2kHPqD0vpOLvbOP5ps366aumZqORAuWXzLDH1spsVWSAEhgErX2CANaABSEAFERv8MkUAao4WDz1v-A2TRmQyR6O0ohsj0BZmwAgYAlaoBwAgLZKAawi05vhHimNMBxiEposCH4s752LuXXsAA6iwTaZsegACF+IKDgAAaQWfs4tMAs2BE9l+AdHq23ijpb+tAzKVUuVZZ2BGQSfKxz5WgXlk6k7qpDcuMKmc4PivSVK1RG19Vyr8cq1VNc3Wb27GYkpurXSOsVRwI1jUXpmvgBar6Tg3HZnHjaoGdr6gOt8U65eLrAkH2CVS5a4SMmt19XNSDdKi3BpxqGmAyGYVoclfoaVaii3JTUE3Su1cjFBOyY3ZupHFr+mqhwhxtGgELXaoxoWf1WN9Vtd4vQ64UT4bAHxhDi4iPRyVB6daGpAxmnlGGJCRn26BeDKGcMaBzN90s3moejHMzuPs4DYGpYTRBZgLWesECo3kJgABoDVNqk01qaml4671n5rASMMtTEFZeDnbW+tTX5SIGDLAYA2AZ05yajAPt5hv0NMaEbE2ZsLbGFIdGsp27P0WU1ctEA3A8DSGvfSlb7pr0ssiRBmAy3OtrfiZ5+aQr74n0MG6VQax9FrEJRofL+L6OHe2-+Urv8sGVaceakBAtasQKAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
