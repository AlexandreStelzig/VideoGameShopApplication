package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.PaymentInformation;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class PaymentListFragment extends Fragment{


    private View view;
    DatabaseManager databaseManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_payment_list, container, false);
        setHasOptionsMenu(true);

        ListView listView = (ListView) view.findViewById(R.id.paymentListView);
        listView.setVisibility(View.GONE);
        LinearLayout noPayment = (LinearLayout) view.findViewById(R.id.noPaymentLayout);
        noPayment.setVisibility(View.GONE);
        databaseManager = DatabaseManager.getInstance();
        List<PaymentInformation> paymentInformations = new ArrayList<>();
        paymentInformations = databaseManager.getAllPaymentMethodsFromActiveUser();
        if(paymentInformations.isEmpty()){
            noPayment.setVisibility(View.VISIBLE);
        }
        else {
            listView.setVisibility(View.VISIBLE);
            CustomListAdapter customListAdapter = new CustomListAdapter(getContext(), paymentInformations);
        }
        return view;
    }

    private class CustomListAdapter extends BaseAdapter {
        private Context context;
        private List<PaymentInformation> allPayment;

        private LayoutInflater inflater;

        private CustomListAdapter(Context context, List<PaymentInformation> paymentInfos){

            super();
            this.context = context;
            this.allPayment = paymentInfos;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount(){return allPayment.size();}

        @Override
        public PaymentInformation getItem(int position){return allPayment.get(position);}

        @Override
        public long getItemId(int position){return position;}


        public class Holder {
            TextView name;
            TextView number;
            TextView expiry;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            final CustomListAdapter.Holder holder = new CustomListAdapter.Holder();
            final View rowView;
            rowView = inflater.inflate(R.layout.custom_layout_payment_listview, null);

            holder.name = (TextView) rowView.findViewById(R.id.paymentCardName);
            holder.number = (TextView) rowView.findViewById(R.id.paymentCardNumber);
            holder.expiry = (TextView) rowView.findViewById(R.id.paymentExpiry);

            final PaymentInformation paymentInformation = allPayment.get(position);
            ((Button)rowView.findViewById(R.id.buttonEditPayment)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).setEditingPayment(paymentInformation);
                    ((MainActivity)getActivity()).displayFragment(R.layout.fragment_payment_info);
                }
            });

            return rowView;

        }


    }
}
