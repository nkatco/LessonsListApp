package com.example.lessonslistapp.fragments;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LessonsFrament extends Fragment implements View.OnClickListener {

    private static RecyclerView lessonGroupsRecyclerView;
    private static Spinner lessonGroupSpinner;
    private Button groupsShowButton, groupsDateButton;
    private DatePickerDialog datePickerDialog;

    private Calendar dateAndTime = Calendar.getInstance();

    private static String[] groups;
    private static Integer[] groups_id;

    private String group;
    private int group_id;

    private static List<Lesson> list2 = new ArrayList<>();

    private Date date;

    private static LessonsAdapter lessonsAdapter;

    private Date mainDate = new Date(System.currentTimeMillis());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lessons_frament, container, false);

        List<Group> list = MainActivity.mainRepository.getAllGroups();

        groups = new String[list.size()];
        groups_id = new Integer[list.size()];

        for (int i = 0; i < list.size(); i++) {
            groups[i] = list.get(i).getName();
        }
        for (int i = 0; i < list.size(); i++) {
            groups_id[i] = list.get(i).getId();
        }

        dateAndTime.set(Calendar.DATE, (int) mainDate.getTime());

        lessonGroupsRecyclerView = v.findViewById(R.id.lessonGroupsRecyclerView);
        lessonGroupSpinner = v.findViewById(R.id.lessonGroupSpinner);
        groupsDateButton = v.findViewById(R.id.groupsDateButton);
        groupsShowButton = v.findViewById(R.id.groupsShowButton);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        groupsDateButton.setText(sdf.format(mainDate));

        groupsDateButton.setOnClickListener(this);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, groups);

        lessonGroupSpinner.setAdapter(arrayAdapter);

        lessonGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                group = arrayAdapter.getItem(i);
                group_id = groups_id[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                group = null;
            }
        });

        lessonGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));

        lessonsAdapter = new LessonsAdapter(this.getContext(), list2);
        lessonGroupsRecyclerView.setAdapter(lessonsAdapter);

        groupsShowButton.setOnClickListener(this);
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
            groupsDateButton.setText(sdf.format(date));
        }
    };

    public static void show(List<Lesson> list) {
        lessonsAdapter.newItems(list);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.groupsShowButton:
                if(group != null && !group.equals("")) {
                    if(date == null) {
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.println(smf.format(mainDate));
                        try {
                            show(MainActivity.mainRepository.getSchedule(group_id, smf.format(mainDate)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.println(smf.format(date));
                        try {
                            show(MainActivity.mainRepository.getSchedule(group_id, smf.format(date)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case R.id.groupsDateButton:
                getTime();
                break;
        }
    }
}