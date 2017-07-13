package a7967917_7698299.videogameshopapplication.fragments;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.User;

/**
 * Created by alex on 2017-06-24.
 */

public class AccountFragment extends Fragment {


    private View view;

    private LinearLayout orderLayout;
    private LinearLayout accountLayout;
    private LinearLayout paymentLayout;
    private LinearLayout addressLayout;

    private TextView customerNameTextView;

    private DatabaseManager databaseManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);
        databaseManager = DatabaseManager.getInstance();

        // init components
        orderLayout = (LinearLayout) view.findViewById(R.id.fragment_account_orders);
        accountLayout = (LinearLayout) view.findViewById(R.id.fragment_account_info);
        paymentLayout = (LinearLayout) view.findViewById(R.id.fragment_account_payments);
        addressLayout = (LinearLayout) view.findViewById(R.id.fragment_account_address);
        customerNameTextView = (TextView) view.findViewById(R.id.fragment_account_customer_name);

        setLayoutOnClick();


        User user = databaseManager.getCurrentActiveUser();
        customerNameTextView.setText("Welcome, " + user.getFirstName() + " " + user.getLastName());

        
        return view;
    }

    private void setLayoutOnClick() {


        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayFragment(R.id.nav_orders);
            }
        });

        accountLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayFragment(R.layout.fragment_account_info);
            }
        });

        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayFragment(R.layout.fragment_payment_list);
            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayFragment(R.layout.fragment_address_list);
            }
        });


    }


}
