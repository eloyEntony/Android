package com.example.courseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.courseapp.adapter.CategoryAdapter;
import com.example.courseapp.adapter.CourseAdapter;
import com.example.courseapp.model.Category;
import com.example.courseapp.model.Course;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecycler, courseRecycler;
    CategoryAdapter categoryAdapter;
    static CourseAdapter courseAdapter;
    static List<Course> coursesList = new ArrayList<>();
    static List<Course> fullCoursesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Games"));
        categoryList.add(new Category(2, "Sites"));
        categoryList.add(new Category(3, "Lang"));
        categoryList.add(new Category(4, "Others"));

        setCategoryRecycler(categoryList);

        coursesList.add(new Course(1, "java", "Профессия Java\nразработчик", "1 January", "Junior", "#424345", "", 3));
        coursesList.add(new Course(2, "python", "Профессия Python\nразработчик", "10 January", "Junior", "#9FA52D", "", 3));
        coursesList.add(new Course(3, "unity", "Профессия Unity\nразработчик", "10 January", "Junior", "#76134D", "", 1));
        coursesList.add(new Course(4, "front_end", "Профессия Front-end\nразработчик", "10 January", "Junior", "#B14935", "", 2));
        coursesList.add(new Course(5, "back_end", "Профессия Back-end\nразработчик", "10 January", "Junior", "#2C55A6", "", 2));
        coursesList.add(new Course(6, "full_stack", "Профессия Full stack\nразработчик", "10 January", "Junior", "#0D0F29", "", 2));

        fullCoursesList.addAll(coursesList);
        setCourseRecycler(coursesList);

    }

    private void setCourseRecycler(List<Course> coursesList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        courseRecycler = findViewById(R.id.courseRecyclar);
        courseRecycler.setLayoutManager(layoutManager);

        courseAdapter = new CourseAdapter(this, coursesList);
        courseRecycler.setAdapter(courseAdapter);
    }

    private void setCategoryRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);
    }

    public static void showCoursesByCategory(int category){

        coursesList.clear();
        coursesList.addAll(fullCoursesList);

        List<Course> filterCourses = new ArrayList<>();

        for(Course c : coursesList){
            if(c.getCategory()==category)
                filterCourses.add(c);
        }

        coursesList.clear();
        coursesList.addAll(filterCourses);

        courseAdapter.notifyDataSetChanged();//do update
    }

    public void openCart(View view){
        Intent intent = new Intent(this, OrderPage.class);
        startActivity(intent);
    }
}