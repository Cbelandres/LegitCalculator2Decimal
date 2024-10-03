package com.example.listanimals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AnimalAdapter extends ArrayAdapter<Animal> {

    private final Context context;
    private final List<Animal> animals;

    public AnimalAdapter(@NonNull Context context, @NonNull List<Animal> animals) {
        super(context, R.layout.list_item, animals);
        this.context = context;
        this.animals = animals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        Animal animal = animals.get(position);

        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        ImageView imageView = convertView.findViewById(R.id.animal_image);
        TextView textView = convertView.findViewById(R.id.animal_name);
        Button editButton = convertView.findViewById(R.id.edit_button);
        Button deleteButton = convertView.findViewById(R.id.delete_button);

        checkBox.setChecked(animal.isChecked());
        imageView.setImageResource(animal.getImageResId());
        textView.setText(animal.getName());

        // Handle checkbox state changes
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            animal.setChecked(isChecked);
            notifyDataSetChanged();
        });

        // Update button visibility
        if (animal.isChecked()) {
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

        // Handle edit button click
        editButton.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).showEditDialog(animal, position);
            }
        });

        // Handle delete button click
        deleteButton.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).deleteAnimal(position);
            }
        });

        return convertView;
    }
}
