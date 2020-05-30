package com.example.demoapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.demoapp.models.Course;
import com.example.demoapp.models.Student;
import com.example.demoapp.models.User;
import com.example.demoapp.services.CourseCall;
import com.example.demoapp.services.UserCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudFragment extends Fragment {

    private static Long user = null;
    private static String data = null;
    private TextView studInfo;
    private TextView coursesInfo;
    private EditText codeBox;
    private Button markAttBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_student, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(user == null) {
            StudFragmentArgs args = StudFragmentArgs.fromBundle(getArguments());
            user = args.getStudId();
            System.out.println("STUDENT ID IS "+user.toString());
        }
        if(data == null)
            data = "";
        studInfo = (TextView) view.findViewById(R.id.student_info);
        coursesInfo = (TextView)view.findViewById(R.id.attended_courses);
        coursesInfo.setText("");
        codeBox = (EditText)view.findViewById(R.id.code_box);
        markAttBtn = (Button)view.findViewById(R.id.mark_attendance_btn);
        markAttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeBox.getText().toString().trim();
                markAttendance(code);
            }
        });
        updateData();
    }

    private void markAttendance(String code){
        Call<Course> markCall = CourseCall.getInstance().markAttendance(code,user.toString());
        markCall.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                data += response.body().getName() + "\n";
                coursesInfo.setText(data);
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                System.out.println("Error whil att : " + t.getMessage() );
            }
        });
    }

    private void updateData(){
        Call<User> userCall = UserCall.getInstance().getUser(user.toString());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User stud =  response.body();
                String info = "";
                info += stud.getFirstName() + " " + stud.getLastName() + "\n";
                studInfo.setText(info);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
