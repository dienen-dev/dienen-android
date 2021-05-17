package us.gijuno.dienen_v3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import us.gijuno.dienen_v3.data.Repository
import java.lang.reflect.Type


class RegisterActivity : AppCompatActivity() {
    private var postRegister: Int = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        personal_information.setOnClickListener {
            startActivity(Intent(this, Personal_information::class.java))
        }

        register_id_et.addTextChangedListener(textWatcher)
        register_pw_et.addTextChangedListener(textWatcher)
        register_name_et.addTextChangedListener(textWatcher)
        register_code_et.addTextChangedListener(textWatcher)


        register_register_btn.setOnClickListener() {

            val name = register_name_et.text.toString()
            val userID = register_id_et.text.toString()
            val password = register_pw_et.text.toString()
            val verificationKey = register_code_et.text.toString()

            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    postRegister =
                        Repository().postRegister(name, userID, password, verificationKey)
                }
                when (postRegister) {
                    201 -> {
                        Toast.createToast(this@RegisterActivity, "회원가입 성공", R.drawable.ic_check)
                            .show()
                        finish()
                    }
                    403 -> {
                        Toast.createToast(this@RegisterActivity, "디넌 인증키 불일치", R.drawable.ic_redx)
                            .show()
                    }
                    409 -> {
                        Toast.createToast(this@RegisterActivity, "이미 있는 아이디", R.drawable.ic_redx)
                            .show()
                    }
                    else -> {
                        Toast.createToast(this@RegisterActivity, "오류", R.drawable.ic_redx).show()
                    }
                }
            }
        }

    }


    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (TextUtils.isEmpty(register_id_et.getText()) || TextUtils.isEmpty(register_pw_et.getText()) || TextUtils.isEmpty(
                    register_name_et.getText()
                ) || TextUtils.isEmpty(register_code_et.getText())
            ) {
                register_register_btn.setEnabled(false)
                register_register_btn.setTextColor(
                    ContextCompat.getColor(
                        this@RegisterActivity,
                        R.color.colorPrimary
                    )
                )
            } else {
                register_register_btn.setEnabled(true)
                register_register_btn.setTextColor(Color.parseColor("#FFFFFF"))
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


    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?
        ): Converter<ResponseBody, *>? {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter EmptyResponse()
                delegate.convert(it)
            }
        }

    }
}

class EmptyResponse {

}
