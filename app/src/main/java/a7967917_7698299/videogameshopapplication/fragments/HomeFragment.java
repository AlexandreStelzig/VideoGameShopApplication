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
import android.widget.SearchView;
import android.widget.TextView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.ImageLoader;
import a7967917_7698299.videogameshopapplication.model.Item;
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

    private List<String> gameImageList = new ArrayList<>();
    private List<String> consoleImageList = new ArrayList<>();
    private List<String> recommendedImageList = new ArrayList<>();

    private Bitmap[] gameImageCache;
    private Bitmap[] consoleImageCache;
    private Bitmap[] recommendedImageCache;


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

        } else {
            signInButton.setVisibility(View.VISIBLE);
            recommendedLayout.setVisibility(View.GONE);

            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).displayFragment(R.id.nav_sign_in_out);
                }
            });
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





            if (consoleImageList.isEmpty() || gameImageList.isEmpty() || recommendedImageList.isEmpty()) {
                gameList = databaseManager.getXNumberItem(3, ItemVariables.TYPE.GAME);
                consoleList = databaseManager.getXNumberItem(3, ItemVariables.TYPE.CONSOLE);
                recommendedList = databaseManager.getXNumberItemRandom(3);


                for (int gameCounter = 0; gameCounter < gameList.size(); gameCounter++) {
                    gameImageList.add(databaseManager.getImagesFromGameId(gameList.get(gameCounter).getItemId(), true).get(0).getImageURL());
                }

                for (int consoleCounter = 0; consoleCounter < consoleList.size(); consoleCounter++) {
                    consoleImageList.add(databaseManager.getImagesFromConsoleId(consoleList.get(consoleCounter).getItemId(), true).get(0).getImageURL());
                }


                for (int recommendedCounter = 0; recommendedCounter < recommendedList.size(); recommendedCounter++) {

                    if (recommendedList.get(recommendedCounter).getItemType() == ItemVariables.TYPE.CONSOLE) {
                        recommendedImageList.add(databaseManager.getImagesFromConsoleId(recommendedList.get(recommendedCounter).getItemId(), true).get(0).getImageURL());
                    } else {
                        recommendedImageList.add(databaseManager.getImagesFromGameId(recommendedList.get(recommendedCounter).getItemId(), true).get(0).getImageURL());
                    }

                }

                gameImageCache = new Bitmap[gameImageList.size()];
                consoleImageCache = new Bitmap[consoleImageList.size()];
                recommendedImageCache = new Bitmap[recommendedImageList.size()];

            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

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

                    imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),
                            R.drawable.loading));

                    if (position < recommendedImageList.size() && recommendedImageCache[position] == null)
                        new ImageLoader(imageView, new ImageLoader.AsyncResponse() {
                            @Override
                            public void processFinish(Bitmap output) {
                                // using this method for caching
                                recommendedImageCache[position] = output;
                            }
                        }, getContext()).execute(recommendedImageList.get(position));
                    else
                        imageView.setImageBitmap(recommendedImageCache[position]);

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

                    imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),
                            R.drawable.loading));

                    if (position < consoleImageList.size() && consoleImageCache[position] == null)
                        new ImageLoader(imageView, new ImageLoader.AsyncResponse() {
                            @Override
                            public void processFinish(Bitmap output) {
                                // using this method for caching
                                consoleImageCache[position] = output;
                            }
                        }, getContext()).execute(consoleImageList.get(position));
                    else
                        imageView.setImageBitmap(consoleImageCache[position]);

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

                    imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),
                            R.drawable.loading));


                    if (position < gameImageList.size() && gameImageCache[position] == null)
                        new ImageLoader(imageView, new ImageLoader.AsyncResponse() {
                            @Override
                            public void processFinish(Bitmap output) {
                                // using this method for caching
                                gameImageCache[position] = output;
                            }
                        }, getContext()).execute(gameImageList.get(position));
                    else
                        imageView.setImageBitmap(gameImageCache[position]);


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
