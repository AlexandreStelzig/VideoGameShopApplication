package a7967917_7698299.videogameshopapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.variables.OrderVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class HelpFragment extends Fragment {


    private View view;
    private ListView listView;
    private ScrollView scrollView;

    private TextView aboutTextView;
    private TextView shoppingTextView;
    private TextView ordersTextView;
    private TextView accountTextView;

    private DatabaseManager databaseManager;

    private View viewToScrollTo = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_help, container, false);
        setHasOptionsMenu(true);


        databaseManager = DatabaseManager.getInstance();

        // used for testing - create an order. Also uncomment the button in the help fragment
//        ((Button) view.findViewById(R.id.test)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity)getActivity()).createOrderFromCartItems("test","test","test", OrderVariables.STATUS.DELIVERED, 1234, "test",  2,2,"test","test","test","test","test", true );
//            }
//        });


        listView = (ListView) view.findViewById(R.id.fragment_help_listview);
        scrollView = (ScrollView) view.findViewById(R.id.fragment_help_scrollview);

        aboutTextView = (TextView) view.findViewById(R.id.fragment_help_about_textview);
        shoppingTextView = (TextView) view.findViewById(R.id.fragment_help_shpping_textview);
        ordersTextView = (TextView) view.findViewById(R.id.fragment_help_orders_textview);
        accountTextView = (TextView) view.findViewById(R.id.fragment_help_account_textview);

        final List<String> options = new ArrayList<>();
        options.add(getResources().getString(R.string.about));
        options.add(getResources().getString(R.string.shopping_and_order));
        options.add(getResources().getString(R.string.viewing_orders));
        options.add(getResources().getString(R.string.account_and_settings));

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, options);

        listView.setAdapter(arrayAdapter);

        Helper.setListViewHeightBasedOnChildren(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (position == options.indexOf(getResources().getString(R.string.about))) {
                    viewToScrollTo = aboutTextView;
                } else if (position == options.indexOf(getResources().getString(R.string.shopping_and_order))) {
                    viewToScrollTo = shoppingTextView;
                } else if (position == options.indexOf(getResources().getString(R.string.viewing_orders))) {
                    viewToScrollTo = ordersTextView;
                } else {
                    viewToScrollTo = accountTextView;
                }

                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, viewToScrollTo.getTop());
                    }
                });

            }
        });


        return view;
    }
}
