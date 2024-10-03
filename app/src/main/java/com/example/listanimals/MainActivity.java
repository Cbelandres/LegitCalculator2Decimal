package com.example.listanimals;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Button addButton;
    private AnimalAdapter adapter;
    private List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        editText = findViewById(R.id.edit_text);
        addButton = findViewById(R.id.add_button);

        animals = new ArrayList<>();
        adapter = new AnimalAdapter(this, animals);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String name = editText.getText().toString().trim();
            if (!name.isEmpty()) {
                Animal animal = new Animal(name, R.drawable.img, false);
                animals.add(animal);
                adapter.notifyDataSetChanged();
                editText.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Animal name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Animal animal = animals.get(position);
            showEditDeleteDialog(animal, position);
            return true;
        });
    }

    public void showEditDialog(Animal animal, int position) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_animal, null);
        EditText nameEditText = dialogView.findViewById(R.id.edit_animal_name);
        // Assume dialog_edit_animal.xml contains EditText with id edit_animal_name

        nameEditText.setText(animal.getName());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Animal");
        builder.setView(dialogView);
        builder.setPositiveButton("Save", (dialog, which) -> {
            String newName = nameEditText.getText().toString().trim();
            if (!newName.isEmpty()) {
                animal.setName(newName);
                animals.set(position, animal);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Animal name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void showEditDeleteDialog(Animal animal, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(animal.getName());
        builder.setItems(new String[]{"Edit", "Delete"}, (dialog, which) -> {
            switch (which) {
                case 0: // Edit
                    showEditDialog(animal, position);
                    break;
                case 1: // Delete
                    deleteAnimal(position);
                    break;
            }
        });
        builder.show();
    }

    public void deleteAnimal(int position) {
        animals.remove(position);
        adapter.notifyDataSetChanged();
    }
}
