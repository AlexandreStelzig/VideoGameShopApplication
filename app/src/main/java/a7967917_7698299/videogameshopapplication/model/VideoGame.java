package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class VideoGame extends Item {


    private VideoGameVariables.ESRB esrbRating;
    private double gameLength;
    private VideoGameVariables.CATEGORY gameCategory;
    private VideoGameVariables.REGION gameRegion;
    private int numberOfPlayers;

    public VideoGame(String name, double price, String description, ItemVariables.STAR_REVIEW review, String publisher, String datePublished, VideoGameVariables.ESRB esrbRating, double gameLength, VideoGameVariables.CATEGORY gameCategory, VideoGameVariables.REGION gameRegion, int numberOfPlayers, long gameId) {
        super(gameId, ItemVariables.TYPE.GAME, name, price, description, review, publisher, datePublished);
        this.esrbRating = esrbRating;
        this.gameLength = gameLength;
        this.gameCategory = gameCategory;
        this.gameRegion = gameRegion;
        this.numberOfPlayers = numberOfPlayers;
    }

    public VideoGameVariables.ESRB getesrbRating() {
        return esrbRating;
    }

    public void setesrbRating(VideoGameVariables.ESRB esrbRating) {
        this.esrbRating = esrbRating;
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
}
