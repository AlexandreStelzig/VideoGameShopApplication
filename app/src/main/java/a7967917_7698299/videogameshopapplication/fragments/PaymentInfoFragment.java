package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.PaymentInformation;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class PaymentInfoFragment extends Fragment {


    private View view;

    private EditText editName;
    private EditText editCard;
    private EditText editExpiryMonth;
    private EditText editExpiryYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_payment_info, container, false);
        setHasOptionsMenu(true);
        final DatabaseManager databaseManager = DatabaseManager.getInstance();
        editName = (EditText) view.findViewById(R.id.editPaymentName);
        editCard = (EditText) view.findViewById(R.id.editCardNumber);
        editExpiryMonth = (EditText) view.findViewById(R.id.editExpiryMonth);
        editExpiryYear = (EditText) view.findViewById(R.id.editExpiryYear);
        Button save = (Button) view.findViewById(R.id.paymentInfoSave);
        Button cancel = (Button) view.findViewById(R.id.paymentInfoCancel);
        final PaymentInformation editingPayment = ((MainActivity) getActivity()).getEditingPayment();
        if (editingPayment != null) {
            editName.setText(editingPayment.getNameOnCard());
            editCard.setText(Long.toString(editingPayment.getCardNumber()));


            String stringExpiryMonth = Integer.toString(editingPayment.getExpirationMonth());
            if (stringExpiryMonth.length() == 1)
                stringExpiryMonth = "0" + stringExpiryMonth;

            String stringExpiryYear = Integer.toString(editingPayment.getExpirationYear());
            if (stringExpiryYear.length() == 1)
                stringExpiryYear = "0" + stringExpiryYear;

            editExpiryMonth.setText(stringExpiryMonth);
            editExpiryYear.setText(stringExpiryYear);

        } else {
            editName.setText("");
            editCard.setText("");
            editExpiryMonth.setText("");
            editExpiryYear.setText("");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                if (name.equals("")) {
                    editName.setError("Name on card is empty.");
                    return;
                }
                String number = editCard.getText().toString();
                if (number.equals("")) {
                    editCard.setError("Card number is empty.");
                    return;
                }
                if (number.length() < 16) {
                    editCard.setError("Card number must be 16 digits long.");
                    return;
                }
                String expiryMonth = editExpiryMonth.getText().toString();
                if (expiryMonth.length() < 2) {
                    editExpiryMonth.setError("Expiry month needs to be 2 digits long.");
                    return;
                }
                if (Integer.parseInt(expiryMonth) < 1) {
                    editExpiryMonth.setError("Expiry month cannot be lower than 01.");
                    return;
                }
                if (Integer.parseInt(expiryMonth) > 12) {
                    editExpiryMonth.setError("Expiry month cannot be higher than 12.");
                    return;
                }
                String expiryYear = editExpiryYear.getText().toString();
                if (expiryYear.length() < 2) {
                    editExpiryYear.setError("Expiry year needs to be 2 digits long.");
                    return;
                }
//                  Maybe validation for year??
//                if(Integer.parseInt(expiryYear) < 17){
//                    editExpiryYear.setError("Expiry year needs to be higher than 17");
//                    return;
//                }
                if (editingPayment != null) {
                    databaseManager.updatePaymentInformation(editingPayment.getPaymentId(), Long.parseLong(number), name, Integer.parseInt(expiryMonth),
                            Integer.parseInt(expiryYear), databaseManager.getCurrentActiveUser().getUserId());
                    Toast.makeText(getContext(), "Payment method updated.", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).displayFragment(R.layout.fragment_payment_list);
                } else {
                    databaseManager.createPaymentInformation(Long.parseLong(number), name, Integer.parseInt(expiryMonth),
                            Integer.parseInt(expiryYear), databaseManager.getCurrentActiveUser().getUserId());
                    Toast.makeText(getContext(), "New payment method added to account.", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).displayFragment(R.layout.fragment_payment_list);
                }

                View current = getActivity().getCurrentFocus();
                if (current != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).displayFragment(R.layout.fragment_payment_list);
                View current = getActivity().getCurrentFocus();
                if (current != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        final PaymentInformation editingPayment = ((MainActivity) getActivity()).getEditingPayment();
        if (editingPayment != null) {
            editName.setText(editingPayment.getNameOnCard());
            editCard.setText(Long.toString(editingPayment.getCardNumber()));

            String stringExpiryMonth = Integer.toString(editingPayment.getExpirationMonth());
            if (stringExpiryMonth.length() == 1)
                stringExpiryMonth = "0" + stringExpiryMonth;

            String stringExpiryYear = Integer.toString(editingPayment.getExpirationYear());
            if (stringExpiryYear.length() == 1)
                stringExpiryYear = "0" + stringExpiryYear;

            editExpiryMonth.setText(stringExpiryMonth);
            editExpiryYear.setText(stringExpiryYear);
            ((MainActivity) getActivity()).setEditingPayment(null);
        } else {
            editName.setText("");
            editCard.setText("");
            editExpiryMonth.setText("");
            editExpiryYear.setText("");
        }
    }
}
