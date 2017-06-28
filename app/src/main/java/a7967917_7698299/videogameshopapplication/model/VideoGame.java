package a7967917_7698299.videogameshopapplication.model;

import java.util.Date;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class VideoGame extends Item {


    private VideoGameVariables.ERSB ersbRating;
    private double gameLength;
    private VideoGameVariables.CATEGORY gameCategory;
    private VideoGameVariables.REGION gameRegion;
    private int numberOfPlayers;
    private long gameId;

    public VideoGame(String name, double price, String description, ItemVariables.STAR_REVIEW review, ItemVariables.PUBLISHER publisher, Date datePublished, VideoGameVariables.ERSB ersbRating, double gameLength, VideoGameVariables.CATEGORY gameCategory, VideoGameVariables.REGION gameRegion, int numberOfPlayers, long gameId) {
        super(ItemVariables.TYPE.GAME, name, price, description, review, publisher, datePublished);
        this.ersbRating = ersbRating;
        this.gameLength = gameLength;
        this.gameCategory = gameCategory;
        this.gameRegion = gameRegion;
        this.numberOfPlayers = numberOfPlayers;
        this.gameId = gameId;
    }

    public VideoGameVariables.ERSB getErsbRating() {
        return ersbRating;
    }

    public void setErsbRating(VideoGameVariables.ERSB ersbRating) {
        this.ersbRating = ersbRating;
    }

    public double getGameLength() {
        return gameLength;
    }

    public void setGameLength(double gameLength) {
        this.gameLength = gameLength;
    }

    public VideoGameVariables.CATEGORY getGameCategory() {
        return gameCategory;
    }

    public void setGameCategory(VideoGameVariables.CATEGORY gameCategory) {
        this.gameCategory = gameCategory;
    }

    public VideoGameVariables.REGION getGameRegion() {
        return gameRegion;
    }

    public void setGameRegion(VideoGameVariables.REGION gameRegion) {
        this.gameRegion = gameRegion;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
}
