package com.example.demoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.models.Account;
import com.example.demoapp.models.AttendaceCode;
import com.example.demoapp.models.Course;
import com.example.demoapp.models.Student;
import com.example.demoapp.models.Subject;
import com.example.demoapp.models.User;
import com.example.demoapp.services.AccountCall;
import com.example.demoapp.services.AttendaceCodeCall;
import com.example.demoapp.services.SubjectCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfFragment extends Fragment implements FirstFragment.UserSave {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView text;
    private static Long user = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView infoText;
    private Button addCourseBtn;
    private Button addSubjectBtn;
    private TextView message;
    private String data = "";
    public ProfFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfFragment newInstance(String param1, String param2) {
        ProfFragment fragment = new ProfFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_prof, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(user == null) {
            ProfFragmentArgs args = ProfFragmentArgs.fromBundle(getArguments());
            user = args.getUserID();
        }

        this.addCourseBtn = view.findViewById(R.id.add_new_course_btn);
        this.addSubjectBtn = view.findViewById(R.id.add_new_subject_btn);
        this.message = view.findViewById(R.id.basic_info_subject_textbox);

        this.infoText = view.findViewById(R.id.course_info);
        this.infoText.setMovementMethod(new ScrollingMovementMethod());
        Call<Subject> callSubject = SubjectCall.getInstance().getSubject(user.toString());
        callSubject.enqueue(new Callback<Subject>(){

            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                if(response.body() != null) {
                    addSubjectBtn.setVisibility(View.INVISIBLE);
                    message.setText(response.body().getName());
                    updateInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<Subject> call, Throwable t) {
                infoText.setText("Nu exista materii adaugate!");
                addCourseBtn.setVisibility(View.INVISIBLE);
            }
        });
        view.findViewById(R.id.add_new_subject_btn).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                addSubjectClicked();
            }
        });
        view.findViewById(R.id.add_new_course_btn).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                addCourseClicked();
            }
        });
    }

    private void updateInfo(Subject subject) {
        data = "";
        data += "Materia :" + subject.getName() + "\n";
        data += "Credite :" + subject.getCredits() + "\n";
        data += "Cursuri :\n";
        List<Course> courses = subject.getCourses();
        for(Course c : courses){
            data += "\t\tNume:" + c.getName()+ "\n\t\tCod prezenta:" + c.getAttCode() + "\n";
            data += "\t\t\tPrezenta la curs:\n";
            for(Student s : c.getAttendace()){
                data += "\t\t\t\t"+s.getFirstName() + " " + s.getLastName() + "\n";
            }
        }
        this.infoText.setText(data);
    }

    private void addSubjectClicked() {
       ProfFragmentDirections.ActionProfFragmentToAddSubject action = ProfFragmentDirections.actionProfFragmentToAddSubject();
       action.setProfId(user);
        NavHostFragment.findNavController(ProfFragment.this)
                .navigate(action);
    }

    private void addCourseClicked(){

        Call<Subject> callSubject = SubjectCall.getInstance().getSubject(user.toString());
        callSubject.enqueue(new Callback<Subject>(){

            @Override
            public void onResponse(Call<Subject> call, Response<Subject> response) {
                ProfFragmentDirections.ActionProfFragmentToAddCourse action = ProfFragmentDirections.actionProfFragmentToAddCourse();
                action.setSubjectId(response.body().getId());
                NavHostFragment.findNavController(ProfFragment.this)
                        .navigate(action);
            }
            @Override
            public void onFailure(Call<Subject> call, Throwable t) {
                System.out.println("\n\nSubject get for new course error: "+t.getMessage()+"\n\n");
                Toast toast = Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG);
                toast.show();
                call.cancel();
            }
        });


    }


    @Override
    public void onLoginRequest(User user) {
        //text.setText(user.getEmail());
    }
}
