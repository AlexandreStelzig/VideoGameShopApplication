package a7967917_7698299.videogameshopapplication.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import a7967917_7698299.videogameshopapplication.R;

/**
 * Created by alex on 2017-06-24.
 */

public class SettingsFragment extends Fragment {


    private View view;
    private Spinner countrySpinner;
    private Spinner languageSpinner;
    private TextView nbResultsTextView;

    private int nbResultsPerPage = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);

        countrySpinner = (Spinner) view.findViewById(R.id.fragment_settings_country_spinner);
        languageSpinner = (Spinner) view.findViewById(R.id.fragment_settings_country_language_spinner);
        nbResultsTextView = (TextView) view.findViewById(R.id.fragment_settings_country_nbResults_textview);


        initSpinners();

        return view;
    }

    private void initSpinners() {

        String[] countries = new String[]{"Canada", "USA"};
        ArrayAdapter countryArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countries);
        countrySpinner.setAdapter(countryArrayAdapter);

        String[] languages = new String[]{"Français", "English"};
        ArrayAdapter languageArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, languages);
        languageSpinner.setAdapter(languageArrayAdapter);
        languageSpinner.setSelection(1);

        nbResultsTextView.setText(nbResultsPerPage + "");
        nbResultsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQuickToggleDialog();
            }
        });


    }


    public void showQuickToggleDialog() {

        View viewPicker = View.inflate(getContext(), R.layout.custom_number_picker_dialog, null);
        AlertDialog.Builder db = new AlertDialog.Builder(getContext());
        db.setView(viewPicker);
        db.setTitle("Quick Toggle Review");

        final NumberPicker np = (NumberPicker) viewPicker.findViewById(R.id.numberPicker1);

        np.setMaxValue(100); // max value 100
        np.setMinValue(5);   // min value 0
        np.setWrapSelectorWheel(false);

        np.setValue(nbResultsPerPage);

        db.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });

        db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                nbResultsPerPage = (np.getValue());
                nbResultsTextView.setText(nbResultsPerPage + "");
            }
        });
        db.show();


    }
}
