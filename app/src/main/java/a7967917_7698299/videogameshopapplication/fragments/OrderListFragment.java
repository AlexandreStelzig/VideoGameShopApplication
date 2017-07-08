package a7967917_7698299.videogameshopapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.variables.OrderVariables;

/**
 * Created by alex on 2017-06-24.
 */

public class OrderListFragment extends Fragment{


    private View view;

    private DatabaseManager databaseManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_list, container, false);
        setHasOptionsMenu(true);

        databaseManager = DatabaseManager.getInstance();




        return view;
    }
}
