package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.helper.ImageLoader;
import a7967917_7698299.videogameshopapplication.model.Console;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class ResultsFragment extends Fragment {

    // components
    private View view;
    private ListView listView;
    private View searchViewRoot;
    private SearchView searchView;
    private ProgressBar progressBar;
    private TextView nbResultsTextView;
    private TextView noResultsTextView;

    // Database
    private DatabaseManager databaseManager;

    // items
    private List<Item> itemList;
    private CustomListAdapter customListAdapter;
    private List<String> imagesURLList;
    private Bitmap[] cachedImages;

    // loading stuff
    private boolean loading = false;

    // search filters / queries
    private ItemVariables.CONSOLES consoleToFilter = null;
    private ItemVariables.CONSOLES gameByConsoleToFilter = null;
    private VideoGameVariables.CATEGORY gameByCategoryFilter = null;
    private String searchViewQuery = null;

    private boolean filterByConsole;
    private boolean filterGamesByConsole;
    private boolean filterGamesByCategory;
    private boolean filterBySearchViewQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_results, container, false);
        setHasOptionsMenu(true);

        // init components
        listView = (ListView) view.findViewById(R.id.fragment_results_listview);
        nbResultsTextView = (TextView) view.findViewById(R.id.fragment_results_nb_results);
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_results_progress_bar);
        progressBar.setVisibility(View.GONE);
        noResultsTextView = (TextView) view.findViewById(R.id.fragment_results_no_results_textview);
        noResultsTextView.setVisibility(View.GONE);

        // init stuff
        itemList = new ArrayList<>();
        imagesURLList = new ArrayList<>();
        cachedImages = new Bitmap[itemList.size()];
        databaseManager = DatabaseManager.getInstance();


        // init search
        searchViewRoot = (View) view.findViewById(R.id.search_view_root);
        searchView = (SearchView) view.findViewById(R.id.search_view_results);
        initSearchView();

        initListView();

        return view;
    }

    // remove auto focus for search view
    @Override
    public void onResume() {
        super.onResume();

        searchView.clearFocus();
        searchViewRoot.requestFocus();
    }

    private void initSearchView() {

        // set search view listeners
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.setQuery(query, false);
                searchView.clearFocus();
                setSearchViewQuery(query);
                ((MainActivity) getActivity()).displayFragment(R.id.search_view_results);
//                populateListView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        // set the query form home menu
        if (searchViewQuery != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    searchView.setQuery(searchViewQuery, false);
                }
            }, 1);

        }

    }

    private void initListView() {

        customListAdapter = new CustomListAdapter(getContext());
        listView.setAdapter(customListAdapter);
        populateListView();
    }

    public void populateListView() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        noResultsTextView.setVisibility(View.GONE);

        new LoadData().execute("");
    }


    private void setNumberOfResults() {
        int nbResults = itemList.size();
        String text = nbResults + " ";
        if (nbResults == 1)
            text += "RESULT";
        else
            text += "RESULTS";


        if (filterByConsole) {
            if(consoleToFilter == null){
                text += ": ALL CONSOLES";
            }else{
                text += ": " + consoleToFilter.toString() + " consoles";
            }
        } else if (filterGamesByConsole) {
            if(gameByConsoleToFilter == null){
                text += ": ALL GAMES";
            }else{
                text += ": " + gameByConsoleToFilter.toString() + " games";
            }

        } else if (filterGamesByCategory) {
            if(gameByCategoryFilter == null){
                text += ": ALL GAMES";
            }else{
                text += ": " + gameByCategoryFilter.toString();
            }
        } else {
            if (searchViewQuery.equals(""))
                text += ": ALL";
            else
                text += ": " + searchViewQuery;

        }

        nbResultsTextView.setText(text);
    }

    public void setFilterByConsole(ItemVariables.CONSOLES consoleToFilterBy) {


        filterByConsole = true;
        filterGamesByConsole = false;
        filterGamesByCategory = false;
        filterBySearchViewQuery = false;

        consoleToFilter = consoleToFilterBy;
    }

    public void setFilterGamesByConsole(ItemVariables.CONSOLES consoleToFilterBy) {

        filterByConsole = false;
        filterGamesByConsole = true;
        filterGamesByCategory = false;
        filterBySearchViewQuery = false;

        gameByConsoleToFilter = consoleToFilterBy;;
    }

    public void setFilterGamesByCategory(VideoGameVariables.CATEGORY categoryToFilterBy) {


        filterByConsole = false;
        filterGamesByConsole = false;
        filterGamesByCategory = true;
        filterBySearchViewQuery = false;

        gameByCategoryFilter = categoryToFilterBy;
    }

    public void setSearchViewQuery(String query) {
        filterByConsole = false;
        filterGamesByConsole = false;
        filterGamesByCategory = false;
        filterBySearchViewQuery = true;

        searchViewQuery = query;
    }


    // holder pattern for listview items
    private class CustomListAdapter extends BaseAdapter {
        Context context;

        private LayoutInflater inflater = null;

        public CustomListAdapter(Context context) {
            // TODO Auto-generated constructor stub
            super();
            this.context = context;
            inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder {
            TextView title;
            TextView consolePublisher;
            TextView esrb;
            TextView release;
            TextView price;
            ImageView imageView;
            Button cartButton;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Holder holder = new Holder();
            final View rowView;
            rowView = inflater.inflate(R.layout.custom_layout_item_listview, null);

            holder.title = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_title);
            holder.consolePublisher = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_console_publisher);
            holder.esrb = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_esrb);
            holder.release = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_release);
            holder.price = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_price);
            holder.imageView = (ImageView) rowView.findViewById(R.id.custom_layout_item_listview_image);
            holder.cartButton = (Button) rowView.findViewById(R.id.custom_layout_item_add_cart);

            ((Button) rowView.findViewById(R.id.custom_layout_item_close_button)).setVisibility(View.GONE);
            ((TextView) rowView.findViewById(R.id.custom_layout_item_amount_edittext)).setVisibility(View.GONE);

            final Item rowItem = itemList.get(position);


            holder.cartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).addItemToCart(rowItem.getItemId(), rowItem.getItemType());
                }
            });


            holder.title.setText(rowItem.getName());
            holder.release.setText("Published " + rowItem.getDatePublished());
            holder.price.setText(Double.toString(rowItem.getPrice()) + "$");


            if (rowItem.getItemType() == ItemVariables.TYPE.CONSOLE) {
                holder.esrb.setVisibility(View.GONE);
                holder.consolePublisher.setText("Console by " + rowItem.getPublisher());

            } else {
                List<ItemVariables.CONSOLES> consolesList = databaseManager.getConsolesFromGameId(rowItem.getItemId());

                String console = "";
                if (consolesList.size() == 1) {
                    console = Helper.convertConsoleToString(consolesList.get(0));
                } else if (consolesList.size() == 0) {
                    console = "No consoles";
                } else {
                    console = "2+ Consoles";
                }

                holder.consolePublisher.setText(console + " by " + rowItem.getPublisher());
                holder.esrb.setText("ESRB: " + Helper.convertESRBToString(((VideoGame) rowItem).getesrbRating()));

            }

            if (position < imagesURLList.size() && cachedImages[position] == null)
                new ImageLoader(holder.imageView, new ImageLoader.AsyncResponse() {
                    @Override
                    public void processFinish(Bitmap output) {
                        // using this method for caching
                        cachedImages[position] = output;
                    }
                }).execute(imagesURLList.get(position));
            else
                holder.imageView.setImageBitmap(cachedImages[position]);

            rowView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ripple_normal));

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchView.clearFocus();
                    ((MainActivity) getActivity()).setItemIdToOpenAtInfoLaunch(rowItem.getItemId(), rowItem.getItemType());
                    ((MainActivity) getActivity()).displayFragment(R.layout.fragment_item_info);
                }
            });


            return rowView;

        }
    }

    private class LoadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            loading = true;
            imagesURLList.clear();

            if (filterByConsole) {

                if(consoleToFilter == null){
                    itemList = databaseManager.getAllConsoles();
                }else{
                    itemList = databaseManager.getConsolesByType(consoleToFilter);
                }

            } else if (filterGamesByConsole) {
                if(gameByConsoleToFilter == null){
                    itemList = databaseManager.getAllGames();
                }else{
                    itemList = databaseManager.getGamesFromConsoleType(gameByConsoleToFilter);
                }

            } else if (filterGamesByCategory) {
                if(gameByCategoryFilter == null){
                    itemList = databaseManager.getAllGames();
                }else{
                    itemList = databaseManager.getGamesByCategory(gameByCategoryFilter);
                }
            } else {
                if (searchViewQuery.isEmpty())
                    itemList = databaseManager.getAllItems();
                else
                    itemList = databaseManager.getItemsByQuery(searchViewQuery);
            }


            if (!itemList.isEmpty()) {

                List<ItemImage> itemImages = new ArrayList<>();
                for (int counter = 0; counter < itemList.size(); counter++) {
                    itemImages.clear();
                    Item currentItem = itemList.get(counter);

                    if (currentItem.getItemType() == ItemVariables.TYPE.CONSOLE) {
                        itemImages = databaseManager.getImagesFromConsoleId(currentItem.getItemId(), true);
                    } else {
                        itemImages = databaseManager.getImagesFromGameId(currentItem.getItemId(), true);
                    }

                    if (itemImages.isEmpty()) {
                        imagesURLList.add("");
                    } else {
                        String url = (itemImages.get(0).getImageURL());
                        imagesURLList.add(url);
                    }
                }

            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            customListAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);

            if(!filterBySearchViewQuery)
                searchView.setQuery("", false);

            if (itemList.isEmpty()) {
                listView.setVisibility(View.GONE);
                noResultsTextView.setVisibility(View.VISIBLE);
                noResultsTextView.setText("No results for query " + searchViewQuery + "");
            } else {
                listView.setVisibility(View.VISIBLE);
                noResultsTextView.setVisibility(View.GONE);
            }

            cachedImages = new Bitmap[itemList.size()];

            setNumberOfResults();



            loading = false;

        }
    }


}
