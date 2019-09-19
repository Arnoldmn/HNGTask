package mna.mumbere.hngtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import android.widget.TextView
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    lateinit var mtxtEmail: TextView
    lateinit var mtxtFname: TextView
    lateinit var mtxtLname: TextView

    lateinit var mDatabase: DatabaseReference

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mtxtEmail = findViewById(R.id.etEmail)
        mtxtFname = findViewById(R.id.etFname)
        mtxtLname = findViewById(R.id.etLname)

        mDatabase = FirebaseDatabase.getInstance().reference

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val email = dataSnapshot.child("email").getValue().toString()
                val lname = dataSnapshot.child("first name").getValue().toString()
                val fname = dataSnapshot.child("first name").getValue().toString()

                mtxtEmail.text = email
                mtxtFname.text = fname
                mtxtLname.text = lname

            }

            override fun onCancelled(error: DatabaseError) {


            }
        })
        mAuth = FirebaseAuth.getInstance()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {

            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
