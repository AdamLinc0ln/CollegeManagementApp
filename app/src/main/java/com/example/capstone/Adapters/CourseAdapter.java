package com.example.capstone.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.capstone.Activities.CourseActivity;
import com.example.capstone.Entities.Course;
import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;


public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {
    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewCourseTitle.setText(currentCourse.getTitle());
        holder.textViewCourseEnd.setText(currentCourse.getEnd());
        holder.textViewCourseStatus.setText(CourseActivity.getStatus(currentCourse.getStatus()));
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
        notifyDataSetChanged();
    }

    public Course getCourseAt(int position){
        return courses.get(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder {
        private TextView textViewCourseTitle;
        private TextView textViewCourseEnd;
        private TextView textViewCourseStatus;


        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewCourseTitle = itemView.findViewById(R.id.text_view_course_title);
            textViewCourseEnd = itemView.findViewById(R.id.text_view_course_end_date);
            textViewCourseStatus = itemView.findViewById(R.id.text_view_course_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Course course);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
