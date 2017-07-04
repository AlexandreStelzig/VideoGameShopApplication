package a7967917_7698299.videogameshopapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.OrderVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

/**
 * Created by alex on 7/2/2017.
 */

public final class Helper {


    public static ItemVariables.STAR_REVIEW convertIntegerToReview(int reviewInteger) {

        ItemVariables.STAR_REVIEW review = ItemVariables.STAR_REVIEW.ONE_STAR;

        switch (reviewInteger) {
            case 1:
                review = ItemVariables.STAR_REVIEW.ONE_STAR;
                break;
            case 2:
                review = ItemVariables.STAR_REVIEW.TWO_STAR;
                break;
            case 3:
                review = ItemVariables.STAR_REVIEW.THREE_STAR;
                break;
            case 4:
                review = ItemVariables.STAR_REVIEW.FOUR_STAR;
                break;
            case 5:
                review = ItemVariables.STAR_REVIEW.FIVE_STAR;
                break;
            default:
                review = ItemVariables.STAR_REVIEW.ONE_STAR;
                break;
        }
        return review;

    }


    public static VideoGameVariables.ERSB convertStringToERSB(String ersb) {

        VideoGameVariables.ERSB convertedERSB = VideoGameVariables.ERSB.CHILDHOOD;

        switch (ersb) {
            case "CHILDHOOD":
                convertedERSB = VideoGameVariables.ERSB.CHILDHOOD;
                break;
            case "EVERYONE":
                convertedERSB = VideoGameVariables.ERSB.EVERYONE;
                break;
            case "EVERYONE_10PLUS":
                convertedERSB = VideoGameVariables.ERSB.EVERYONE_10PLUS;
                break;
            case "TEEN":
                convertedERSB = VideoGameVariables.ERSB.TEEN;
                break;
            case "MATURE_17PLUS":
                convertedERSB = VideoGameVariables.ERSB.MATURE_17PLUS;
                break;
            case "ADULT_18PLUS":
                convertedERSB = VideoGameVariables.ERSB.ADULT_18PLUS;
                break;

        }
        return convertedERSB;

    }

    public static VideoGameVariables.CATEGORY convertStringToCategory(String category) {

        VideoGameVariables.CATEGORY convertedCategory = VideoGameVariables.CATEGORY.ACTION;

        switch (category) {
            case "ACTION":
                convertedCategory = VideoGameVariables.CATEGORY.ACTION;
                break;
            case "ADVENTURE":
                convertedCategory = VideoGameVariables.CATEGORY.ADVENTURE;
                break;
            case "RPG":
                convertedCategory = VideoGameVariables.CATEGORY.RPG;
                break;
            case "SPORTS":
                convertedCategory = VideoGameVariables.CATEGORY.SPORTS;
                break;
        }
        return convertedCategory;

    }


    public static VideoGameVariables.REGION convertStringToRegion(String region) {

        VideoGameVariables.REGION convertedRegion = VideoGameVariables.REGION.ALL;

        switch (region) {
            case "ALL":
                convertedRegion = VideoGameVariables.REGION.ALL;
                break;
            case "NA":
                convertedRegion = VideoGameVariables.REGION.NA;
                break;
            case "EU":
                convertedRegion = VideoGameVariables.REGION.EU;
                break;
            case "ASIA":
                convertedRegion = VideoGameVariables.REGION.ASIA;
                break;
        }
        return convertedRegion;

    }

    public static OrderVariables.STATUS convertStringToStatus(String status) {

        OrderVariables.STATUS convertedStatus = OrderVariables.STATUS.ORDER_RECEIVED;

        switch (status) {

            case "ORDER_RECEIVED":
                convertedStatus = OrderVariables.STATUS.ORDER_RECEIVED;
                break;
            case "IN_TRANSIT":
                convertedStatus = OrderVariables.STATUS.IN_TRANSIT;
                break;
            case "OUT_FOR_DELIVERY":
                convertedStatus = OrderVariables.STATUS.OUT_FOR_DELIVERY;
                break;
            case "DELIVERED":
                convertedStatus = OrderVariables.STATUS.DELIVERED;
                break;
        }
        return convertedStatus;
    }

}
