package a7967917_7698299.videogameshopapplication.manager;

import java.text.Collator;
import java.text.Normalizer;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import a7967917_7698299.videogameshopapplication.model.Item;

/**
 * Created by alex on 7/8/2017.
 */

public class SortingManager {


    public enum SortingStates {
        NAME_AZ,
        NAME_ZA,
        DATE_RELEASE_NEW_OLD,
        DATE_RELEASE_OLD_NEW
    }

    private  SortingStates currentState;


    private static SortingManager instance;

    private SortingManager() {
        currentState = SortingStates.NAME_AZ;
    }

    public static SortingManager getInstance() {
        if (instance == null)
            instance = new SortingManager();
        return instance;
    }


    // CALL THIS METHOD FOR SORTING
    public List<Item> sortItemList(List<Item> items) {

        List<Item> sortedItemList = items;
        switch (currentState) {

            case NAME_AZ:
                sortedItemList = sortItemListByNameAZ(items);
                break;
            case NAME_ZA:
                sortedItemList = sortItemListByNameZA(items);
                break;
            case DATE_RELEASE_NEW_OLD:
                sortedItemList = sortItemListNewOld(items);
                break;
            case DATE_RELEASE_OLD_NEW:
                sortedItemList = sortItemListOldNew(items);
                break;
        }


        return sortedItemList;
    }

    private List<Item> sortItemListByNameAZ(List<Item> items) {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {

                Collator usCollator = Collator.getInstance(Locale.US);
                usCollator.setStrength(Collator.PRIMARY);

                String lowerCase1 = item1.getName().toLowerCase();
                String lowerCase2 = item2.getName().toLowerCase();

                String o1String = Normalizer.normalize(lowerCase1, Normalizer.Form.NFD);
                String o2String = Normalizer.normalize(lowerCase2, Normalizer.Form.NFD);

                // take out the numbers
                String o1StringPart = o1String.replaceAll("\\d", "");
                String o2StringPart = o2String.replaceAll("\\d", "");

                if (o1StringPart.equalsIgnoreCase(o2StringPart)) {
                    return extractInt(o1String) - extractInt(o2String);
                }
                return o1String.compareTo(o2String);

            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });

        return items;
    }

    private List<Item> sortItemListNewOld(List<Item> items) {
        return  items;
    }

    private List<Item> sortItemListOldNew(List<Item> items) {
        return  items;
    }


    private List<Item> sortItemListByNameZA(List<Item> items) {
        items = sortItemListByNameAZ(items);
        Collections.reverse(items);
        return items;
    }


}
