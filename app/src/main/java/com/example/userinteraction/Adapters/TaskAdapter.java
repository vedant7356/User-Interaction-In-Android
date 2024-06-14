package com.example.userinteraction.Adapters;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userinteraction.Models.Task;
import com.example.userinteraction.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<Task> tasks;

    public TaskAdapter(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textViewTask.setText(task.getTaskName());
        holder.checkBoxCompleted.setChecked(task.isCompleted());

        holder.itemView.setOnClickListener(v -> {
            task.setCompleted(!task.isCompleted());
            animateTaskCompletion(holder.itemView);
            notifyItemChanged(position);
        });

        animateTaskAddition(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    private void animateTaskCompletion(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1f);
        animator.setDuration(500);
        animator.start();
    }

    private void animateTaskAddition(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
        view.startAnimation(animation);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;
        CheckBox checkBoxCompleted;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        }
    }
}
