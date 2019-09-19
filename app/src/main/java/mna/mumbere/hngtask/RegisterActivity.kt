package mna.mumbere.hngtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    lateinit var mbtnRegister: Button
    lateinit var metEmail: EditText
    lateinit var metFname: EditText
    lateinit var metLname: EditText
    lateinit var metPassword: EditText
    lateinit var mtxtReg: TextView

    lateinit var mDatabase: DatabaseReference

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        //    mDatabase = FirebaseDatabase.getInstance().getReference("Users")

        mbtnRegister = findViewById(R.id.btnRegister)
        metEmail = findViewById(R.id.txtEmail)
        metPassword = findViewById(R.id.txtPassword)
        metFname = findViewById(R.id.txtFname)
        metLname = findViewById(R.id.txtLname)
        mtxtReg = findViewById(R.id.txtReg)

        mbtnRegister.setOnClickListener {

            val name = metFname.text.toString().trim()
            val lname = metLname.text.toString().trim()
            val email = metEmail.text.toString().trim()
            val mpasswor = metPassword.text.toString().trim()

            if (TextUtils.isEmpty(name)) {
                metFname.error = "Enter first name"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(lname)) {
                metLname.error = "Enter last name"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(email)) {
                metEmail.error = "Enter email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(mpasswor)) {
                metPassword.error = "Enter password"
                return@setOnClickListener
            }
            createUser(name, lname, email, mpasswor)

        }

        mtxtReg.setOnClickListener {
            val intentLog = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intentLog)
        }


    }

    private fun createUser(name: String, lname: String, email: String, mpasswor: String) {

        mAuth.createUserWithEmailAndPassword(email, mpasswor)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {

                    val currentUser = FirebaseAuth.getInstance().currentUser
                    val uid = currentUser!!.uid

                    val usersMap = HashMap<String, String>()

                    usersMap["first name"] = name
                    usersMap["last name"] = lname

                    mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(uid)

                    mDatabase.setValue(usersMap).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intentMain = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intentMain)
                            finish()
                        }
                    }


                } else {

                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                // ...
            }
    }
}
