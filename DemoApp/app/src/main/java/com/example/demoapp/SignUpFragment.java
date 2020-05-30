package com.example.demoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.demoapp.models.Account;
import com.example.demoapp.models.User;
import com.example.demoapp.services.AccountCall;
import com.example.demoapp.services.UserCall;
import com.example.demoapp.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpFragment extends Fragment {
    private Button signUpBtn;
    private EditText firstNameBox;
    private EditText lastNameBox;
    private EditText emailBox;
    private EditText phoneBox;
    private EditText identificationBox;
    private EditText groupBox;
    private EditText passBox;
    private EditText passConfirmBox;
    private User user;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_user, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = new User();

        firstNameBox = (EditText)view.findViewById(R.id.first_name_box);
        lastNameBox = (EditText)view.findViewById(R.id.last_name_box);
        emailBox = (EditText)view.findViewById(R.id.email_box);
        phoneBox = (EditText)view.findViewById(R.id.phone_box);
        identificationBox = (EditText)view.findViewById(R.id.identif_box);
        groupBox = (EditText)view.findViewById(R.id.group_box);
        signUpBtn = (Button)view.findViewById(R.id.sign_up_btn);
        passBox = (EditText)view.findViewById(R.id.pass1_box);
        passConfirmBox = (EditText)view.findViewById(R.id.pass2_box);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName = firstNameBox.getText().toString().trim();
                String lastName = lastNameBox.getText().toString().trim();
                String email = emailBox.getText().toString().trim();
                String phone = phoneBox.getText().toString().trim();
                String matricol = identificationBox.getText().toString().trim();
                String group = groupBox.getText().toString().trim();

                Call<User> call = UserCall.getInstance().addUser("s",firstName,lastName,email,phone,matricol,group,"-");
                call.enqueue(new Callback<User>(){

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();
                        Call<Account> callAccount = AccountCall.getInstance().createAccount(user.getId().toString(),passBox.getText().toString().trim(),passConfirmBox.getText().toString().trim());
                        callAccount.enqueue(new Callback<Account>(){

                            @Override
                            public void onResponse(Call<Account> call, Response<Account> response) {
                                NavHostFragment.findNavController(SignUpFragment.this)
                                        .navigate(R.id.action_signUpFragment2_to_firstFragment);
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
                        System.out.println("\n\nUser insert error: "+t.getMessage()+"\n\n");
                        Toast toast = Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG);
                        toast.show();
                        call.cancel();
                    }
                });

            }
        });
    }
}
