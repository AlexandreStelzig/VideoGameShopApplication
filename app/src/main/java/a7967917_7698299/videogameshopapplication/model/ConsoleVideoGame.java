package a7967917_7698299.videogameshopapplication.model;

/**
 * Created by alex on 2017-06-26.
 */

public class ConsoleVideoGame {

    private long consoleId;
    private long gameId;

    public ConsoleVideoGame(long consoleId, long gameId) {
        this.consoleId = consoleId;
        this.gameId = gameId;
    }

    public long getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(long consoleId) {
        this.consoleId = consoleId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
