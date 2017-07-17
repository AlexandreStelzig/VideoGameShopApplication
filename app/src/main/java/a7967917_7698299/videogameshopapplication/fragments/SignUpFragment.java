package a7967917_7698299.videogameshopapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
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

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class SignUpFragment extends Fragment{


    private View view;
    DatabaseManager databaseManager;

    private EditText editFirstName;
    private EditText editLastName;
    private EditText editPassword;
    private EditText editEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        setHasOptionsMenu(true);
        Button signUpButton = (Button) view.findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
        databaseManager = DatabaseManager.getInstance();


        editFirstName = (EditText)view.findViewById(R.id.editFirstNameSignUp);
        editEmail = (EditText)view.findViewById(R.id.editEmailSignUp);
        editPassword = (EditText)view.findViewById(R.id.editPasswordSignUp);
        editLastName = (EditText)view.findViewById(R.id.editLastNameSignUp);

        // fix bug where the fields doesn't reinitialize
        resetFields();

        return view;
    }

    public void signUp(){


        if(TextUtils.isEmpty(editFirstName.getText())){
            editFirstName.setError("First name is empty.");
            return;
        }

        if(TextUtils.isEmpty(editLastName.getText())){
            editLastName.setError("Last name is empty.");
            return;
        }

        if(TextUtils.isEmpty(editEmail.getText())){
            editEmail.setError("E-mail field is empty!");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText()).matches()){
            editEmail.setError("Invalid e-mail given. Please check the format of the e-mail given. It should look like: example@example.com");
            return;
        }

        if(TextUtils.isEmpty(editPassword.getText())){
            editPassword.setError("Password field is empty.");
            return;
        }

        if(databaseManager.getUserByEmail(editEmail.getText().toString()) != null){
            editEmail.setError("Email already exists.");
            return;
        }

        Toast.makeText(getContext(), "Account created!", Toast.LENGTH_SHORT).show();
        long userId = databaseManager.createUser(editEmail.getText().toString(), editPassword.getText().toString(),editFirstName.getText().toString(), editLastName.getText().toString());
        databaseManager.setCurrentActiveUser(userId);

        View current = getActivity().getCurrentFocus();
        if (current != null){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        

        ((MainActivity)getActivity()).displayFragment(R.id.nav_home);
        ((MainActivity) getActivity()).resetMainDrawerMenu();
    }

    private void resetFields() {
        editFirstName.setText("");
        editLastName.setText("");
        editEmail.setText("");
        editPassword.setText("");
    }
}
