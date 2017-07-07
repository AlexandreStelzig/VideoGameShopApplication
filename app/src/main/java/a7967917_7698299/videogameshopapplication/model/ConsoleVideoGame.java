package a7967917_7698299.videogameshopapplication.model;

/**
 * Created by alex on 2017-06-26.
 */

public class ConsoleVideoGame {

    private String consoleName;
    private long gameId;

    public ConsoleVideoGame(String consoleName, long gameId) {
        this.consoleName = consoleName;
        this.gameId = gameId;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
