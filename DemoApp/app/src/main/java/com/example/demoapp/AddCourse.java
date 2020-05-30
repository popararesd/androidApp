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

import com.example.demoapp.models.AttendaceCode;
import com.example.demoapp.models.Course;
import com.example.demoapp.models.Subject;
import com.example.demoapp.services.AttendaceCodeCall;
import com.example.demoapp.services.CourseCall;
import com.example.demoapp.services.SubjectCall;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddCourse extends Fragment {

    private Long subjectId;
    private EditText nameBox;
    private EditText dayBox;
    private EditText monthBox;
    private EditText yearBox;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_course, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        nameBox = view.findViewById(R.id.course_name_box);
        dayBox = view.findViewById(R.id.course_day_box);
        monthBox = view.findViewById(R.id.month_course_box);
        yearBox = view.findViewById(R.id.year_course_box);

        AddCourseArgs args = AddCourseArgs.fromBundle(getArguments());
        subjectId = args.getSubjectId();
        view.findViewById(R.id.add_course_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewCourse(nameBox.getText().toString().trim(),dayBox.getText().toString().trim(),monthBox.getText().toString().trim(),yearBox.getText().toString().trim());
            }
        });
    }

    private void addNewCourse(String name,String day,String month,String year){
        Call<Course> courseCall = CourseCall.getInstance().addCourse(name,day,month,year);
        courseCall.enqueue(new Callback<Course>(){

            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                Course course = response.body();
                Call<Subject> callSubject = SubjectCall.getInstance().addCourse(subjectId.toString(),course.getCourseId().toString());
                callSubject.enqueue(new Callback<Subject>(){

                    @Override
                    public void onResponse(Call<Subject> call, Response<Subject> response) {
                        AddCourseDirections.ActionAddCourseToProfFragment action = AddCourseDirections.actionAddCourseToProfFragment();
                        NavHostFragment.findNavController(AddCourse.this)
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

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                System.out.println("\n\nAccount insert error: "+t.getMessage()+"\n\n");
                Toast toast = Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG);
                toast.show();
                call.cancel();
            }
        });
    }

}
