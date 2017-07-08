package a7967917_7698299.videogameshopapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import a7967917_7698299.videogameshopapplication.R;

/**
 * @author Mathieu Perron, Alexandre Stelzig
 */

public class SignInFragment extends Fragment{


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        setHasOptionsMenu(true);

        return view;
    }

    public void signIn(View v) {
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

        //TODO: Set up validation



    }





}
