package com.wjbaker.gocart.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.ui.activities.CredentialsActivity;
import com.wjbaker.gocart.ui.activities.MainActivity;

public class LoginFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FirebaseAuth firebaseAuth;

    public LoginFragment() {}

    public static LoginFragment newInstance(int sectionNumber)
    {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState)
    {
        this.firebaseAuth = FirebaseAuth.getInstance();

        View rootView = inflater.inflate(
                R.layout.fragment_login, container, false);

        EditText usernameTextbox = rootView.findViewById(R.id.login_username);
        EditText passwordTextbox = rootView.findViewById(R.id.login_password);
        Button confirmButton = rootView.findViewById(R.id.login_confirm);

        confirmButton.setOnClickListener(view ->
        {
            String username = usernameTextbox.getText().toString();
            String password = passwordTextbox.getText().toString();

            if (TextUtils.isEmpty(username))
            {
//                return;
            }

            if (TextUtils.isEmpty(password))
            {
//                return;
            }

//            this.firebaseAuth.signInWithEmailAndPassword(username, password)
//                    .addOnCompleteListener(this.getActivity(), task ->
//                    {
//                        if (!task.isSuccessful())
//                        {
//                            // Error
//                        }
//                        else
//                        {
                            Intent intent = new Intent(this.getContext(), MainActivity.class);
                            this.startActivity(intent);
//                        }
//                    });
        });

        return rootView;
    }
}
