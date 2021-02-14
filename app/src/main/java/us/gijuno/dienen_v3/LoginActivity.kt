package us.gijuno.dienen_v3

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import us.gijuno.dienen_v3.data.Repository


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_id_et.addTextChangedListener(textWatcher)
        login_pw_et.addTextChangedListener(textWatcher)

        login_login_btn.setOnClickListener {

            val userID = login_id_et.text.toString()
            val passowrd = login_pw_et.text.toString()

            GlobalScope.launch {
                Repository().postLogin(userID,passowrd)
            }
        }

        login_goto_register_btn.setOnClickListener() {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    var textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (TextUtils.isEmpty(login_id_et.getText()) || TextUtils.isEmpty(login_pw_et.getText())) {
                login_login_btn.setEnabled(false)
                login_login_btn.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.colorPrimary))
            } else {
                login_login_btn.setEnabled(true)
                login_login_btn.setTextColor(Color.parseColor("#FFFFFF"))
            }
        }

        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int,
            after: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
        }
    }


}