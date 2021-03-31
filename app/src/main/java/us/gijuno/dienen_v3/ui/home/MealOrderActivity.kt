package us.gijuno.dienen_v3.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_meal_order.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.data.LoggedIn

class MealOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_order)

        go_to_write_order_btn.setOnClickListener {
            startActivity(Intent(this, HomeWriteActivity::class.java))
        }

    }

    override fun onResume() {
        super.onResume()

        when {
            LoggedIn().isLoggedIn(LoggedIn().ACCESS_TOKEN) -> {

            }
            else -> {
                go_to_write_order_btn.visibility = View.GONE
            }
        }

    }
}