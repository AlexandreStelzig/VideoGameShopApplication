package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.helper.ImageLoader;
import a7967917_7698299.videogameshopapplication.manager.SortingManager;
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
    private Spinner filterSpinner;

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
    private String searchViewQuery = "";

    private boolean filterByConsole;
    private boolean filterGamesByConsole;
    private boolean filterGamesByCategory;
    private boolean filterBySearchViewQuery;

    private int sortSelected = 0;
    private int consoleSelected = -1;
    private int categorySelected = -1;

    private boolean refreshData = true;


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
        setupPopupWindows();


        return view;
    }

    // first time doing popupwindows - might be a bit ugly but seems to work
    private void setupPopupWindows() {


        final ListPopupWindow consolesListPopupWindow = new ListPopupWindow(
                getContext());
        final ListPopupWindow filterListPopupWindow = new ListPopupWindow(
                getContext());
        final ListPopupWindow categoryListPopupWindow = new ListPopupWindow(
                getContext());

        Button filterButton = (Button) view.findViewById(R.id.fragment_results_filter_button);


        // CATEGORIES WINDOW
        final String[] categoriesOption = new String[]{"Back", "Action", "Adventure", "RPG", "Sports"};

        categoryListPopupWindow.setAdapter(new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, categoriesOption) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;

                if (position == 0) {
                    mTextView.setText("< Back");
                    mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                    mTextView.setTypeface(null, Typeface.BOLD);
                } else if (filterGamesByCategory || gameByCategoryFilter != null) {
                    if (categorySelected == position) {
                        mTextView.setTypeface(null, Typeface.BOLD);
                        mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    } else {
                        mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                        mTextView.setTypeface(null, Typeface.NORMAL);
                    }

                } else {
                    mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                    mTextView.setTypeface(null, Typeface.NORMAL);
                }


                return mTextView;
            }
        });
        categoryListPopupWindow.setAnchorView(filterButton);
        categoryListPopupWindow.setWidth((400));
        categoryListPopupWindow.setModal(true);
        categoryListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoryListPopupWindow.dismiss();
                refreshData = false;
                switch (position) {
                    case 0:
                        filterListPopupWindow.show();
                        break;
                    case 1:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_game_action);
                        break;
                    case 2:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_game_adventure);
                        break;
                    case 3:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_game_rpg);
                        break;
                    case 4:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_game_sport);
                        break;
                }
            }
        });

        // CONSOLES WINDOW
        String[] consoleOptions = new String[]{"Back", "Nintendo Switch", "Nintendo 3DS", "PS4", "Xbox One"};

        consolesListPopupWindow.setAdapter(new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, consoleOptions) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;

                if (position == 0) {
                    mTextView.setText("< Back");
                    mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                    mTextView.setTypeface(null, Typeface.BOLD);
                } else if (filterByConsole || consoleToFilter != null) {
                    if (consoleSelected == position) {
                        mTextView.setTypeface(null, Typeface.BOLD);
                        mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    } else {
                        mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                        mTextView.setTypeface(null, Typeface.NORMAL);
                    }

                } else if (filterGamesByConsole || gameByConsoleToFilter != null) {
                    if (consoleSelected == position) {
                        mTextView.setTypeface(null, Typeface.BOLD);
                        mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    } else {
                        mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                        mTextView.setTypeface(null, Typeface.NORMAL);
                    }
                } else {
                    mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                    mTextView.setTypeface(null, Typeface.NORMAL);
                }


                return mTextView;
            }
        });
        consolesListPopupWindow.setAnchorView(filterButton);
        consolesListPopupWindow.setWidth((500));
        consolesListPopupWindow.setModal(true);
        consolesListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                consolesListPopupWindow.dismiss();
                refreshData = false;
                switch (position) {
                    case 0:
                        filterListPopupWindow.show();
                        break;
                    case 1:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_switch);
                        break;
                    case 2:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_3ds);
                        break;
                    case 3:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_ps4);
                        break;
                    case 4:
                        ((MainActivity) getActivity()).displayFragment(R.id.nav_xbox);
                        break;
                }
            }
        });


        // FILTER WINDOW
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterListPopupWindow.show();
            }
        });

        String[] filterOptions = new String[]{"Consoles  >", "Category  >", "X Clear Filters"};


        filterListPopupWindow.setAdapter(new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line, filterOptions) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;


                mTextView.setTypeface(null, Typeface.NORMAL);

                if (position == 0) {
                    if (consoleSelected != -1)
                        mTextView.setText("(1) Consoles >");
                    else
                        mTextView.setText("Consoles >");
                } else if (position == 1) {
                    if (categorySelected != -1)
                        mTextView.setText("(1) Categories >");
                    else
                        mTextView.setText("Categories >");
                } else if (position == 2) {
                    mTextView.setTypeface(null, Typeface.BOLD);
                }

                mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));


                return mTextView;
            }

        });
        filterListPopupWindow.setAnchorView(filterButton);
        filterListPopupWindow.setWidth((400));


        filterListPopupWindow.setModal(true);
        filterListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                filterListPopupWindow.dismiss();
                if (position == 0)
                    consolesListPopupWindow.show();
                else if (position == 1) {
                    categoryListPopupWindow.show();
                } else {
                    setSearchViewQuery("");
                    refreshData = true;
                    ((MainActivity) getActivity()).displayFragment(R.id.search_view_results);
                }
            }
        });


        // SORT WINDOW

        final ListPopupWindow sortListPopupWindow = new ListPopupWindow(
                getContext());

        Button sortButton = (Button) view.findViewById(R.id.fragment_results_sort_button);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortListPopupWindow.show();
            }
        });

        String[] sortOptions = new String[]{"Name (A-Z)", "Name (Z-A)", "Price (Low-High)", "Price (High - Low)"};


        sortListPopupWindow.setAdapter(new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1, sortOptions) {


            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View mView = super.getDropDownView(position, convertView, parent);
                TextView mTextView = (TextView) mView;

                if (sortSelected == position) {
                    mTextView.setTypeface(null, Typeface.BOLD);
                    mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                } else {
                    mTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.primaryText));
                    mTextView.setTypeface(null, Typeface.NORMAL);
                }


                return mTextView;
            }


        });
        sortListPopupWindow.setAnchorView(sortButton);
        sortListPopupWindow.setWidth((500));


        sortListPopupWindow.setModal(true);
        sortListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sortListPopupWindow.setSelection(position);
                sortSelected = position;

                sortListPopupWindow.dismiss();

                // ugly but okay
                SortingManager sortingManager = SortingManager.getInstance();
                switch (position) {
                    case 0:
                        sortingManager.setCurrentState(SortingManager.SortingStates.NAME_AZ);
                        break;
                    case 1:
                        sortingManager.setCurrentState(SortingManager.SortingStates.NAME_ZA);
                        break;
                    case 2:
                        sortingManager.setCurrentState(SortingManager.SortingStates.PRICE_HIGH_LOW);
                        break;
                    case 3:
                        sortingManager.setCurrentState(SortingManager.SortingStates.PRICE_LOW_HIGH);
                        break;
                }
                populateListView();

                listView.smoothScrollToPosition(0);


            }
        });

