package com.example.a7_minutes_workout.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a7_minutes_workout.R;
import com.example.a7_minutes_workout.ViewWorkout;
import com.example.a7_minutes_workout.interfaceworkout.ItemClick;
import com.example.a7_minutes_workout.model.Workout;

import java.util.List;

class WorkoutHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView image;
    public TextView text;
    public ItemClick itemClick;

    public WorkoutHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.im_workout);
        text = itemView.findViewById(R.id.tv_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public void onClick(View view) {
        itemClick.onClick(view,getAdapterPosition());
    }
}
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder>{
    private List<Workout> workoutList;
    private Context context;

    public WorkoutAdapter(List<Workout> workoutList, Context context) {
        this.workoutList = workoutList;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_workout,viewGroup,false);
        return  new WorkoutHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder workoutHolder, int i) {
        workoutHolder.image.setImageResource(workoutList.get(i).getIdImage());
        workoutHolder.text.setText(workoutList.get(i).getName());
        workoutHolder.setItemClick(new ItemClick() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, ViewWorkout.class);
                intent.putExtra("id_image",workoutList.get(position).getIdImage());
                intent.putExtra("name",workoutList.get(position).getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }
}
