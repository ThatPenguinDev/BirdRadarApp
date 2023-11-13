package com.example.loginpage

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up a gesture detector
        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                // Check if the swipe is towards the left
                if (e1 != null && e2 != null && e2.x - e1.x > 50) {
                    // Swipe towards the left detected, finish the activity (go back)
                    finish()
                    return true
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })

        // Set up the onTouchListener for the root view
        binding.root.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        // Set up click listener for SignUp button
        binding.signUpButton.setOnClickListener {
            // Handle sign-up button click
            // Add your sign-up logic here

            // Open MainActivity3
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
