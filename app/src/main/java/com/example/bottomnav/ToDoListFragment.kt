package com.example.bottomnav

import android.app.AlertDialog
import android.graphics.Rect
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.Fragment

class ToDoListFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var adapter: AnimalAdapter
    private var animals = mutableListOf<Animal>()
    private lateinit var gestureDetector: GestureDetectorCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_to_do_list, container, false)

        // Initialize views
        listView = view.findViewById(R.id.list_view)
        editText = view.findViewById(R.id.edit_text)
        addButton = view.findViewById(R.id.add_button)

        adapter = AnimalAdapter(requireContext(), animals)
        listView.adapter = adapter

        // GestureDetector to handle double tap
        gestureDetector = GestureDetectorCompat(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                // Find the position of the clicked item
                val view = listView.pointToView(e.x.toInt(), e.y.toInt())
                val position = listView.getPositionForView(view)
                if (position != ListView.INVALID_POSITION) {
                    val animal = animals[position]
                    showEditDeleteDialog(animal, position)
                }
                return true
            }
        })

        // Attach GestureDetector to ListView
        listView.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            v.performClick()
        }

        addButton.setOnClickListener {
            val name = editText.text.toString().trim()
            if (name.isNotEmpty()) {
                val animal = Animal(name, R.drawable.img, false) // Replace R.drawable.img with your drawable resource
                animals.add(animal)
                adapter.notifyDataSetChanged()
                editText.setText("")
            } else {
                Toast.makeText(requireContext(), "Animal name cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun showEditDeleteDialog(animal: Animal, position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(animal.name)
            .setItems(arrayOf("Edit", "Delete")) { _, which ->
                when (which) {
                    0 -> showEditDialog(animal, position)
                    1 -> deleteAnimal(position)
                }
            }
            .show()
    }

    fun showEditDialog(animal: Animal, position: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_animal, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.edit_animal_name)

        nameEditText.setText(animal.name)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Animal")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newName = nameEditText.text.toString().trim()
                if (newName.isNotEmpty()) {
                    animal.name = newName // Update the name directly on the object
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "Animal name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun deleteAnimal(position: Int) {
        animals.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    // Extension function to get the view under a specific point in ListView
    private fun ListView.pointToView(x: Int, y: Int): View? {
        val firstPosition = firstVisiblePosition
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val bounds = Rect()
            child.getHitRect(bounds)
            if (bounds.contains(x, y)) {
                return child
            }
        }
        return null
    }
}
