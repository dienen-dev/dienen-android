package us.gijuno.dienen_v3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin.*
import us.gijuno.dienen_v3.data.DienenService
import us.gijuno.dienen_v3.data.Warning

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        go_to_write_admin.setOnClickListener {
            startActivity(Intent(this, WriteAdminActivity::class.java))
        }

    }
}