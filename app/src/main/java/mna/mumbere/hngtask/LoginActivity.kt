package mna.mumbere.hngtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast


class LoginActivity : AppCompatActivity() {

    lateinit var mbtnLogin: Button
    lateinit var mEmail: EditText
    lateinit var mPassword: EditText

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.txtEmail)
        mPassword = findViewById(R.id.txtPassword)
        mbtnLogin = findViewById(R.id.btnLogin)

        mAuth = FirebaseAuth.getInstance()

        mbtnLogin.setOnClickListener {

            val email = mEmail.text.toString().trim()
            val password = mPassword.text.toString().trim()


            userLogin(email, password)

        }

    }


    private fun userLogin(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    val startIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(startIntent)

                } else {
                    Toast.makeText(
                        applicationContext, "Invalid email && password",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // ...
            }
    }
}



