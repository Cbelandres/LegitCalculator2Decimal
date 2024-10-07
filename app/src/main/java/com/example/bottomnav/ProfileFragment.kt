package com.example.bottomnav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Getting references to UI elements
        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val editTextEmail = view.findViewById<EditText>(R.id.editTextEmail)
        val radioMale = view.findViewById<RadioButton>(R.id.radioMale)
        val radioFemale = view.findViewById<RadioButton>(R.id.radioFemale)
        val checkBoxTerms = view.findViewById<CheckBox>(R.id.checkBoxTerms)

        view.findViewById<View>(R.id.btnSaveProfile).setOnClickListener {
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val gender = if (radioMale.isChecked) "Male" else "Female"
            val acceptedTerms = checkBoxTerms.isChecked

            if (name.isNotEmpty() && email.isNotEmpty()) {
                Toast.makeText(context, "Profile saved!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
