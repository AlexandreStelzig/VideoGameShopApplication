package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.UserAddress;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class AddressListFragment extends Fragment{


    DatabaseManager databaseManager;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_address_list, container, false);
        setHasOptionsMenu(true);
        ProgressBar progress = (ProgressBar) view.findViewById(R.id.addressProgressBar);
        progress.setVisibility(View.VISIBLE);
        ListView listView = (ListView) view.findViewById(R.id.addressListView);
        listView.setVisibility(View.GONE);
        LinearLayout noAddress = (LinearLayout) view.findViewById(R.id.noAddressesLayout);
        noAddress.setVisibility(View.GONE);
        databaseManager = DatabaseManager.getInstance();
        List<UserAddress> addresses = new ArrayList<>();
        addresses = databaseManager.getAllAddressesFromActiveUser();
        if(addresses.isEmpty()){
            progress.setVisibility(View.GONE);
            noAddress.setVisibility(View.VISIBLE);
        }
        else{
            progress.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            CustomListAdapter customListAdapter = new CustomListAdapter(getContext(), addresses);
        }

        android.support.design.widget.FloatingActionButton addAddress = (android.support.design.widget.FloatingActionButton)view.findViewById(R.id.addAddressButton);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).displayFragment(R.layout.fragment_address_info);
            }
        });

        return view;
    }


    private class CustomListAdapter extends BaseAdapter {
        private Context context;
        private List<UserAddress> allAddresses;

        private LayoutInflater inflater;

        private CustomListAdapter(Context context, List<UserAddress> addresses){

            super();
            this.context = context;
            this.allAddresses = addresses;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount(){return allAddresses.size();}

        @Override
        public UserAddress getItem(int position){return allAddresses.get(position);}

        @Override
        public long getItemId(int position){return position;}


        public class Holder {
            TextView street;
            TextView postalCode;
            TextView city;
            TextView province;
            TextView country;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final CustomListAdapter.Holder holder = new CustomListAdapter.Holder();
            final View rowView;
            rowView = inflater.inflate(R.layout.custom_layout_address_listview, null);

            holder.city = (TextView) rowView.findViewById(R.id.addressCity);
            holder.country = (TextView) rowView.findViewById(R.id.addressCountry);
            holder.postalCode = (TextView) rowView.findViewById(R.id.addressPostal);
            holder.province = (TextView) rowView.findViewById(R.id.addressProvince);
            holder.street = (TextView) rowView.findViewById(R.id.addressStreet);

            final UserAddress userAddress = allAddresses.get(position);
            ((Button)rowView.findViewById(R.id.buttonEditAddress)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).setEditingAddress(userAddress);
                    ((MainActivity)getActivity()).displayFragment(R.layout.fragment_address_info);
                }
            });

            return rowView;

        }


    }
}
