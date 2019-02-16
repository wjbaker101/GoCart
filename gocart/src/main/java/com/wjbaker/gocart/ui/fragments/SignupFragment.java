package com.wjbaker.gocart.ui.fragments;

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

public class SignupFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FirebaseAuth firebaseAuth;

    public SignupFragment() {}

    public static SignupFragment newInstance(int sectionNumber)
    {
        SignupFragment fragment = new SignupFragment();
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
                R.layout.fragment_signup, container, false);

        EditText usernameTextbox = rootView.findViewById(R.id.signup_username);
        EditText password1Textbox = rootView.findViewById(R.id.signup_password1);
        EditText password2Textbox = rootView.findViewById(R.id.signup_password2);
        Button signupButton = rootView.findViewById(R.id.signup_confirm);

        signupButton.setOnClickListener(view ->
        {
            String username = usernameTextbox.getText().toString();
            String password1 = password1Textbox.getText().toString();
            String password2 = password2Textbox.getText().toString();

            if (TextUtils.isEmpty(username))
            {
                return;
            }

            if (TextUtils.isEmpty(password1))
            {
                return;
            }

            if (TextUtils.isEmpty(password2))
            {
                return;
            }

            firebaseAuth.createUserWithEmailAndPassword(username, password1)
                    .addOnCompleteListener(getActivity(), task ->
                    {
                        Toast.makeText(
                                getActivity(),
                                "createUserWithEmail:onComplete:" + task.isSuccessful(),
                                Toast.LENGTH_SHORT)
                                .show();

                        if (!task.isSuccessful())
                        {
                            Toast.makeText(
                                    getActivity(),
                                    "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
        });

        return rootView;
    }
}
