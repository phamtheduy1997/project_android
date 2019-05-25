package com.example.a7_minutes_workout;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a7_minutes_workout.database.WorkoutDB;
import com.example.a7_minutes_workout.utils.Common;

import java.util.Locale;

public class ViewWorkout extends AppCompatActivity {
    int idImage;
    private long sec = 0;
    String name;
    TextView tvTime, tvTitle;
    ImageView imDetailImage;
    Button btnStart, btnPause;
    private CountDownTimer countDownTimer;
    boolean isRunning = false;
    WorkoutDB workoutDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout);
        workoutDB = new WorkoutDB(this);
        tvTime = findViewById(R.id.tv_time);
        tvTitle = findViewById(R.id.tv_titlew);
        imDetailImage = findViewById(R.id.im_detail_image);
        btnStart = findViewById(R.id.btn_start);
        btnPause = findViewById(R.id.btn_pause);
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnPause.getText().toString().equals("Tạm dừng")){
                    if (isRunning){
                        countDownTimer.cancel();
                        isRunning = false;
                        btnPause.setText("Tiếp tục");
                        btnStart.setVisibility(View.INVISIBLE);

                    }
                }else if (btnPause.getText().toString().equals("Tiếp tục")){
                    btnPause.setVisibility(View.INVISIBLE);
                    btnStart.setVisibility(View.VISIBLE);
//                    btnStart.setText("Kết thúc");
                    new CountDownTimer(sec, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            tvTime.setText("" + millisUntilFinished / 1000 );

                        }

                        @Override
                        public void onFinish() {
                            finish();
                        }
                    }.start();
                }

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().toString().equals("Bắt đầu")){
                    if (!isRunning) {
                        btnPause.setVisibility(View.VISIBLE);
                        btnStart.setText("Kết thúc");
                        startTimer();
                    } else {
                        finish();
                    }
                    isRunning = !isRunning;
                }else if (btnStart.getText().toString().equals("Kết thúc")){
                    btnPause.setVisibility(View.INVISIBLE);
                    finish();
                }

            }
        });
        tvTime.setText("");
        if (getIntent() != null) {
            idImage = getIntent().getIntExtra("id_image", -1);
            name = getIntent().getStringExtra("name");
            imDetailImage.setImageResource(idImage);
            tvTitle.setText(name);
        }
    }

    private void startTimer() {
        int timeLimit = 0;
        if (workoutDB.getSettingMode() == 0)
        {
            timeLimit = Common.TIME_LIMIT_EASY;
        }
        else if (workoutDB.getSettingMode() == 1)
        {
            timeLimit = Common.TIME_LIMIT_MEDIUM;
        }
        else if (workoutDB.getSettingMode() == 2)
        {
            timeLimit = Common.TIME_LIMIT_HARD;
        }
        countDownTimer = new CountDownTimer(timeLimit, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
//                String timeLeftFormat = (String) String.format(Locale.getDefault(),"%s",sec);
                tvTime.setText("" + millisUntilFinished / 1000 );
                sec = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }


}
