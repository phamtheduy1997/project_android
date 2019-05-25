package com.example.a7_minutes_workout;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a7_minutes_workout.database.WorkoutDB;
import com.example.a7_minutes_workout.model.Workout;
import com.example.a7_minutes_workout.utils.Common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyTraining extends AppCompatActivity {
    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady, txtCountdown,txtTimer,ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;

    int ex_id = 0;
    WorkoutDB workoutDB;

    List<Workout> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training);
        workoutDB = new WorkoutDB(this);
        initData();
        // mapping
        btnStart = findViewById(R.id.btn_startw);

        ex_image = findViewById(R.id.im_detail_image);

        txtCountdown= findViewById(R.id.txtCountDown);
        txtGetReady = findViewById(R.id.txtGetReady);
        txtTimer = findViewById(R.id.tv_time);
        ex_name =findViewById(R.id.tv_titlew);

        progressBar = findViewById(R.id.progress_bar);

        layoutGetReady = findViewById(R.id.layout_get_ready);

        //Set data
        progressBar.setMax(list.size());

        setWorkoutInformation(ex_id);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().toString().toLowerCase().equals("bắt đầu"))
                {
                    showGetReady();
                    btnStart.setText("Hoàn Thành");
                }
                else if (btnStart.getText().toString().toLowerCase().equals("hoàn thành"))
                {
                    // if click done while workout is running
                    if (workoutDB.getSettingMode() == 0)
                    {
                        workoutEasyCountdown.cancel();
                    }
                    else   if (workoutDB.getSettingMode() == 1)
                    {
                        workoutMediumCountdown.cancel();
                    }
                    else   if (workoutDB.getSettingMode() == 2)
                    {
                        workoutHardCountdown.cancel();
                    }
                    restTimeCountdown.cancel();

                    if (ex_id < list.size())
                    {
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else showFinished();
                }
                else
                {
                    if (workoutDB.getSettingMode() == 0)
                    {
                        workoutEasyCountdown.cancel();
                    }
                    else   if (workoutDB.getSettingMode() == 1)
                    {
                        workoutMediumCountdown.cancel();
                    }
                    else   if (workoutDB.getSettingMode() == 2)
                    {
                        workoutHardCountdown.cancel();
                    }

                    restTimeCountdown.cancel();

                    if (ex_id < list.size())
                    {
                        setWorkoutInformation(ex_id);
                    }
                    else showFinished();

                }

            }
        });
    }

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("Hoàn Thành !!!");
        txtCountdown.setText("Chúc mừng ! \nBạn đã hoàn thành xong bài tập ngày hôm nay ");
        txtCountdown.setTextSize(20);

        //save done workout
        workoutDB.saveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        btnStart.setText("Bỏ qua");

        layoutGetReady.setVisibility(View.VISIBLE);

        restTimeCountdown.start();

        txtGetReady.setText("Thời gian nghỉ");
    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.VISIBLE);

        txtGetReady.setText("Chuẩn bị");
        new CountDownTimer(6000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                txtCountdown.setText(""+(millisUntilFinished-1000)/1000);
            }

            @Override
            public void onFinish() {
                showWorkout();
            }
        }.start();
    }

    private void showWorkout() {
        if (ex_id < list.size()){
            ex_image.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);
            if (workoutDB.getSettingMode() == 0)
            {
                workoutEasyCountdown.start();
            }
            else   if (workoutDB.getSettingMode() == 1)
            {
                workoutMediumCountdown.start();
            }
            else   if (workoutDB.getSettingMode() == 2)
            {
                workoutHardCountdown.start();
            }
            Glide.with(this).load(list.get(ex_id).getIdImage()).into(ex_image);
//            ex_image.setImageResource(list.get(ex_id).getIdImage());
            ex_name.setText(list.get(ex_id).getName());
        }
        else {
            showFinished();
        }
    }
    CountDownTimer workoutEasyCountdown = new CountDownTimer(Common.TIME_LIMIT_EASY,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size()-1){
                ex_id++ ;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setWorkoutInformation(ex_id);
                btnStart.setText("Bắt đầu");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer workoutMediumCountdown = new CountDownTimer(Common.TIME_LIMIT_MEDIUM,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size()-1){
                ex_id++ ;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setWorkoutInformation(ex_id);
                btnStart.setText("Bắt đầu");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer workoutHardCountdown = new CountDownTimer(Common.TIME_LIMIT_HARD,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            if (ex_id < list.size()-1){
                ex_id++ ;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");

                setWorkoutInformation(ex_id);
                btnStart.setText("Bắt đầu");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer restTimeCountdown = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtCountdown.setText(""+(millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            setWorkoutInformation(ex_id);
            showWorkout();
        }
    };
    private void setWorkoutInformation(int id) {
        Glide.with(this).load(list.get(id).getIdImage()).into(ex_image);
//        ex_image.setImageResource(list.get(id).getIdImage());
        ex_name.setText(list.get(id).getName());
        btnStart.setText("Bắt đầu");

        ex_image.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);

        layoutGetReady.setVisibility(View.INVISIBLE);
    }

    private void initData() {
        list.add(new Workout(R.drawable.nhay_cheo,"Nhảy đan chéo"));
        list.add(new Workout(R.drawable.wall_sits,"Ngồi dựa tường"));
        list.add(new Workout(R.drawable.pushup,"Chống đẩy"));
        list.add(new Workout(R.drawable.abs_crunch,"Tập cơ bụng"));
        list.add(new Workout(R.drawable.step_on_chair,"Bước lên ghế"));
        list.add(new Workout(R.drawable.regular_squat,"Gánh đùi"));
        list.add(new Workout(R.drawable.tricep_dip_on_chair,"Nhún cơ tam đầu bằng ghế"));
        list.add(new Workout(R.drawable.plank,"Tấm ván"));
        list.add(new Workout(R.drawable.high_knee_running_in_place,"Chạy tại chỗ đầu gối đánh cao"));
        list.add(new Workout(R.drawable.buoc_gap_goi,"Bước gập gối"));
        list.add(new Workout(R.drawable.pushup_with_side_rotations,"Chống đẩy và xoay người"));
        list.add(new Workout(R.drawable.side_plank,"Tấm ván bên"));

    }
}

