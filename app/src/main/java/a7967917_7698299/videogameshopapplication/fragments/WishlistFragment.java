package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class WishlistFragment extends Fragment {


    // components
    private ListView listView;
    private View view;
    private TextView nbResultsTextView;
    private ProgressBar progressBar;
    private LinearLayout noResultsLayout;

    // database
    DatabaseManager databaseManager;

    // items
    private List<Item> itemList;
    private CustomListAdapter customListAdapter;
    private List<String> imagesURLList;

    boolean loading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        setHasOptionsMenu(true);
        databaseManager = DatabaseManager.getInstance();


        // init components
        listView = (ListView) view.findViewById(R.id.fragment_wishlist_listview);
        nbResultsTextView = (TextView) view.findViewById(R.id.fragment_wishlist_nb_results);
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_wishlist_progress_bar);
        progressBar.setVisibility(View.GONE);
        noResultsLayout = (LinearLayout) view.findViewById(R.id.fragment_wishlist_no_results_layout);
        noResultsLayout.setVisibility(View.GONE);


        imagesURLList = new ArrayList<>();
        itemList = new ArrayList<>();

        initListView();
        setNumberOfResults();

        return view;
    }


    private void initListView() {

        customListAdapter = new CustomListAdapter(getContext());
        listView.setAdapter(customListAdapter);
        populateListView();
    }

    private void setNumberOfResults() {
        int nbResults = itemList.size();
        String text = nbResults + " ";
        if (nbResults == 1)
            text += "WISHLIST ITEM";
        else
            text += "WISHLIST ITEMS";

        nbResultsTextView.setText(text);
    }


    private void populateListView() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        noResultsLayout.setVisibility(View.GONE);
        new LoadData().execute("");
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

            final CustomListAdapter.Holder holder = new CustomListAdapter.Holder();
            final View rowView;
            rowView = inflater.inflate(R.layout.custom_layout_item_listview, null);

            holder.title = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_title);
            holder.consolePublisher = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_console_publisher);
            holder.esrb = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_esrb);
            holder.release = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_release);
            holder.price = (TextView) rowView.findViewById(R.id.custom_layout_item_listview_price);
            holder.imageView = (ImageView) rowView.findViewById(R.id.custom_layout_item_listview_image);
            holder.cartButton = (Button) rowView.findViewById(R.id.custom_layout_item_add_cart);

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
//
            if (position < imagesURLList.size())
                new Helper.ReplaceImageViewWithURL(holder.imageView).execute(imagesURLList.get(position));

            rowView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ripple_normal));

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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

            // TODO change way of fetching data to more generic one

            itemList = databaseManager.getAllWishListItems();


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

            if (itemList.isEmpty()) {
                listView.setVisibility(View.GONE);
                noResultsLayout.setVisibility(View.VISIBLE);
            } else {
                listView.setVisibility(View.VISIBLE);
                noResultsLayout.setVisibility(View.GONE);
            }

            setNumberOfResults();

            loading = false;

        }
    }


}
