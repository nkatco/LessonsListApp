package com.example.lessonslistapp;

import com.example.lessonslistapp.model.Group;
import com.example.lessonslistapp.model.Less;
import com.example.lessonslistapp.model.Lesson;
import com.example.lessonslistapp.model.Teacher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainRepository {

    private List<Group> groups = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();

    public MainRepository() throws IOException, JSONException {
        String lineGroup = getGroups();
        String lineTeachers = getTeachers();

        JSONArray arrayGroups = null;
        JSONArray arrayTeachers = null;

        arrayGroups = new JSONArray(lineGroup);
        arrayTeachers = new JSONArray(lineTeachers);

        for (int i = 0; i < arrayGroups.length(); i++) {
            JSONObject obj = arrayGroups.getJSONObject(i);
            groups.add(new Group(obj.getInt("id"), obj.getString("name")));
        }
        System.out.println(groups.size());
        for (int i = 0; i < arrayTeachers.length(); i++) {
            JSONObject obj = arrayTeachers.getJSONObject(i);
            teachers.add(new Teacher(obj.getInt("id"), obj.getString("name")));
        }
    }
    public List<Group> getAllGroups() {
        return groups;
    }
    public List<Teacher> getAllTeachers() {
        return teachers;
    }

    private String getGroups() throws IOException {
        URL url = new URL("http://mfc.samgk.ru/api/groups");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        line = builder.toString();
        return line;
    }
    private String getTeachers() throws IOException {
        URL url = new URL("http://asu.samgk.ru/api/teachers");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        line = builder.toString();
        return line;
    }
    public List<Lesson> getSchedule(int id, String data) throws IOException {
        URL url = new URL("https://asu.samgk.ru/api/schedule/" + id + "/" + data + "/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        line = builder.toString();
        System.out.println(line);

        List<Lesson> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(line);
            String date = object.getString("date");
            JSONArray lessons = object.getJSONArray("lessons");
            for (int i = 0; i < lessons.length(); i++) {
                JSONObject obj = lessons.getJSONObject(i);
                list.add(new Lesson(date, new Less(obj.getString("title"),
                        obj.getInt("num"),
                        obj.getString("teachername"),
                        obj.getString("nameGroup"),
                        obj.getString("cab"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("lessons size: " + list.size());
        return list;
    }
    public List<Lesson> getScheduleByTeacher(int id, String data) throws IOException {
        URL url = new URL("https://asu.samgk.ru/api/schedule/teacher/" + data + "/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.connect();

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        line = builder.toString();
        System.out.println(line);

        List<Lesson> list = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(line);
            String date = object.getString("date");
            JSONArray lessons = object.getJSONArray("lessons");
            for (int i = 0; i < lessons.length(); i++) {
                JSONObject obj = lessons.getJSONObject(i);
                list.add(new Lesson(date, new Less(obj.getString("title"),
                        obj.getInt("num"),
                        obj.getString("teachername"),
                        obj.getString("nameGroup"),
                        obj.getString("cab"))));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("lessons size: " + list.size());
        return list;
    }
}
