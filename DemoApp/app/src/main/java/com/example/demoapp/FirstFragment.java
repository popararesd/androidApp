package com.example.demoapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.demoapp.models.Account;
import com.example.demoapp.models.Professor;
import com.example.demoapp.models.Student;
import com.example.demoapp.models.User;
import com.example.demoapp.services.AccountCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {
    private EditText emailBox;
    private EditText passBox;
    private User user;
    private UserSave userSave;

    public interface UserSave {
        void onLoginRequest(User user);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailBox = (EditText) view.findViewById(R.id.email_box);
        passBox = (EditText) view.findViewById(R.id.passwordBox);

        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String email = emailBox.getText().toString().trim();
                String pass = passBox.getText().toString().trim();
                Call<Account> callAccount = AccountCall.getInstance().checkLogin(email, pass);
                callAccount.enqueue(new Callback<Account>() {

                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        Account account = response.body();
                        user = account.getUser();
                        userSave.onLoginRequest(user);
                        if (user.getType().equals("p")) {
                            FirstFragmentDirections.ActionFirstFragmentToProfFragment action = FirstFragmentDirections.actionFirstFragmentToProfFragment();
                            action.setUserID(user.getId());
                            NavHostFragment.findNavController(FirstFragment.this)
                                    .navigate(action);
                        } else {
                            if (user.getType().equals("s")) {
                                FirstFragmentDirections.ActionFirstFragmentToStudFragment action = FirstFragmentDirections.actionFirstFragmentToStudFragment();
                                action.setStudId(user.getId());
                                NavHostFragment.findNavController(FirstFragment.this)
                                        .navigate(action);
                            }
                            else
                                NavHostFragment.findNavController(FirstFragment.this)
                                        .navigate(R.id.action_firstFragment_to_adminFragment);
                        }

                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        System.out.println("\n\nAccount get error: " + t.getMessage() + "\n\n");
                        Toast toast = Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_LONG);
                        toast.show();
                        call.cancel();
                    }
                });

            }
        });
        view.findViewById(R.id.sign_up_from_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_firstFragment_to_signUpFragment2);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UserSave)
            userSave = (UserSave) context;
        else
            throw new RuntimeException(context.toString());
    }

}
