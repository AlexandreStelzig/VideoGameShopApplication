package a7967917_7698299.videogameshopapplication.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.helper.ImageLoader;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class ItemInfoFragment extends Fragment {


    // components
    private View view;
    private TextView itemTitleTextview;
    private TextView itemPriceTextView;
    private TextView itemESRBTextView;
    private TextView itemConsoleTextView;
    private TextView itemPublisherTextView;
    private TextView itemDatePublishedTextView;
    private TextView itemDescriptionTextView;
    private LinearLayout gameLayout;
    private Button wishlistButton;
    private Button addCartButton;
    private ProgressBar progressBar;
    private CarouselView carouselView;


    // carousel
    private List<String> imageListURL;
    private Bitmap[] imageCache;


    // database
    private DatabaseManager databaseManager;

    // wishlist
    private boolean itemAlreadyInWishlist;


    // initialized by setItemIdToOpenAtLaunch method before opening fragment
    private long itemId;
    private ItemVariables.TYPE itemType;

    // item
    private Item item;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_item_info, container, false);
        setHasOptionsMenu(true);

        databaseManager = DatabaseManager.getInstance();


        // init components
        itemTitleTextview = (TextView) view.findViewById(R.id.fragment_item_info_item_name_textview);
        itemPriceTextView = (TextView) view.findViewById(R.id.fragment_item_info_price_textview);
        itemESRBTextView = (TextView) view.findViewById(R.id.fragment_item_info_esrb_textview);
        itemConsoleTextView = (TextView) view.findViewById(R.id.fragment_item_info_console_textview);
        itemPublisherTextView = (TextView) view.findViewById(R.id.fragment_item_info_publisher_textview);
        itemDatePublishedTextView = (TextView) view.findViewById(R.id.fragment_item_info_date_pubished_textview);
        itemDescriptionTextView = (TextView) view.findViewById(R.id.fragment_item_info_description_textview);
        gameLayout = (LinearLayout) view.findViewById(R.id.fragment_item_game_info_layout);
        addCartButton = (Button) view.findViewById(R.id.fragment_item_info_add_cart_button);
        wishlistButton = (Button) view.findViewById(R.id.fragment_item_info_add_wishlist_button);
        progressBar = (ProgressBar) view.findViewById(R.id.carouselProgressBar);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);

        if (itemType == ItemVariables.TYPE.CONSOLE) {
            item = databaseManager.getConsoleById(itemId);
        } else {
            item = databaseManager.getGameById(itemId);
        }


        initHeader();
        initCarousel();
        initBody();

        return view;
    }

    private void initBody() {

        itemPublisherTextView.setText("Publisher: " + item.getPublisher());
        itemDatePublishedTextView.setText("Date published: " + item.getDatePublished());
        itemDescriptionTextView.setText(item.getDescription());


        if (itemType == ItemVariables.TYPE.CONSOLE) {
            gameLayout.setVisibility(View.GONE);
        } else {
            itemESRBTextView.setText("ESRB Rating: " + Helper.convertESRBToString(((VideoGame) item).getesrbRating()));

            List<ItemVariables.CONSOLES> consolesList = databaseManager.getConsolesFromGameId(item.getItemId());

            String consoleString = "";

            if (consolesList.isEmpty()) {
                consoleString = "No consoles";
            } else {

                for (int i = 0; i < consolesList.size(); i++) {
                    consoleString += Helper.convertConsoleToString(consolesList.get(i));
                    if (i + 1 < consolesList.size())
                        consoleString += " ";
                }
            }


            itemConsoleTextView.setText("Consoles: " + consoleString);
        }


        setWishlistButtonText();


        wishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).toggleWishlistAdd(itemId, itemType);
                setWishlistButtonText();
            }
        });

    }

    private void setWishlistButtonText() {
        itemAlreadyInWishlist = databaseManager.isItemAlreadyInWishlist(itemId, itemType);
        if (!itemAlreadyInWishlist) {
            wishlistButton.setText("Add To Wishlist");
        } else {
            wishlistButton.setText("Remove From Wishlist");
        }
    }


    // CALL THIS METHOD BEFORE OPENING FRAGMENT
    public void setItemIdToOpenAtLaunch(long itemId, ItemVariables.TYPE itemType) {
        this.itemId = itemId;
        this.itemType = itemType;
    }

    private void initHeader() {
        itemTitleTextview.setText(item.getName());
        itemPriceTextView.setText(item.getPrice() + "$");

        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).addItemToCart(itemId, itemType);
            }
        });


    }

    private void initCarousel() {

        progressBar.setVisibility(View.VISIBLE);
        carouselView.setVisibility(View.GONE);

        new LoadData().execute("");

    }

    private class LoadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            List<ItemImage> itemImages = new ArrayList<>();

            if (itemType == ItemVariables.TYPE.CONSOLE)
                itemImages = databaseManager.getImagesFromConsoleId(itemId, false);
            else
                itemImages = databaseManager.getImagesFromGameId(itemId, false);

            imageListURL = new ArrayList<>();


            for (int i = 0; i < itemImages.size(); i++) {
                imageListURL.add(itemImages.get(i).getImageURL());
            }


            imageCache = new Bitmap[imageListURL.size()];

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            carouselView.setViewListener(new ViewListener() {
                @Override
                public View setViewForPosition(final int position) {
                    View customView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout_carousel, null);
                    //set view attributes here
                    ImageView imageView = (ImageView) customView.findViewById(R.id.custom_layout_carousel_image);

                    imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),
                            R.drawable.loading));


                    if (position < imageListURL.size() && imageCache[position] == null)
                        new ImageLoader(imageView, new ImageLoader.AsyncResponse() {
                            @Override
                            public void processFinish(Bitmap output) {
                                // using this method for caching
                                imageCache[position] = output;
                            }
                        }).execute(imageListURL.get(position));
                    else
                        imageView.setImageBitmap(imageCache[position]);

                    return customView;
                }
            });

            carouselView.setPageCount(imageListURL.size());

            progressBar.setVisibility(View.GONE);
            carouselView.setVisibility(View.VISIBLE);


        }
    }


}
