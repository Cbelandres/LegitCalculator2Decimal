package com.example.bottomnav

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity

class AnimalAdapter(
    private val context: Context,
    private val animals: MutableList<Animal>
) : ArrayAdapter<Animal>(context, R.layout.list_item, animals) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            val inflater = LayoutInflater.from(context)
            itemView = inflater.inflate(R.layout.list_item, parent, false)
        }

        // Get the current animal item
        val animal = animals[position]

        // Set up the views from the layout
        val checkBox = itemView!!.findViewById<CheckBox>(R.id.checkbox)
        val imageView = itemView.findViewById<ImageView>(R.id.animal_image)
        val textView = itemView.findViewById<TextView>(R.id.animal_name)
        val editButton = itemView.findViewById<Button>(R.id.edit_button)
        val deleteButton = itemView.findViewById<Button>(R.id.delete_button)

        // Set the name and image
        textView.text = animal.name
        imageView.setImageResource(animal.imageResId)

        // Set the checkbox checked state and listen for changes
        checkBox.setOnCheckedChangeListener(null) // Avoid resetting when recycling views
        checkBox.isChecked = animal.checked

        // Show or hide the edit and delete buttons based on the checkbox state
        editButton.visibility = if (animal.checked) View.VISIBLE else View.GONE
        deleteButton.visibility = if (animal.checked) View.VISIBLE else View.GONE

        // Handle checkbox state change
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            animal.checked = isChecked
            notifyDataSetChanged() // Refresh the list
        }

        // Handle edit button click
        editButton.setOnClickListener {
            val fragmentActivity = context as? FragmentActivity
            fragmentActivity?.supportFragmentManager?.let { fragmentManager ->
                val fragment = fragmentManager.findFragmentById(R.id.fragment_container) as? ToDoListFragment
                fragment?.showEditDialog(animal, position)
            }
        }

        // Handle delete button click
        deleteButton.setOnClickListener {
            val fragmentActivity = context as? FragmentActivity
            fragmentActivity?.supportFragmentManager?.let { fragmentManager ->
                val fragment = fragmentManager.findFragmentById(R.id.fragment_container) as? ToDoListFragment
                fragment?.deleteAnimal(position)
            }
        }

        return itemView
    }
}
