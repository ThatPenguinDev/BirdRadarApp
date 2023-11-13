package com.example.loginpage


import com.google.maps.android.PolyUtil
import android.content.Context
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginpage.databinding.ActivityMainBinding
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    lateinit var signUpButton: Button // Add this line for the sign-up button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the views
        username = binding.email
        password = binding.password
        loginButton = binding.loginButton
        signUpButton = binding.signUpButton // Initialize the sign-up button

        // Set up a gesture detector to detect swipe down
        val gestureDetector = GestureDetector(this, object : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 != null) {
                    if (e2.y - e1.y > 50) {
                        // Swipe down detected, close the keyboard
                        hideKeyboard()
                        return true
                    }
                }
                return false
            }
        })

        // Set up the onTouchListener for the root view
        binding.root.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        loginButton.setOnClickListener(View.OnClickListener {
            if (username.text.toString() == "user" && password.text.toString() == "1234") {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        })

        signUpButton.setOnClickListener(View.OnClickListener {
            // Handle sign-up button click
            Toast.makeText(this, "Sign Up Clicked!", Toast.LENGTH_SHORT).show()
            // Add your sign-up logic here

            // Open SignUpActivity
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        })
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
