package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.model.Console;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.Order;
import a7967917_7698299.videogameshopapplication.model.OrderItem;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class OrderListFragment extends Fragment{


    // components
    private ListView listView;
    private View view;
    private TextView nbResultsTextView;
    private ProgressBar progressBar;
    private LinearLayout noResultsLayout;


    private DatabaseManager databaseManager;

    // items
    private List<Order> orders;
    private CustomListAdapter customListAdapter;

    boolean loading = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_list, container, false);
        setHasOptionsMenu(true);

        databaseManager = DatabaseManager.getInstance();


        // init components
        listView = (ListView) view.findViewById(R.id.fragment_order_list_listview);
        nbResultsTextView = (TextView) view.findViewById(R.id.fragment_order_list_nb_results);
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_order_list_progress_bar);
        progressBar.setVisibility(View.GONE);
        noResultsLayout = (LinearLayout) view.findViewById(R.id.fragment_order_list_no_results_layout);
        noResultsLayout.setVisibility(View.GONE);

        orders = new ArrayList<>();

        initListView();
//        setNumberOfResults();

        return view;
    }



    private void initListView() {

        customListAdapter = new CustomListAdapter(getContext());
        listView.setAdapter(customListAdapter);
        populateListView();
    }

//    private void setNumberOfResults() {
//        int nbResults = itemList.size();
//        String text = nbResults + " ";
//        if (nbResults == 1)
//            text += "WISHLIST ITEM";
//        else
//            text += "WISHLIST ITEMS";
//
//        nbResultsTextView.setText(text);
//    }


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
            return orders.size();
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
            TextView id;
            TextView dateOrdered;
            TextView status;
            TextView price;
            ListView listView;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final CustomListAdapter.Holder holder = new CustomListAdapter.Holder();
            final View rowView;
            rowView = inflater.inflate(R.layout.custom_layout_order_listview, null);

            holder.id = (TextView) rowView.findViewById(R.id.custom_layout_order_id);
            holder.dateOrdered = (TextView) rowView.findViewById(R.id.custom_layout_order_date);
            holder.status = (TextView) rowView.findViewById(R.id.custom_layout_order_status);
            holder.price = (TextView) rowView.findViewById(R.id.custom_layout_order_price);
            holder.listView = (ListView) rowView.findViewById(R.id.custom_layout_order_listview);


            Order rowOrder = orders.get(position);
            List<OrderItem> orderItems = databaseManager.getOrderItemsFromOrderId(rowOrder.getOrderId());
            List<Item> items = new ArrayList<>();

            for(int i = 0; i < orderItems.size(); i++){
                OrderItem temp = orderItems.get(i);
                if(temp.getItemType() == ItemVariables.TYPE.CONSOLE){
                    items.add(databaseManager.getConsoleById(temp.getItemId()));
                }else{
                    items.add(databaseManager.getGameById(temp.getItemId()));
                }
            }

            holder.id.setText("Order Id: " + rowOrder.getOrderId());
            holder.dateOrdered.setText("Date Ordered: " + rowOrder.getDateOrdered());
            holder.status.setText("Status: " + rowOrder.getStatus());


            double total = 0;
            for(int i = 0; i < orderItems.size(); i++){
                total += orderItems.get(i).getAmount() * items.get(i).getPrice();
            }

            holder.price.setText("Total: " + String.format("%.2f$", total));

            List<String> orderItemName = new ArrayList<>();
            for(int i = 0; i < items.size(); i++)
                orderItemName.add(items.get(i).getName());

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, orderItemName);
            holder.listView.setAdapter(arrayAdapter);

//            Helper.setListViewHeightBasedOnChildren(holder.listView);

            return rowView;

        }
    }

    private class LoadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            loading = true;

            orders = databaseManager.getAllOrdersFromActiveUser();

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            customListAdapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);

            if (orders.isEmpty()) {
                listView.setVisibility(View.GONE);
                noResultsLayout.setVisibility(View.VISIBLE);
            } else {
                listView.setVisibility(View.VISIBLE);
                noResultsLayout.setVisibility(View.GONE);
            }


//            setNumberOfResults();

            loading = false;

        }
    }

}
