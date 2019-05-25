package com.example.a7_minutes_workout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a7_minutes_workout.database.WorkoutDB;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class Calendar extends AppCompatActivity {
    MaterialCalendarView calendarView;
    WorkoutDB workoutDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        workoutDB = new WorkoutDB(this);

        calendarView = findViewById(R.id.cv_calendar);

        // get workout day list from db
        List<String> workoutDay = workoutDB.getWorkoutDays();
        HashSet<CalendarDay> convertedList = new HashSet<>();
        for(String value:workoutDay)
            convertedList.add(CalendarDay.from(new Date(Long.parseLong(value))));
        calendarView.addDecorator(new WorkoutDone(convertedList));
    }
}
