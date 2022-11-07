package com.example.lessonslistapp.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.example.lessonslistapp.LessonsAdapter;
import com.example.lessonslistapp.MainActivity;
import com.example.lessonslistapp.R;
import com.example.lessonslistapp.model.Group;
import com.example.lessonslistapp.model.Lesson;
import com.example.lessonslistapp.model.Teacher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TeachersFragment extends Fragment implements View.OnClickListener {

    private RecyclerView lessonTeachersRecyclerView;
    private Button teachersDateButton, teachersShowButton;
    private Spinner lessonTeachersSpinner;
    private DatePickerDialog datePickerDialog;

    private String[] teachers;
    private Integer[] teachers_id;

    private Calendar dateAndTime = Calendar.getInstance();

    private static List<Lesson> list2 = new ArrayList<>();
    private Date date;

    private static LessonsAdapter lessonsAdapter;

    private Date mainDate = new Date(System.currentTimeMillis());

    private String teacher;
    private int teacher_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_teachers, container, false);

        List<Teacher> list = MainActivity.mainRepository.getAllTeachers();

        teachers = new String[list.size()];
        teachers_id = new Integer[list.size()];

        for (int i = 0; i < list.size(); i++) {
            teachers[i] = list.get(i).getName();
        }
        for (int i = 0; i < list.size(); i++) {
            teachers_id[i] = list.get(i).getId();
        }

        dateAndTime.set(Calendar.DATE, (int) mainDate.getTime());

        lessonTeachersRecyclerView = v.findViewById(R.id.lessonTeachersRecyclerView);
        lessonTeachersSpinner = v.findViewById(R.id.lessonTeachersSpinner);
        teachersDateButton = v.findViewById(R.id.teachersDateButton);
        teachersShowButton = v.findViewById(R.id.teachersShowButton);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        teachersDateButton.setText(sdf.format(mainDate));

        teachersDateButton.setOnClickListener(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, teachers);

        lessonTeachersSpinner.setAdapter(arrayAdapter);

        lessonTeachersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                teacher = arrayAdapter.getItem(i);
                teacher_id = teachers_id[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lessonTeachersRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

        lessonsAdapter = new LessonsAdapter(this.getContext(), list2);
        lessonTeachersRecyclerView.setAdapter(lessonsAdapter);

        teachersShowButton.setOnClickListener(this);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getTime() {
        datePickerDialog = new DatePickerDialog(this.getContext(), dataPickerListener,
                mainDate.getYear(), mainDate.getMonth(), mainDate.getDay());
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener dataPickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            date = dateAndTime.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            teachersDateButton.setText(sdf.format(date));
        }
    };

    public static void show(List<Lesson> list) {
        lessonsAdapter.newItems(list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teachersShowButton:
                if(teacher != null && !teacher.equals("")) {
                    if(date == null) {
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.println(smf.format(mainDate));
                        try {
                            show(MainActivity.mainRepository.getScheduleByTeacher(teacher_id, smf.format(mainDate)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.println(smf.format(date));
                        try {
                            show(MainActivity.mainRepository.getSchedule(teacher_id, smf.format(date)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.teachersDateButton:
                getTime();
                break;
        }
    }
}