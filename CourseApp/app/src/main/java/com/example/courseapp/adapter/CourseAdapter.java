package com.example.courseapp.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseapp.CoursePage;
import com.example.courseapp.R;
import com.example.courseapp.model.Course;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    Context context;
    List<Course> courseList;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseItems = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        return new CourseAdapter.CourseViewHolder(courseItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.courseBg.setCardBackgroundColor(Color.parseColor(courseList.get(position).getColor()));

        int imageId = context.getResources().getIdentifier("ic_" + courseList.get(position).getImg(), "drawable", context.getPackageName());
        holder.courseImage.setImageResource(imageId);

        holder.courseTitle.setText(courseList.get(position).getTitle());
        holder.courseDate.setText(courseList.get(position).getDate());
        holder.courseLevel.setText(courseList.get(position).getLevel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CoursePage.class);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, new Pair<View, String>(holder.courseImage, "courseImage"));

                intent.putExtra("courseBg", Color.parseColor(courseList.get(position).getColor()));
                intent.putExtra("courseImage", imageId);
                intent.putExtra("courseTitle", courseList.get(position).getTitle());
                intent.putExtra("courseDate", courseList.get(position).getDate());
                intent.putExtra("courseLevel", courseList.get(position).getLevel());
                intent.putExtra("courseText", courseList.get(position).getText());
                intent.putExtra("courseId", courseList.get(position).getId());



                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public static final class CourseViewHolder extends RecyclerView.ViewHolder{

        CardView courseBg;
        ImageView courseImage;
        TextView courseTitle;
        TextView courseDate;
        TextView courseLevel;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseBg= itemView.findViewById(R.id.courseBg);
            courseImage= itemView.findViewById(R.id.courseImage);
            courseTitle= itemView.findViewById(R.id.courseTitle);
            courseDate= itemView.findViewById(R.id.courseDate);
            courseLevel= itemView.findViewById(R.id.courseLevel);
        }
    }

}
