package com.example.a7_minutes_workout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.a7_minutes_workout.adapter.WorkoutAdapter;
import com.example.a7_minutes_workout.model.Workout;

import java.util.ArrayList;
import java.util.List;

public class ListWorkout extends AppCompatActivity {
    private List<Workout> workoutList = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    WorkoutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_workout);
        initData();
        recyclerView = findViewById(R.id.list_workout);
        adapter = new WorkoutAdapter(workoutList,getBaseContext());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        workoutList.add(new Workout(R.drawable.nhay_cheo,"Nhảy đan chéo"));
        workoutList.add(new Workout(R.drawable.wall_sits,"Ngồi dựa tường"));
        workoutList.add(new Workout(R.drawable.pushup,"Chống đẩy"));
        workoutList.add(new Workout(R.drawable.abs_crunch,"Tập cơ bụng"));
        workoutList.add(new Workout(R.drawable.step_on_chair,"Bước lên ghế"));
        workoutList.add(new Workout(R.drawable.regular_squat,"Gánh đùi"));
        workoutList.add(new Workout(R.drawable.tricep_dip_on_chair,"Nhún cơ tam đầu bằng ghế"));
        workoutList.add(new Workout(R.drawable.plank,"Tấm ván"));
        workoutList.add(new Workout(R.drawable.high_knee_running_in_place,"Chạy tại chỗ đầu gối đánh cao"));
        workoutList.add(new Workout(R.drawable.buoc_gap_goi,"Bước gập gối"));
        workoutList.add(new Workout(R.drawable.pushup_with_side_rotations,"Chống đẩy và xoay người"));
        workoutList.add(new Workout(R.drawable.side_plank,"Tấm ván bên"));

    }
}
