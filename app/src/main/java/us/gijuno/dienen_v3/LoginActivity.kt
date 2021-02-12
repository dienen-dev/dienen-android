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


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_id_et.addTextChangedListener(textWatcher)
        login_pw_et.addTextChangedListener(textWatcher)

        login_goto_register_btn.setOnClickListener() {
            //TODO 레지스터 액티비티로 연결
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
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
        }
    }


}