//when you click on yourButtonViewObject show listPopupWindow like this


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

                if(searchViewQuery != null && s.isEmpty() && !searchViewQuery.isEmpty()){
//                    setSearchViewQuery("");
//                    ((MainActivity) getActivity()).displayFragment(R.id.search_view_results);
                }



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


        if (refreshData) {
            if (filterByConsole) {
                if (consoleToFilter == null) {
                    text += ": ALL CONSOLES";
                } else {
                    text += ": " + Helper.convertConsoleToString(consoleToFilter) + " consoles";
                }
            } else if (filterGamesByConsole) {
                if (gameByConsoleToFilter == null) {
                    text += ": ALL GAMES";
                } else {
                    text += ": " + Helper.convertConsoleToString(gameByConsoleToFilter) + " games";
                }

            } else if (filterGamesByCategory) {
                if (gameByCategoryFilter == null) {
                    text += ": ALL GAMES";
                } else {
                    text += ": " + gameByCategoryFilter.toString();
                }
            } else {
                if (searchViewQuery.equals(""))
                    text += ": ALL";
                else
                    text += ": " + searchViewQuery;

            }
        } else {
            text += ": custom query";
        }

        nbResultsTextView.setText(text);
    }

    public void setFilterByConsole(ItemVariables.CONSOLES consoleToFilterBy) {


        filterByConsole = true;
        filterGamesByConsole = false;
        filterGamesByCategory = false;
        filterBySearchViewQuery = false;

        if (consoleToFilterBy == ItemVariables.CONSOLES.SWITCH)
            consoleSelected = 1;
        else if (consoleToFilterBy == ItemVariables.CONSOLES.THREE_DS)
            consoleSelected = 2;
        else if (consoleToFilterBy == ItemVariables.CONSOLES.PS4)
            consoleSelected = 3;
        else if (consoleToFilterBy == ItemVariables.CONSOLES.XBOXONE)
            consoleSelected = 4;

        if (refreshData) {
            categorySelected = -1;
        }
        consoleToFilter = consoleToFilterBy;
    }

    public void setFilterGamesByConsole(ItemVariables.CONSOLES consoleToFilterBy) {

        filterByConsole = false;
        filterGamesByConsole = true;
        filterGamesByCategory = false;
        filterBySearchViewQuery = false;

        if (consoleToFilterBy == ItemVariables.CONSOLES.SWITCH)
            consoleSelected = 1;
        else if (consoleToFilterBy == ItemVariables.CONSOLES.THREE_DS)
            consoleSelected = 2;
        else if (consoleToFilterBy == ItemVariables.CONSOLES.PS4)
            consoleSelected = 3;
        else if (consoleToFilterBy == ItemVariables.CONSOLES.XBOXONE)
            consoleSelected = 4;

        if (refreshData) {
            categorySelected = -1;
        }
        gameByConsoleToFilter = consoleToFilterBy;

    }

    public void setFilterGamesByCategory(VideoGameVariables.CATEGORY categoryToFilterBy) {


        filterByConsole = false;
        filterGamesByConsole = false;
        filterGamesByCategory = true;
        filterBySearchViewQuery = false;

        if (categoryToFilterBy == VideoGameVariables.CATEGORY.ACTION)
            categorySelected = 1;
        else if (categoryToFilterBy == VideoGameVariables.CATEGORY.ADVENTURE)
            categorySelected = 2;
        else if (categoryToFilterBy == VideoGameVariables.CATEGORY.RPG)
            categorySelected = 3;
        else if (categoryToFilterBy == VideoGameVariables.CATEGORY.SPORTS)
            categorySelected = 4;


        if (refreshData) {
            consoleSelected = -1;
        }

        gameByCategoryFilter = categoryToFilterBy;
    }

    public void setSearchViewQuery(String query) {
        filterByConsole = false;
        filterGamesByConsole = false;
        filterGamesByCategory = false;
        filterBySearchViewQuery = true;

        if (refreshData) {
            consoleSelected = -1;
            categorySelected = -1;
        }


        searchViewQuery = query;
    }

    private void filterList() {

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

            if (position < imagesURLList.size() && cachedImages[position] == null) {
                holder.imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(),
                        R.drawable.loading));
                new ImageLoader(holder.imageView, new ImageLoader.AsyncResponse() {
                    @Override
                    public void processFinish(Bitmap output) {
                        // using this method for caching
                        cachedImages[position] = output;
                    }
                }, getContext()).execute(imagesURLList.get(position));
            } else
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


            if (refreshData) {

                itemList.clear();

                if (filterByConsole) {

                    if (consoleToFilter == null) {
                        itemList = databaseManager.getAllConsoles();
                    } else {
                        itemList = databaseManager.getConsolesByType(consoleToFilter);
                    }

                } else if (filterGamesByConsole) {
                    if (gameByConsoleToFilter == null) {
                        itemList = databaseManager.getAllGames();
                    } else {
                        itemList = databaseManager.getGamesFromConsoleType(gameByConsoleToFilter);
                    }

                } else if (filterGamesByCategory) {
                    if (gameByCategoryFilter == null) {
                        itemList = databaseManager.getAllGames();
                    } else {
                        itemList = databaseManager.getGamesByCategory(gameByCategoryFilter);
                    }
                } else {
                    if (searchViewQuery.isEmpty())
                        itemList = databaseManager.getAllItems();
                    else
                        itemList = databaseManager.getItemsByQuery(searchViewQuery);
                }
            } else {

                // FILTERS STUFF
                // get the query
                if (!searchViewQuery.isEmpty()) {
                    itemList = databaseManager.getItemsByQuery(searchViewQuery);
                } else {
                    itemList = databaseManager.getAllItems();
                }

                // filter the categories
                if (categorySelected != -1) {
                    VideoGameVariables.CATEGORY categoryToFilter = VideoGameVariables.CATEGORY.ACTION;

                    switch (categorySelected) {
                        case 1:
                            categoryToFilter = VideoGameVariables.CATEGORY.ACTION;
                            break;
                        case 2:
                            categoryToFilter = VideoGameVariables.CATEGORY.ADVENTURE;
                            break;
                        case 3:
                            categoryToFilter = VideoGameVariables.CATEGORY.RPG;
                            break;
                        case 4:
                            categoryToFilter = VideoGameVariables.CATEGORY.SPORTS;
                            break;
                    }

                    List<Item> itemsTemp = new ArrayList<>();
                    for (int i = 0; i < itemList.size(); i++) {
                        if (itemList.get(i).getItemType() == ItemVariables.TYPE.GAME) {
                            if (((VideoGame) itemList.get(i)).getGameCategory() == categoryToFilter) {
                                itemsTemp.add(itemList.get(i));
                            }
                        }
                    }

                    itemList = itemsTemp;
                }

                // filter the consoles
                if (consoleSelected != -1) {
                    ItemVariables.CONSOLES consoleToFilter = ItemVariables.CONSOLES.SWITCH;

                    switch (consoleSelected) {
                        case 1:
                            consoleToFilter = ItemVariables.CONSOLES.SWITCH;
                            break;
                        case 2:
                            consoleToFilter = ItemVariables.CONSOLES.THREE_DS;
                            break;
                        case 3:
                            consoleToFilter = ItemVariables.CONSOLES.PS4;
                            break;
                        case 4:
                            consoleToFilter = ItemVariables.CONSOLES.XBOXONE;
                            break;
                    }

                    List<Item> itemsTemp = new ArrayList<>();
                    for (int i = 0; i < itemList.size(); i++) {
                        if (itemList.get(i).getItemType() == ItemVariables.TYPE.GAME) {

                            VideoGame game = ((VideoGame) itemList.get(i));

                            List<Item> videoGamesIncluded = databaseManager.getGamesFromConsoleType(consoleToFilter);

                            for (int consoleCounter = 0; consoleCounter < videoGamesIncluded.size(); consoleCounter++) {

                                if (videoGamesIncluded.get(consoleCounter).getItemId() == game.getItemId()) {
                                    itemsTemp.add(game);
                                }
                            }


                        } else {
                            if (((Console) itemList.get(i)).getConsoleType() != consoleToFilter) {
                                itemList.remove(i);
                            }
                        }
                    }
                    itemList = itemsTemp;
                }


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

            if (!filterBySearchViewQuery && !refreshData)
                searchView.setQuery("", false);
            if(!refreshData)
                searchView.setQuery(searchViewQuery, false);

            if (itemList.isEmpty()) {
                listView.setVisibility(View.GONE);
                noResultsTextView.setVisibility(View.VISIBLE);
                if (searchViewQuery != null && !searchViewQuery.isEmpty())
                    noResultsTextView.setText("No results for query " + searchViewQuery + "");
                else
                    noResultsTextView.setText("No results for query");
            } else {
                listView.setVisibility(View.VISIBLE);
                noResultsTextView.setVisibility(View.GONE);
            }

            cachedImages = new Bitmap[itemList.size()];

            setNumberOfResults();


            loading = false;
            refreshData = true;

        }
    }

    public void setRefreshData(boolean refreshData) {
        this.refreshData = refreshData;
    }
}
