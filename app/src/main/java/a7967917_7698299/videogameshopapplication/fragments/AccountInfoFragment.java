package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.User;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class AccountInfoFragment extends Fragment {


    private View view;
    EditText editEmail;
    EditText editPassword;
    EditText editFirstName;
    EditText editLastName;

    private DatabaseManager databaseManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_account_info, container, false);
        setHasOptionsMenu(true);

        editEmail = (EditText) view.findViewById(R.id.editEmailAccount);
        editPassword = (EditText) view.findViewById(R.id.editPasswordAccount);
        editFirstName = (EditText) view.findViewById(R.id.editFirstNameAccount);
        editLastName = (EditText) view.findViewById(R.id.editLastNameAccount);
        Button save = (Button) view.findViewById(R.id.accountInfoSave);
        Button cancel = (Button) view.findViewById(R.id.accountInfoCancel);
        databaseManager = DatabaseManager.getInstance();


        final User u = databaseManager.getCurrentActiveUser();
        editEmail.setText(u.getEmail());
        editFirstName.setText(u.getFirstName());
        editLastName.setText(u.getLastName());
        editPassword.setText(u.getPassword());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editEmail.getText())) {
                    editEmail.setError("E-mail field is empty!");
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText()).matches()) {
                    editEmail.setError("Invalid e-mail given. Please check the format of the e-mail given. It should look like: example@example.com");
                    return;
                }
                String email = editEmail.getText().toString();
                if (TextUtils.isEmpty(editPassword.getText())) {
                    editPassword.setError("Password field is empty.");
                    return;
                }
                String password = editPassword.getText().toString();
                if (TextUtils.isEmpty(editFirstName.getText())) {
                    editFirstName.setError("First name is empty.");
                    return;
                }
                String firstName = editFirstName.getText().toString();
                if (TextUtils.isEmpty(editLastName.getText())) {
                    editFirstName.setError("Last name is empty.");
                    return;
                }
                String lastName = editLastName.getText().toString();

                Toast.makeText(getContext(), "Account information updated!", Toast.LENGTH_SHORT).show();
                databaseManager.updateCurrentUser(email, password, firstName, lastName);
                ((MainActivity) getActivity()).displayFragment(R.id.nav_account);

                View current = getActivity().getCurrentFocus();
                if (current != null){
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).displayFragment(R.id.nav_account);
                editEmail.setText(u.getEmail());
                editFirstName.setText(u.getFirstName());
                editLastName.setText(u.getLastName());
                editPassword.setText(u.getPassword());

                View current = getActivity().getCurrentFocus();
                if (current != null){
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        final User u = databaseManager.getCurrentActiveUser();
        editEmail.setText(u.getEmail());
        editFirstName.setText(u.getFirstName());
        editLastName.setText(u.getLastName());
        editPassword.setText(u.getPassword());
    }

}
