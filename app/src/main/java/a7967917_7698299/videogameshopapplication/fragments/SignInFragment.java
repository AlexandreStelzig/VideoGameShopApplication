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
import android.widget.TextView;
import android.widget.Toast;

import a7967917_7698299.videogameshopapplication.MainActivity;
import a7967917_7698299.videogameshopapplication.R;
import a7967917_7698299.videogameshopapplication.database.DatabaseManager;
import a7967917_7698299.videogameshopapplication.model.User;

/**
 * @author Alexandre Stelzig, Mathieu Perron
 */

public class SignInFragment extends Fragment{


    private View view;
    private DatabaseManager databaseManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        setHasOptionsMenu(true);
        Button signInButton = (Button)view.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){signIn();}
        });
        TextView createAccountText = (TextView)view.findViewById(R.id.createAccountLink);
        createAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).displayFragment(R.id.nav_sign_up);
            }
        });
        TextView forgotPasswordText = (TextView)view.findViewById(R.id.forgotPasswordLink);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Sorry, this feature is not yet implemented.", Toast.LENGTH_SHORT).show();
            }
        });
        databaseManager = DatabaseManager.getInstance();
        return view;
    }

    public void signIn() {

        EditText editEmail = (EditText)getView().findViewById(R.id.editEmailSignIn);
        if(TextUtils.isEmpty(editEmail.getText())){
            editEmail.setError("E-mail field is empty!");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editEmail.getText()).matches()){
            editEmail.setError("Invalid e-mail given. Please check the format of the e-mail given. It should look like: example@example.com");
            return;
        }
        EditText editPassword = (EditText)getView().findViewById(R.id.editPasswordSignIn);
        if(TextUtils.isEmpty(editPassword.getText())){
            editPassword.setError("Password field is empty!");
            return;
        }
        String email = editEmail.getText().toString();
        String password = ((EditText)getView().findViewById(R.id.editPasswordSignIn)).getText().toString();

        User u = databaseManager.getUserByEmail(email);
        if(u.equals(null)||!u.getPassword().equals(password)){
            Toast.makeText(getContext(), "Invalid email or password.", Toast.LENGTH_SHORT);
            return;
        }
        databaseManager.setCurrentActiveUser(u.getUserId());
        ((MainActivity) getActivity()).displayFragment(R.id.nav_home);

    }





}
