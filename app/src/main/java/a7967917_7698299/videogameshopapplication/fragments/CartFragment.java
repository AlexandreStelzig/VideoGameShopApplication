package a7967917_7698299.videogameshopapplication.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class CartFragment extends Fragment {


    // components
    private ListView listView;
    private View view;
    private ProgressBar progressBar;
    private LinearLayout noResultsLayout;
    private TextView totalTextView;
    private Button checkoutButton;

    // database
    DatabaseManager databaseManager;

    private int positionSelected = -1;

    // items
    private List<Item> itemList;
    private List<CartItem> cartItemList;
    private CustomListAdapter customListAdapter;
    private List<String> imagesURLList;
    private Bitmap[] cachedImages;

    boolean loading = false;
    int amountHolder = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);
        setHasOptionsMenu(true);

        databaseManager = DatabaseManager.getInstance();


        // init components
        listView = (ListView) view.findViewById(R.id.fragment_cart_list_listview);
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_cart_list_progress_bar);
        progressBar.setVisibility(View.GONE);
        noResultsLayout = (LinearLayout) view.findViewById(R.id.fragment_cart_list_no_results_layout);
        noResultsLayout.setVisibility(View.GONE);
        totalTextView = (TextView) view.findViewById(R.id.fragment_cart_list_total_textview);
        checkoutButton = (Button) view.findViewById(R.id.fragment_cart_list_checkout_button);

        imagesURLList = new ArrayList<>();
        itemList = new ArrayList<>();
        cachedImages = new Bitmap[itemList.size()];
        cartItemList = new ArrayList<>();

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!itemList.isEmpty())
                    ((MainActivity) getActivity()).displayFragment(R.layout.fragment_checkout);
                else
                    Toast.makeText(getContext(), "No items in Cart", Toast.LENGTH_SHORT).show();
            }
        });


        initListView();
        changeTotalText();

        return view;
    }


    private void initListView() {

        customListAdapter = new CustomListAdapter(getContext());
        listView.setAdapter(customListAdapter);
        populateListView();
    }

    private void populateListView() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        noResultsLayout.setVisibility(View.GONE);
        new LoadData().execute("");
    }


    private void changeTotalText() {
        double total = 0;

        for (int i = 0; i < itemList.size(); i++) {

            total += itemList.get(i).getPrice() * cartItemList.get(i).getAmount();

        }

        totalTextView.setText("Subtotal: " + String.format("%.2f$", total));
    }

    private void amountDialog() {

        amountHolder = cartItemList.get(positionSelected).getAmount();

        final View dialogView = View.inflate(getContext(), R.layout.custom_layout_amount_dialog, null);

        final TextView textView = (TextView) dialogView.findViewById(R.id.custom_layout_amount_textview);
        textView.setText(amountHolder + "");

        final Button minusButton = (Button) dialogView.findViewById(R.id.custom_layout_amount_minus);

        final Button addButton = (Button) dialogView.findViewById(R.id.custom_layout_amount_add);

        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getContext());
        alertDialog.setMessage(itemList.get(positionSelected).getPrice() + "$ each");
        alertDialog.setTitle(itemList.get(positionSelected).getName() + " Amount");

        alertDialog.setView(dialogView);


        if (amountHolder == 1)
            minusButton.setEnabled(false);

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountHolder > 1)
                    amountHolder--;
                if (amountHolder == 1)
                    minusButton.setEnabled(false);
                textView.setText(amountHolder + "");
                alertDialog.setMessage(itemList.get(positionSelected).getPrice() + "$ each");
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountHolder++;
                minusButton.setEnabled(true);
                textView.setText(amountHolder + "");
                alertDialog.setMessage("Total: " + amountHolder * itemList.get(positionSelected).getPrice() + "$");
            }
        });


        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                databaseManager.updateCartAmount(itemList.get(positionSelected).getItemType(), cartItemList.get(positionSelected).getItemId(), amountHolder);

                cartItemList.get(positionSelected).setAmount(amountHolder);
                customListAdapter.notifyDataSetChanged();
                changeTotalText();
                ((MainActivity) getActivity()).invalidateOptionsMenu();
            }
        });

        // Setting cancel Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        // Showing Alert Message
        alertDialog.show();
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


            ((Button) rowView.findViewById(R.id.custom_layout_item_close_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    databaseManager.deleteCartItem(rowItem.getItemId(), rowItem.getItemType());

                    List<Bitmap> list = new ArrayList<Bitmap>(Arrays.asList(cachedImages));
                    list.remove(position);
                    cachedImages = list.toArray(cachedImages);

                    imagesURLList.remove(position);
                    itemList.remove(position);
                    cartItemList.remove(position);

                    customListAdapter.notifyDataSetChanged();

                    if (itemList.isEmpty()) {
                        listView.setVisibility(View.GONE);
                        noResultsLayout.setVisibility(View.VISIBLE);
                    } else {
                        listView.setVisibility(View.VISIBLE);
                        noResultsLayout.setVisibility(View.GONE);
                    }

                    getActivity().invalidateOptionsMenu();
                    changeTotalText();
                }
            });


            // amount
            holder.amount.setText("x" + cartItemList.get(position).getAmount() + "");
            holder.amount.setPaintFlags(holder.amount.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            holder.amount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    positionSelected = position;
                    amountDialog();
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


            cartItemList = databaseManager.getAllCartItems();
            itemList = databaseManager.getCurrentActiveUserItemsInCart();


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

            cachedImages = new Bitmap[itemList.size()];

            changeTotalText();

            loading = false;

        }
    }

}
