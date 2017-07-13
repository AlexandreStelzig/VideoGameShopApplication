package a7967917_7698299.videogameshopapplication.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class HomeFragment extends Fragment {


    // components
    private View view;
    private View searchViewRoot;
    private SearchView searchView;

    private List<Item> consoleList = new ArrayList<>();
    private List<Item> gameList = new ArrayList<>();
    private List<Item> recommendedList = new ArrayList<>();

    private DatabaseManager databaseManager;

    private CarouselView recommendedCarouselView;
    private CarouselView consoleCarouselView;
    private CarouselView gameCarouselView;
    private ProgressBar recommendedCarouselProgressBar;
    private ProgressBar consoleCarouselProgressBar;
    private ProgressBar gameCarouselProgressBar;

    private Button signInButton;

    private List<Bitmap> gameImageList = new ArrayList<>();
    private List<Bitmap> consoleImageList = new ArrayList<>();
    private List<Bitmap> recommendedImageList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        databaseManager = DatabaseManager.getInstance();

        // init search view
        signInButton = (Button) view.findViewById(R.id.fragment_home_sign_in_button);
        searchViewRoot = (View) view.findViewById(R.id.search_view_root);
        searchView = (SearchView) view.findViewById(R.id.search_view_home);
        recommendedCarouselView = (CarouselView) view.findViewById(R.id.fragment_home_recommended_carousel);
        consoleCarouselView = (CarouselView) view.findViewById(R.id.fragment_home_console_carousel);
        gameCarouselView = (CarouselView) view.findViewById(R.id.fragment_home_game_carousel);

        recommendedCarouselProgressBar = (ProgressBar) view.findViewById(R.id.fragment_home_recommended_carousel_progressbar);
        consoleCarouselProgressBar = (ProgressBar) view.findViewById(R.id.fragment_home_console_carousel_progressbar);
        gameCarouselProgressBar = (ProgressBar) view.findViewById(R.id.fragment_home_game_carousel_progressbar);


        setHomeSignInComponents();
        initSearchView();
        initHyperLinks();


        populateInfo();

        return view;
    }

    private void populateInfo() {
        recommendedCarouselProgressBar.setVisibility(View.VISIBLE);
        consoleCarouselProgressBar.setVisibility(View.VISIBLE);
        gameCarouselProgressBar.setVisibility(View.VISIBLE);

        recommendedCarouselProgressBar.setRotation((float) (Math.random() * 360));
        consoleCarouselProgressBar.setRotation((float) (Math.random() * 360));
        gameCarouselProgressBar.setRotation((float) (Math.random() * 360));

        recommendedCarouselView.setVisibility(View.GONE);
        consoleCarouselView.setVisibility(View.GONE);
        gameCarouselView.setVisibility(View.GONE);


        new LoadData().execute("");

    }

    private void initHyperLinks() {

        TextView shopAll = (TextView) view.findViewById(R.id.fragment_home_hyperlink_shop);
        TextView shopConsoles = (TextView) view.findViewById(R.id.fragment_home_hyperlink_shop_consoles);
        TextView shopGames = (TextView) view.findViewById(R.id.fragment_home_hyperlink_shop_games);

        shopAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setSearchQuery("");
                ((MainActivity) getActivity()).displayFragment(R.id.search_view_results);
            }
        });

        shopConsoles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFilterByConsole(null);
                ((MainActivity) getActivity()).displayFragment(R.id.search_view_results);
            }
        });

        shopGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFilterGamesByCategory(null);
                ((MainActivity) getActivity()).displayFragment(R.id.search_view_results);
            }
        });

    }

    public void setHomeSignInComponents() {
        boolean userActive = databaseManager.getCurrentActiveUser() != null;
        LinearLayout recommendedLayout = (LinearLayout) view.findViewById(R.id.fragment_home_recommended_layout);

        if (userActive) {
            signInButton.setVisibility(View.GONE);
            recommendedLayout.setVisibility(View.VISIBLE);

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).displayFragment(R.id.nav_sign_in_out);
                }
            });

        } else {
            signInButton.setVisibility(View.VISIBLE);
            recommendedLayout.setVisibility(View.GONE);
        }
    }

    // remove auto focus for search view
    @Override
    public void onResume() {
        super.onResume();

        searchView.setQuery("", false);
        searchViewRoot.requestFocus();
    }


    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ((MainActivity) getActivity()).setSearchQuery(query);
                ((MainActivity) getActivity()).displayFragment(R.id.search_view_results);

                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private class LoadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {


            if (consoleImageList.isEmpty() || gameImageList.isEmpty()) {
                gameList = databaseManager.getXNumberItem(5, ItemVariables.TYPE.GAME);
                consoleList = databaseManager.getXNumberItem(5, ItemVariables.TYPE.CONSOLE);
                recommendedList = databaseManager.getXNumberItemRandom(5);

                try {
                    String url = ("http://used.agwest.com/images/default-image-agwest-thumb.jpg");
                    InputStream in = new java.net.URL(url).openStream();
                    Bitmap unavailableImage = BitmapFactory.decodeStream(in);


                    for (int gameCounter = 0; gameCounter < gameList.size(); gameCounter++) {
                        String urlGame = databaseManager.getImagesFromGameId(gameList.get(gameCounter).getItemId(), true).get(0).getImageURL();
                        if (urlGame.isEmpty()) {
                            gameImageList.add(unavailableImage);
                        } else {
                            url = (urlGame);
                            in = new java.net.URL(url).openStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(in);
                            gameImageList.add(bitmap);
                        }
                    }

                    for (int consoleCounter = 0; consoleCounter < consoleList.size(); consoleCounter++) {
                        String urlConsole = databaseManager.getImagesFromConsoleId(consoleList.get(consoleCounter).getItemId(), true).get(0).getImageURL();
                        if (urlConsole.isEmpty()) {
                            consoleImageList.add(unavailableImage);
                        } else {
                            url = (urlConsole);
                            in = new java.net.URL(url).openStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(in);
                            consoleImageList.add(bitmap);
                        }
                    }


                    for (int recommendedCounter = 0; recommendedCounter < recommendedList.size(); recommendedCounter++) {

                        String urlConsole = "";
                        if (recommendedList.get(recommendedCounter).getItemType() == ItemVariables.TYPE.CONSOLE) {
                            urlConsole = databaseManager.getImagesFromConsoleId(recommendedList.get(recommendedCounter).getItemId(), true).get(0).getImageURL();
                        } else {
                            urlConsole = databaseManager.getImagesFromGameId(recommendedList.get(recommendedCounter).getItemId(), true).get(0).getImageURL();
                        }

                        if (urlConsole.isEmpty()) {
                            recommendedImageList.add(unavailableImage);
                        } else {
                            url = (urlConsole);
                            in = new java.net.URL(url).openStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(in);
                            recommendedImageList.add(bitmap);
                        }
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            gameCarouselView.setViewListener(new ViewListener() {
                @Override
                public View setViewForPosition(final int position) {
                    View customView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout_carousel, null);
                    //set view attributes here
                    ImageView imageView = (ImageView) customView.findViewById(R.id.custom_layout_carousel_image);
                    TextView textView = (TextView) customView.findViewById(R.id.custom_layout_carousel_title);
                    TextView price = (TextView) customView.findViewById(R.id.custom_layout_carousel_price);

                    price.setVisibility(View.VISIBLE);
                    price.setText(gameList.get(position).getPrice() + "$");

                    textView.setVisibility(View.VISIBLE);
                    textView.setText(gameList.get(position).getName());

                    imageView.setImageBitmap(gameImageList.get(position));

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((MainActivity) getActivity()).setItemIdToOpenAtInfoLaunch(gameList.get(position).getItemId(), ItemVariables.TYPE.GAME);
                            ((MainActivity) getActivity()).displayFragment(R.layout.fragment_item_info);
                        }
                    });


                    return customView;
                }

            });

            gameCarouselView.setPageCount(gameImageList.size());

            consoleCarouselView.setViewListener(new ViewListener() {
                @Override
                public View setViewForPosition(final int position) {
                    View customView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout_carousel, null);
                    //set view attributes here
                    ImageView imageView = (ImageView) customView.findViewById(R.id.custom_layout_carousel_image);
                    TextView textView = (TextView) customView.findViewById(R.id.custom_layout_carousel_title);
                    TextView price = (TextView) customView.findViewById(R.id.custom_layout_carousel_price);

                    price.setVisibility(View.VISIBLE);
                    price.setText(consoleList.get(position).getPrice() + "$");

                    textView.setVisibility(View.VISIBLE);
                    textView.setText(consoleList.get(position).getName());

                    imageView.setImageBitmap(consoleImageList.get(position));

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((MainActivity) getActivity()).setItemIdToOpenAtInfoLaunch(consoleList.get(position).getItemId(), ItemVariables.TYPE.CONSOLE);
                            ((MainActivity) getActivity()).displayFragment(R.layout.fragment_item_info);
                        }
                    });
                    return customView;
                }
            });

            consoleCarouselView.setPageCount(consoleImageList.size());


            recommendedCarouselView.setViewListener(new ViewListener() {
                @Override
                public View setViewForPosition(final int position) {
                    View customView = getActivity().getLayoutInflater().inflate(R.layout.custom_layout_carousel, null);
                    //set view attributes here
                    ImageView imageView = (ImageView) customView.findViewById(R.id.custom_layout_carousel_image);
                    TextView textView = (TextView) customView.findViewById(R.id.custom_layout_carousel_title);
                    TextView price = (TextView) customView.findViewById(R.id.custom_layout_carousel_price);

                    price.setVisibility(View.VISIBLE);
                    price.setText(recommendedList.get(position).getPrice() + "$");

                    textView.setVisibility(View.VISIBLE);
                    textView.setText(recommendedList.get(position).getName());

                    imageView.setImageBitmap(recommendedImageList.get(position));

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ((MainActivity) getActivity()).setItemIdToOpenAtInfoLaunch(recommendedList.get(position).getItemId(), recommendedList.get(position).getItemType());
                            ((MainActivity) getActivity()).displayFragment(R.layout.fragment_item_info);
                        }
                    });
                    return customView;
                }
            });

            recommendedCarouselView.setPageCount(recommendedList.size());


            // set visible
            recommendedCarouselProgressBar.setVisibility(View.GONE);
            consoleCarouselProgressBar.setVisibility(View.GONE);
            gameCarouselProgressBar.setVisibility(View.GONE);

            recommendedCarouselView.setVisibility(View.VISIBLE);
            consoleCarouselView.setVisibility(View.VISIBLE);
            gameCarouselView.setVisibility(View.VISIBLE);
        }


    }

}
