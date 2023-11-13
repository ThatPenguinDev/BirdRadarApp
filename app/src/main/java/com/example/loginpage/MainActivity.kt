package com.example.loginpage



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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var loginButton: Button
    lateinit var signUpButton: Button // Add this line for the sign-up button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        // Initialize the views
        username = binding.email
        password = binding.password
        loginButton = binding.loginButton
        signUpButton = binding.signUpButton // Initialize the sign-up button

        val auth = FirebaseAuth.getInstance()
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

        loginButton.setOnClickListener {
            val enteredUsername = username.text.toString()
            val enteredPassword = password.text.toString()

            val databaseReference = FirebaseDatabase.getInstance().getReference("users")
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var loginSuccessful = false

                    for (userSnapshot in dataSnapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)

                        if (user != null && user.email == enteredUsername && user.password == enteredPassword) {
                            // Login Successful
                            loginSuccessful = true
                            break
                        }
                    }

                    if (loginSuccessful) {
                        Toast.makeText(this@MainActivity, "Login Successful!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, MainActivity3::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "Login Failed!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any database error
                    Toast.makeText(this@MainActivity, "Database Error: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        signUpButton.setOnClickListener(View.OnClickListener {
            // Handle sign-up button click
            Toast.makeText(this, "Sign Up Clicked!", Toast.LENGTH_SHORT).show()
            // Add your sign-up logic here

            // Open SignUpActivity
            val intent1 = Intent(this, MainActivity2::class.java)
            startActivity(intent1)
        })
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}