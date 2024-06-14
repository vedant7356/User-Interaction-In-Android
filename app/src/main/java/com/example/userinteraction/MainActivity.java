package com.example.userinteraction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userinteraction.Adapters.TaskAdapter;
import com.example.userinteraction.Models.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAdd;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerView);

        tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        buttonAdd.setOnClickListener(v -> {
            String taskName = editTextTask.getText().toString();
            if (!TextUtils.isEmpty(taskName)) {
                tasks.add(new Task(taskName));
                taskAdapter.notifyItemInserted(tasks.size() - 1);
                recyclerView.scrollToPosition(tasks.size() - 1);
                editTextTask.setText("");
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                tasks.remove(position);
                taskAdapter.notifyItemRemoved(position);
                animateTaskDeletion(viewHolder.itemView);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void animateTaskDeletion(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        view.startAnimation(animation);
    }
}