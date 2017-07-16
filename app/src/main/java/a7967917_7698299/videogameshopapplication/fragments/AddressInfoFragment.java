package a7967917_7698299.videogameshopapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.UserAddress;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class AddressInfoFragment extends Fragment {


    private View view;

    private EditText editStreet;
    private EditText editPostal;
    private EditText editCity;
    private Spinner provinceSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_address_info, container, false);
        setHasOptionsMenu(true);
        final boolean returnCheckout = ((MainActivity)getActivity()).isReturnCheckout();
        ((MainActivity)getActivity()).setReturnCheckout(false);
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        editStreet = (EditText) view.findViewById(R.id.editStreet);
        editPostal = (EditText) view.findViewById(R.id.editPostal);
        editCity = (EditText) view.findViewById(R.id.editCity);
        provinceSpinner = (Spinner) view.findViewById(R.id.provinceSpinner);
        final Button save = (Button) view.findViewById(R.id.addressInfoSave);
        final Button cancel = (Button) view.findViewById(R.id.addressInfoCancel);
        final UserAddress editingAddress = ((MainActivity) getActivity()).getEditingAddress();

        if (editingAddress != null) {
            editStreet.setText(editingAddress.getStreet());
            editPostal.setText(editingAddress.getPostalCode());
            editCity.setText(editingAddress.getCity());
            provinceSpinner.setSelection(((ArrayAdapter) provinceSpinner.getAdapter()).getPosition(editingAddress.getState()));

        } else {
            editStreet.getText().clear();
            editPostal.getText().clear();
            editCity.getText().clear();
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String street = editStreet.getText().toString();
                if (street.equals("")) {
                    editStreet.setError("No street name and number was given.");
                    return;
                }
                String postal = editPostal.getText().toString();
                postal = postal.toUpperCase();
                if (postal.length() != 6) {
                    editPostal.setError("The postal code must be 6 characters long.");
                    return;
                }
                Pattern postalCodePattern = Pattern.compile("[A-Z][0-9][A-Z][0-9][A-Z][0-9]");
                Matcher postalCodeMatcher = postalCodePattern.matcher(postal);
                if (!postalCodeMatcher.matches()) {
                    editPostal.setError("A postal code must be 6 characters long and alternate between letters and numbers. Ex: K1K1K1");
                    return;
                }
                String city = editCity.getText().toString();
                if (city.equals("")) {
                    editCity.setError("No city name was given.");
                    return;
                }
                String province = provinceSpinner.getSelectedItem().toString();
                if (editingAddress != null) {
                    databaseManager.updateAddress(editingAddress.getAddressId(), street, "Canada", province, city, postal,
                            databaseManager.getCurrentActiveUser().getUserId());
                    editStreet.setText("");
                    editPostal.setText("");
                    editCity.setText("");
                    Toast.makeText(getContext(), "Address updated.", Toast.LENGTH_SHORT);
                    if(returnCheckout){
                        ((MainActivity)getActivity()).displayFragment(R.layout.fragment_checkout);
                    } else {
                        ((MainActivity) getActivity()).displayFragment(R.layout.fragment_payment_list);
                    }
                } else {
                    databaseManager.createAddress(street, "Canada", province, city, postal, databaseManager.getCurrentActiveUser().getUserId());
                    editStreet.setText("");
                    editPostal.setText("");
                    editCity.setText("");
                    Toast.makeText(getContext(), "New address added to account.", Toast.LENGTH_SHORT);
                    if(returnCheckout){
                        ((MainActivity)getActivity()).displayFragment(R.layout.fragment_checkout);
                    } else {
                        ((MainActivity) getActivity()).displayFragment(R.layout.fragment_payment_list);
                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(returnCheckout){
                    ((MainActivity)getActivity()).displayFragment(R.layout.fragment_checkout);
                } else {
                    ((MainActivity) getActivity()).displayFragment(R.layout.fragment_payment_list);
                }
                editStreet.setText("");
                editPostal.setText("");
                editCity.setText("");
            }
        });

        return view;
    }

    // fix bug where data doesn't reset correctly
    @Override
    public void onResume() {
        super.onResume();
        final UserAddress editingAddress = ((MainActivity) getActivity()).getEditingAddress();
        if (editingAddress != null) {
            editStreet.setText(editingAddress.getStreet());
            editPostal.setText(editingAddress.getPostalCode());
            editCity.setText(editingAddress.getCity());
            provinceSpinner.setSelection(((ArrayAdapter) provinceSpinner.getAdapter()).getPosition(editingAddress.getState()));
            ((MainActivity) getActivity()).setEditingAddress(null);
        } else {
            editStreet.getText().clear();
            editPostal.getText().clear();
            editCity.getText().clear();
        }

    }
}
