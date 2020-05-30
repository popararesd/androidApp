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

import com.example.demoapp.models.Subject;
import com.example.demoapp.services.SubjectCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSubject extends Fragment {
    private Long profId;
    private EditText nameBox;
    private EditText creditsBox;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_subject, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameBox = view.findViewById(R.id.subject_name_box);
        creditsBox = view.findViewById(R.id.credit_number_box);
        AddSubjectArgs args = AddSubjectArgs.fromBundle(getArguments());
        profId = args.getProfId();
        view.findViewById(R.id.add_subject_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewSubject(nameBox.getText().toString().trim(),creditsBox.getText().toString().trim());
            }
        });
    }
    void addNewSubject(String name,String credits){
        Call<Subject> callSubject = SubjectCall.getInstance().addSubject(name,credits,profId.toString());
        callSubject.enqueue(new Callback<Subject>(){

            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                AddSubjectDirections.ActionAddSubjectToProfFragment action = AddSubjectDirections.actionAddSubjectToProfFragment();
                NavHostFragment.findNavController(AddSubject.this)
                        .navigate(action);
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {
                System.out.println("\n\nAccount insert error: "+t.getMessage()+"\n\n");
                Toast toast = Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG);
                toast.show();
                call.cancel();
            }
        });
    }
}
