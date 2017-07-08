package a7967917_7698299.videogameshopapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

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


    public static VideoGameVariables.ESRB convertStringToesrb(String esrb) {

        VideoGameVariables.ESRB convertedesrb = VideoGameVariables.ESRB.CHILDHOOD;

        switch (esrb) {
            case "CHILDHOOD":
                convertedesrb = VideoGameVariables.ESRB.CHILDHOOD;
                break;
            case "EVERYONE":
                convertedesrb = VideoGameVariables.ESRB.EVERYONE;
                break;
            case "EVERYONE_10PLUS":
                convertedesrb = VideoGameVariables.ESRB.EVERYONE_10PLUS;
                break;
            case "TEEN":
                convertedesrb = VideoGameVariables.ESRB.TEEN;
                break;
            case "MATURE_17PLUS":
                convertedesrb = VideoGameVariables.ESRB.MATURE_17PLUS;
                break;
            case "ADULT_18PLUS":
                convertedesrb = VideoGameVariables.ESRB.ADULT_18PLUS;
                break;

        }
        return convertedesrb;

    }

    public static String convertESRBToString(VideoGameVariables.ESRB esrb){
        String convertedERSB = "";

        switch (esrb){

            case CHILDHOOD:
                convertedERSB = "Childhood";
                break;
            case EVERYONE:
                convertedERSB = "Everyone";
                break;
            case EVERYONE_10PLUS:
                convertedERSB = "Everyone 10+";
                break;
            case TEEN:
                convertedERSB = "Teen";
                break;
            case MATURE_17PLUS:
                convertedERSB = "Mature 17+";
                break;
            case ADULT_18PLUS:
                convertedERSB = "Adult 18+";
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

    public static ItemVariables.CONSOLES convertStringToConsole(String console) {

        ItemVariables.CONSOLES convertedConsole = ItemVariables.CONSOLES.SWITCH;

        switch (console) {

            case "SWITCH":
                convertedConsole = ItemVariables.CONSOLES.SWITCH;
                break;
            case "PS4":
                convertedConsole = ItemVariables.CONSOLES.PS4;
                break;
            case "XBOXONE":
                convertedConsole = ItemVariables.CONSOLES.XBOXONE;
                break;
            case "THREE_DS":
                convertedConsole = ItemVariables.CONSOLES.THREE_DS;
                break;
        }
        return convertedConsole;
    }

    public static String convertConsoleToString(ItemVariables.CONSOLES console) {

        String consoleString = "";

        switch (console) {
            case SWITCH:
                consoleString = "Switch";
                break;
            case THREE_DS:
                consoleString = "3DS";
                break;
            case PS4:
                consoleString = "PS4";
                break;
            case XBOXONE:
                consoleString = "Xbox One";
                break;
        }
        return consoleString;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



}
