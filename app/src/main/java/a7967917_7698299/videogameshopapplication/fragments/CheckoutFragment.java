package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.helper.ImageLoader;
import a7967917_7698299.videogameshopapplication.model.CartItem;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.PaymentInformation;
import a7967917_7698299.videogameshopapplication.model.UserAddress;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.OrderVariables;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class CheckoutFragment extends Fragment{


    // components
    private ListView listView;
    private TextView totalTextView;
    private View view;
    private Button confirmButton;
    private Button cancelButton;
    private RadioGroup radioPayment;
    private RadioGroup radioAddress;

    // database
    DatabaseManager databaseManager;

    private int positionSelected = -1;

    // items
    private List<Item> itemList;
    private List<CartItem> cartItemList;
    private CheckoutFragment.CustomListAdapter customListAdapter;
    private List<PaymentInformation> paymentMethods;
    private List<UserAddress> shippingAddresses;

    int amountHolder = 0;
    boolean extraShipping = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_checkout, container, false);
        setHasOptionsMenu(true);
        databaseManager = DatabaseManager.getInstance();
        listView = (ListView) view.findViewById(R.id.orderListCheckout);
        totalTextView = (TextView) view.findViewById(R.id.textCheckoutTotal);
        itemList = new ArrayList<>();
        cartItemList = new ArrayList<>();
        cartItemList = databaseManager.getAllCartItems();
        itemList = databaseManager.getCurrentActiveUserItemsInCart();
        paymentMethods = databaseManager.getAllPaymentMethodsFromActiveUser();
        shippingAddresses = databaseManager.getAllAddressesFromActiveUser();
        confirmButton = (Button) view.findViewById(R.id.confirmCheckoutButton);
        cancelButton = (Button) view.findViewById(R.id.cancelCheckoutButton);
        radioAddress = (RadioGroup) view.findViewById(R.id.radioAddress);
        radioPayment = (RadioGroup) view.findViewById(R.id.radioPayment);
        setPayment();
        setShipping();
        RadioGroup shipping = (RadioGroup) view.findViewById(R.id.radioShipping);
        shipping.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                changeTotalText();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paymentMethods.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(),"No payment methods exist, please add a new one", Toast.LENGTH_SHORT);
                    return;
                }
                if(shippingAddresses.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "No shipping addresses exist, please add a new one", Toast.LENGTH_SHORT);
                    return;
                }
                int radioId = radioPayment.getCheckedRadioButtonId();
                if(radioId == -1){
                    Toast.makeText(getActivity().getApplicationContext(), "No payment method was selected. Please select one or make a new one.", Toast.LENGTH_SHORT);
                    return;
                }
                RadioButton selected = (RadioButton) view.findViewById(radioId);
                UserAddress selectedAddress = (UserAddress) selected.getTag();
                radioId = radioAddress.getCheckedRadioButtonId();
                if(radioId == -1){
                    Toast.makeText(getActivity().getApplicationContext(), "No shipping address was selected. Please select one or make a new one.", Toast.LENGTH_SHORT);
                    return;
                }
                selected = (RadioButton) view.findViewById(radioId);
                PaymentInformation selectedPayment = (PaymentInformation) selected.getTag();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String date = format.format(c.getTime());

                if(((RadioButton) view.findViewById(R.id.radioExpeditedShipping)).isChecked()){
                    c.add(Calendar.DATE, 2);
                    extraShipping = true;
                }
                else{
                    c.add(Calendar.DATE, 5);
                }
                String shipDate = format.format(c.getTime());
                ((MainActivity)getActivity()).createOrderFromCartItems(databaseManager.getCurrentActiveUser().getFirstName()+databaseManager.getCurrentActiveUser().getLastName(),
                        date, shipDate, OrderVariables.STATUS.ORDER_RECEIVED, selectedPayment.getCardNumber(),
                        selectedPayment.getNameOnCard(), selectedPayment.getExpirationMonth(), selectedPayment.getExpirationYear(), selectedAddress.getStreet(),
                        selectedAddress.getCountry(), selectedAddress.getState(), selectedAddress.getCity(), selectedAddress.getPostalCode(), extraShipping);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).displayFragment(R.id.action_cart);
            }
        });

        TextView newAddress = (TextView)view.findViewById(R.id.textNewAddress);
        TextView newPayment = (TextView) view.findViewById(R.id.textNewPayment);
        newAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setReturnCheckout(true);
                ((MainActivity)getActivity()).displayFragment(R.layout.fragment_address_info);
            }
        });

        newPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).setReturnCheckout(true);
                ((MainActivity)getActivity()).displayFragment(R.layout.fragment_payment_info);
            }
        });

        initListView();
        changeTotalText();

        return view;
    }

    private void setShipping() {
        for(int i = 0; i < shippingAddresses.size(); i++){
            RadioButton rButton = new RadioButton(getContext());
            rButton.setId(i);
            rButton.setText("Postal code: " + shippingAddresses.get(i).getPostalCode());
            rButton.setTag(shippingAddresses.get(i));
            radioAddress.addView(rButton);
        }
    }

    private void setPayment() {
        for(int i = 0; i < paymentMethods.size(); i++){
            RadioButton rButton = new RadioButton(getContext());
            rButton.setId(i);
            rButton.setText("Card ending with: " + paymentMethods.get(i).getCardNumber() % 10000);
            rButton.setTag(paymentMethods.get(i));
            radioPayment.addView(rButton);
        }
    }

    private void initListView(){

        customListAdapter = new CustomListAdapter(getContext());
        listView.setAdapter(customListAdapter);
    }


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
            TextView amount;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final CheckoutFragment.CustomListAdapter.Holder holder = new CheckoutFragment.CustomListAdapter.Holder();
            final View rowView;
            rowView = inflater.inflate(R.layout.custom_layout_checkout_item_listview, null);

            holder.title = (TextView) rowView.findViewById(R.id.custom_layout_checkout_item_listview_title);
            holder.price = (TextView) rowView.findViewById(R.id.custom_layout_checkout_item_listview_price);

            final Item rowItem = itemList.get(position);


            return rowView;

        }
    }

    private void changeTotalText() {
        double subtotal = 0;

        for (int i = 0; i < itemList.size(); i++) {

            subtotal += itemList.get(i).getPrice() * cartItemList.get(i).getAmount();

        }
        double tax = subtotal*0.13;
        double total = extraShipping ? subtotal+tax+25 : subtotal+tax;
        totalTextView.setText("Subtotal: " + String.format("%.2f$", subtotal)
                + "\nTax: " + String.format("%.2f$", tax) + "\nTotal: " + String.format("%.2f$", total));
    }

}
