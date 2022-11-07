package com.example.lessonslistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lessonslistapp.model.Lesson;

import java.util.List;

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonsViewHolder> {

    private Context context;
    private List<Lesson> list;


    public LessonsAdapter(Context context, List<Lesson> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LessonsAdapter.LessonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lesson_item, parent, false);
        return new LessonsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonsAdapter.LessonsViewHolder holder, int position) {
        holder.numberTextView.setText(String.valueOf(list.get(position).getLess().getNum()));
        holder.teacherTextView.setText(list.get(position).getLess().getTeacherName());
        holder.lessonTextView.setText(list.get(position).getLess().getTitle());
        holder.cabTextView.setText(list.get(position).getLess().getCab());
    }

    public void newItems(List<Lesson> list) {
        this.list = list;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LessonsViewHolder extends RecyclerView.ViewHolder {

        private TextView numberTextView, teacherTextView, lessonTextView, cabTextView;

        public LessonsViewHolder(@NonNull View itemView) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            teacherTextView = itemView.findViewById(R.id.teacherTextView);
            lessonTextView = itemView.findViewById(R.id.lessonTextView);
            cabTextView = itemView.findViewById(R.id.cabTextView);
        }
    }
}
