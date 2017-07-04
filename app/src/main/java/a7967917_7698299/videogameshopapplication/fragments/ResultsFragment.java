package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.model.Console;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

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

    // Database
    private DatabaseManager databaseManager;

    // query
    private String searchViewQuery;

    // items
    private List<Item> itemList;
    private CustomListAdapter customListAdapter;
    private List<Bitmap> imagesList;

    private boolean loading = false;

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

        // init stuff
        itemList = new ArrayList<>();
        imagesList = new ArrayList<>();
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

//        searchView.setQuery("", false);
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

                populateListView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        // set the query form home menu
        if (searchViewQuery != null){
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

    private void populateListView() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);

        new LoadData().execute("");
    }


    public void setSearchViewQuery(String query) {
        searchViewQuery = query;
    }

    private void setNumberOfResults(){
        int nbResults = itemList.size();
        String text = nbResults + " ";
        if(nbResults == 1)
            text += "RESULT";
        else
            text += "RESULTS";

        nbResultsTextView.setText(text);
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
            TextView ERSB;
            TextView release;
            TextView price;
            ImageView imageView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final Holder holder = new Holder();
            final View rowView;
            rowView = inflater.inflate(R.layout.custom_layout_item_listview, null);

            holder.title = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_title);
            holder.consolePublisher = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_console_publisher);
            holder.ERSB = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_ERSB);
            holder.release = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_release);
            holder.price = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_price);
            holder.imageView = (ImageView) rowView.findViewById(R.id.custom_layout_item_listview_image);


            Item viewItem = itemList.get(position);

            holder.title.setText(viewItem.getName());
            holder.release.setText("Published " + viewItem.getDatePublished());
            holder.price.setText(Double.toString(viewItem.getPrice()) + "$");


            if (viewItem.getItemType() == ItemVariables.TYPE.CONSOLE) {
                  holder.ERSB.setVisibility(View.GONE);
                holder.consolePublisher.setText("Console by " + viewItem.getPublisher());

            } else {
                holder.consolePublisher.setText("Console by " + viewItem.getPublisher());
                holder.ERSB.setText("ESRB: " + ((VideoGame) viewItem).getErsbRating());
            }

            if (position < imagesList.size())
                holder.imageView.setImageBitmap(imagesList.get(position));

            rowView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ripple_normal));

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
            imagesList.clear();



            // TODO change way of fetching data to more generic one
            itemList = databaseManager.getAllItems();



            try {

                String url = ("http://used.agwest.com/images/default-image-agwest-thumb.jpg");
                InputStream in = new java.net.URL(url).openStream();
                Bitmap unavailableImage = BitmapFactory.decodeStream(in);

                List<ItemImage> itemImages = new ArrayList<>();
                for(int counter = 0; counter < itemList.size(); counter++){
                    itemImages.clear();
                        Item currentItem = itemList.get(counter);

                    if(currentItem.getItemType() == ItemVariables.TYPE.CONSOLE){
                        itemImages = databaseManager.getImagesFromConsoleId(((Console)currentItem).getConsoleId(), true);
                    }else{
                        itemImages = databaseManager.getImagesFromGameId(((VideoGame)currentItem).getGameId(), true);
                    }

                    if(itemImages.isEmpty()){
                        imagesList.add(unavailableImage);
                    }else{
                        url = (itemImages.get(0).getImageURL());
                        in = new java.net.URL(url).openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        imagesList.add(bitmap);
                    }


                }

//                url = ();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            customListAdapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            setNumberOfResults();

            loading = false;

        }
    }


}
