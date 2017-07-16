package a7967917_7698299.videogameshopapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class AccountInfoFragment extends Fragment {


    private View view;

    private LinearLayout orderLayout;
    private LinearLayout accountLayout;
    private LinearLayout paymentLayout;
    private LinearLayout addressLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_info, container, false);
        setHasOptionsMenu(true);

        
        
        return view;
    }




}
