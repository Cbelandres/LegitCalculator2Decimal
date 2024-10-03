package com.belandres.menuappbelandres

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), FragmentInterface {
    private lateinit var fragmentContainer: FrameLayout

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize fragment container
        fragmentContainer = findViewById(R.id.fragmentContainer)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        layoutInflater.inflate(R.layout.custom_popup_menu_item, toolbar, true)

        val button = findViewById<ImageView>(R.id.burgerJunction)
        button.setOnClickListener {
            val popup = PopupMenu(this, button)
            popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
            popup.setForceShowIcon(true)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_fragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, SampleFragment())
                            .commit()
                    }
                    R.id.menu_dialogue -> {
                        showAlertDialog()
                    }
                    R.id.menu_exit -> {
                        showExitConfirmationDialog()
                    }
                }
                true
            }
            popup.show()
        }
    }

    // Load a fragment into the FrameLayout container (using FragmentInterface)
    override fun loadFragment(fragment: SampleFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun showAlertDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Do you want to continue?")
            .setCancelable(false)
            .setPositiveButton("+") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("_") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        alert.setTitle("DIALOG")
        alert.show()
    }

    //exit option
    private fun showExitConfirmationDialog() {
        val exitDialogBuilder = AlertDialog.Builder(this)
        exitDialogBuilder.setMessage("Are you sure you want to exit?")
            .setCancelable(true)
            .setPositiveButton("Exit") { _, _ ->
                finishAffinity()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        val exitDialog = exitDialogBuilder.create()
        exitDialog.setTitle("Exit Confirmation")
        exitDialog.show()
    }
}
