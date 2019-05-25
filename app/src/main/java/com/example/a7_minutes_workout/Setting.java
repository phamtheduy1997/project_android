package com.example.a7_minutes_workout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.a7_minutes_workout.database.WorkoutDB;

import java.util.Calendar;
import java.util.Date;

public class Setting extends AppCompatActivity {
    Button btnSave;
    RadioButton rdiEasy, rdiMedium, rdiHard;
    RadioGroup rdiGroup;
    WorkoutDB workoutDB;
    ToggleButton switchAlarm;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btnSave = findViewById(R.id.btn_save);
        rdiGroup = findViewById(R.id.rd_group);
        rdiEasy = findViewById(R.id.rd_easy);
        rdiMedium = findViewById(R.id.rd_medium);
        rdiHard = findViewById(R.id.rd_hard);
        switchAlarm = findViewById(R.id.switch_alarm);
        timePicker = findViewById(R.id.time_picker);
        workoutDB = new WorkoutDB(this);
        int mode = workoutDB.getSettingMode();
        setRadioButton(mode);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                saveWorkoutMode();
                saveAlarm(switchAlarm.isChecked());

                Toast.makeText(Setting.this, "Đã lưu", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveAlarm(boolean checked) {
        if (checked) {
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;
            intent = new Intent(Setting.this, AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //Set alarm
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());
//            Date toDay = Calendar.getInstance().getTime();
//            calendar.set(toDay.getYear(),toDay.getMonth(),toDay.getDay(),timePicker.getHour(),timePicker.getMinute());

            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        } else {
            //Cancel Alarm
            Intent intent = new Intent(Setting.this, AlarmNotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        }
    }
    private void saveWorkoutMode() {
        int selectedID =  rdiGroup.getCheckedRadioButtonId();
        if (selectedID == rdiEasy.getId())
        {
            workoutDB.saveSettingMode(0);
        }
        else   if (selectedID == rdiMedium.getId())
        {
            workoutDB.saveSettingMode(1);
        }
        else   if (selectedID == rdiHard.getId())
        {
            workoutDB.saveSettingMode(2);
        }
    }

    private void setRadioButton(int mode) {
        if (mode == 0)
        {
            rdiGroup.check(R.id.rd_easy);
        }
        else if (mode == 1 )
        {
            rdiGroup.check(R.id.rd_medium);
        }
        else if (mode == 2 )
        {
            rdiGroup.check(R.id.rd_hard);
        }
    }
}
