package com.example.demoapp;

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
import com.example.demoapp.models.User;
import com.example.demoapp.services.AccountCall;
import com.example.demoapp.services.UserCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminFragment extends Fragment {

    private EditText firstNameBox;
    private EditText lastNameBox;
    private EditText emailBox;
    private EditText phoneBox;
    private EditText departmetBox;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_admin, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.firstNameBox = (EditText) view.findViewById(R.id.admin_firstname_box);
        this.lastNameBox = (EditText) view.findViewById(R.id.admin_lastname_box);
        this.emailBox = (EditText) view.findViewById(R.id.admin_email_box);
        this.phoneBox = (EditText) view.findViewById(R.id.admin_phone_box);
        this.departmetBox = (EditText) view.findViewById(R.id.admin_department_box);

        view.findViewById(R.id.admin_create_account_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameBox.getText().toString().trim();
                String lastName = lastNameBox.getText().toString().trim();
                String email = emailBox.getText().toString().trim();
                String phone = phoneBox.getText().toString().trim();
                String department = departmetBox.getText().toString().trim();
                createProfAccount(firstName,lastName,email,phone,department);
            }
        });
    }

    private void createProfAccount(String firstName,String lastName,String email,String phone,String department){
        Call<User> call = UserCall.getInstance().addUser("p",firstName,lastName,email,phone,"-","-",department);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                Call<Account> callAccount = AccountCall.getInstance().createAccount(user.getId().toString(),"admin","admin");
                callAccount.enqueue(new Callback<Account>(){
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {

                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        System.out.println("\n\nAccount insert error: "+t.getMessage()+"\n\n");
                        Toast toast = Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG);
                        toast.show();
                        call.cancel();
                    }
                });
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
