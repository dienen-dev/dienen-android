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
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import us.gijuno.dienen_v3.data.Repository
import java.lang.reflect.Type


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        register_id_et.addTextChangedListener(textWatcher)
        register_pw_et.addTextChangedListener(textWatcher)
        register_name_et.addTextChangedListener(textWatcher)
        register_code_et.addTextChangedListener(textWatcher)

        val userID = register_id_et.text.toString()
        val name = register_name_et.text.toString()
        val password = register_id_et.text.toString()



        register_register_btn.setOnClickListener() {

            val name = register_name_et.text.toString()
            val userID = register_id_et.text.toString()
            val password = register_pw_et.text.toString()
            val verificationKey = register_code_et.text.toString()

            //TODO 레트로핏 작성

            GlobalScope.launch {
                Repository().postRegister(name,userID,password,verificationKey)
            }


           startActivity(Intent(this, MainActivity::class.java))
        }

    }


    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (TextUtils.isEmpty(register_id_et.getText()) || TextUtils.isEmpty(register_pw_et.getText()) || TextUtils.isEmpty(register_name_et.getText()) || TextUtils.isEmpty(register_code_et.getText())) {
                register_register_btn.setEnabled(false)
                register_register_btn.setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.colorPrimary))
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
        override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
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
