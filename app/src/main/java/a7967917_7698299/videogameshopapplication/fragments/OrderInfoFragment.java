package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.helper.ImageLoader;
import a7967917_7698299.videogameshopapplication.model.CartItem;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.Order;
import a7967917_7698299.videogameshopapplication.model.OrderItem;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class OrderInfoFragment extends Fragment {


    // components
    private ListView listView;
    private View view;
    private TextView orderIdTextView;
    private TextView orderStatusTextView;
    private TextView dateOrderedTextView;
    private TextView subtotalTextView;
    private TextView shippingTextView;
    private TextView taxesTextView;
    private TextView totalTextView;
    private TextView shipNameTextView;
    private TextView shipAddressTextView;
    private TextView shipCityStateTextView;
    private TextView shipCountryPostalTextView;
    private TextView paymentNameTextView;
    private TextView paymentCardNumberTextView;


    // initialized by setOrderIdToOpenAtOrderInfoLaunch method before opening fragment
    private long orderId;
    private Order order;

    // database
    private DatabaseManager databaseManager;

    // items
    private List<Item> itemList;
    private List<OrderItem> orderItems;
    private CustomListAdapter customListAdapter;
    private List<String> imagesURLList;
    private Bitmap[] cachedImages;

    boolean loading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_info, container, false);
        setHasOptionsMenu(true);

        listView = (ListView) view.findViewById(R.id.fragment_order_info_listview);

        databaseManager = DatabaseManager.getInstance();
        order = databaseManager.getOrderByOrderId(orderId);

        orderIdTextView = (TextView) view.findViewById(R.id.fragment_order_info_order_id) ;
        orderStatusTextView = (TextView) view.findViewById(R.id.fragment_order_info_status) ;
        dateOrderedTextView = (TextView) view.findViewById(R.id.fragment_order_info_date_ordered) ;
        subtotalTextView = (TextView) view.findViewById(R.id.fragment_order_info_subtotal) ;
        shippingTextView = (TextView) view.findViewById(R.id.fragment_order_info_shipment) ;
        taxesTextView = (TextView) view.findViewById(R.id.fragment_order_info_taxes) ;
        totalTextView = (TextView) view.findViewById(R.id.fragment_order_info_total) ;
        shipNameTextView = (TextView) view.findViewById(R.id.fragment_order_info_ship_name) ;
        shipAddressTextView = (TextView) view.findViewById(R.id.fragment_order_info_ship_address) ;
        shipCityStateTextView = (TextView) view.findViewById(R.id.fragment_order_info_ship_city_state) ;
        shipCountryPostalTextView = (TextView) view.findViewById(R.id.fragment_order_info_ship_country) ;
        paymentNameTextView = (TextView) view.findViewById(R.id.fragment_order_info_card_name);
        paymentCardNumberTextView = (TextView) view.findViewById(R.id.fragment_order_info_card_number) ;



        imagesURLList = new ArrayList<>();
        itemList = new ArrayList<>();
        cachedImages = new Bitmap[itemList.size()];
        orderItems = new ArrayList<>();

        initComponents();
        initListView();

        return view;
    }


    // CALL THIS METHOD BEFORE OPENING FRAGMENT
    public void setOrderIdToOpenAtOrderInfoLaunch(long orderId) {
        this.orderId = orderId;
    }


    private void initComponents() {

        orderIdTextView.setText("Order ID: " + order.getOrderId());
        orderStatusTextView.setText("Status: " + order.getStatus());
        dateOrderedTextView.setText("Date Ordered: " + order.getDateOrdered());
        subtotalTextView.setText("0000$");
        shippingTextView.setText("0000$");
        taxesTextView.setText("0000$");
        totalTextView.setText("0000$");
        shipNameTextView.setText(order.getDeliverTo());
        shipAddressTextView.setText( order.getStreet());
        shipCityStateTextView.setText(order.getCity() +", "+ order.getState());
        shipCountryPostalTextView.setText(order.getCountry() + ", " + order.getPostalCode());
        paymentNameTextView.setText(order.getNameOnCard());
        paymentCardNumberTextView.setText("Card Number: " + order.getCardNumber());

    }

    private void initListView() {

        customListAdapter = new CustomListAdapter(getContext());
        listView.setAdapter(customListAdapter);
        populateListView();
    }


    private void populateListView() {

        listView.setVisibility(View.GONE);
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
            TextView amount;

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
            holder.amount = (TextView) rowView.findViewById(R.id.custom_layout_item_amount_edittext);

            final Item rowItem = itemList.get(position);

            ((Button) rowView.findViewById(R.id.custom_layout_item_add_cart)).setVisibility(View.GONE);
            ((Button) rowView.findViewById(R.id.custom_layout_item_close_button)).setVisibility(View.GONE);

            // amount
            holder.amount.setText("x" + orderItems.get(position).getAmount() + "");

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


            orderItems = databaseManager.getOrderItemsFromOrderId(orderId);
            itemList.clear();

            for (int i = 0; i < orderItems.size(); i++) {

                OrderItem orderItemTemp = orderItems.get(i);

                if (orderItemTemp.getItemType() == ItemVariables.TYPE.CONSOLE) {
                    itemList.add(databaseManager.getConsoleById(orderItemTemp.getItemId()));
                } else {
                    itemList.add(databaseManager.getGameById(orderItemTemp.getItemId()));
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

            listView.setVisibility(View.VISIBLE);

            cachedImages = new Bitmap[itemList.size()];

            Helper.setListViewHeightBasedOnChildren(listView);

            loading = false;

        }
    }
}
