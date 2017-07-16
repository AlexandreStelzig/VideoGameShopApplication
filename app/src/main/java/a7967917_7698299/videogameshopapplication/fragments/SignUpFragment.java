package a7967917_7698299.videogameshopapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return view;
    }

    public void signUp(){
        View v = getView();
        EditText editFirstName = (EditText)v.findViewById(R.id.editFirstNameSignUp);
        if(TextUtils.isEmpty(editFirstName.getText())){
            editFirstName.setError("First name is empty.");
            return;
        }
        EditText editLastName = (EditText)v.findViewById(R.id.editLastNameSignUp);
        if(TextUtils.isEmpty(editLastName.getText())){
            editFirstName.setError("Last name is empty.");
            return;
        }
        EditText editEmail = (EditText)v.findViewById(R.id.editEmailSignUp);
        if(TextUtils.isEmpty(editEmail.getText())){
            editEmail.setError("E-mail field is empty!");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText()).matches()){
            editEmail.setError("Invalid e-mail given. Please check the format of the e-mail given. It should look like: example@example.com");
            return;
        }
        EditText editPassword = (EditText)v.findViewById(R.id.editPasswordSignUp);
        if(TextUtils.isEmpty(editPassword.getText())){
            editPassword.setError("Password field is empty.");
            return;
        }

        Toast.makeText(getContext(), "Account created!", Toast.LENGTH_SHORT);
        long userId = databaseManager.createUser(editEmail.getText().toString(), editPassword.getText().toString(),editFirstName.getText().toString(), editLastName.getText().toString());
        databaseManager.setCurrentActiveUser(userId);
        ((MainActivity)getActivity()).displayFragment(R.id.nav_home);
        ((MainActivity) getActivity()).resetMainDrawerMenu();
    }
}
