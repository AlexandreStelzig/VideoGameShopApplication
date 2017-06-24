package a7967917_7698299.videogameshopapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a7967917_7698299.videogameshopapplication.R;

/**
 * Created by alex on 2017-06-24.
 */

public class CartFragment extends Fragment{


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);
        setHasOptionsMenu(true);

        return view;
    }
}
