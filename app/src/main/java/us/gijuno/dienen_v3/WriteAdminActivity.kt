package us.gijuno.dienen_v3

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_write_admin.*
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import us.gijuno.dienen_v3.data.PostWarning
import us.gijuno.dienen_v3.data.Repository
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import java.util.HashMap

import android.os.AsyncTask
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy


class WriteAdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_admin)
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val fireStore = FirebaseFirestore.getInstance()
//
//        write_admin_num.addTextChangedListener(textWatcher)
//        write_admin_name.addTextChangedListener(textWatcher)
        write_admin_content.addTextChangedListener(textWatcher)

        write_admin_btn.setOnClickListener {
            //한번만 눌러주세요
            write_admin_btn.setEnabled(false)
            write_admin_btn.setTextColor(
                ContextCompat.getColor(
                    this@WriteAdminActivity,
                    R.color.colorPrimary
                )
            )
//
//            val num = write_admin_num.text.toString()
//            val name = write_admin_name.text.toString()
            val content = write_admin_content.text.toString()
            val currentDateTime = Calendar.getInstance().time
            val date_time =
                SimpleDateFormat("yyyy.MM.dd; HH:mm:ss", Locale.KOREA).format(currentDateTime)

            val warningUser = PostWarning()
//
//            warningUser.num = num
//            warningUser.name = name

            val firestoreDB =
                fireStore.collection("warning").document("${warningUser.num} ${warningUser.name}")
            val getFirestore = firestoreDB.get()
            getFirestore.addOnSuccessListener {
                val test2 = it["content"]
                Log.d("getFirestoreLister", test2.toString())

                when (it["content"]) {
                    "" -> {
                        warningUser.content = "$date_time - $content"
                    }
                    null -> {
                        warningUser.content = "$date_time - $content"
                    }
                    else -> {
                        warningUser.content = "${it["content"]}\n$date_time - $content"
                    }
                }
                val addFirestore = firestoreDB.set(warningUser)
                when (checkInternetConnection()) {
                    true -> {

                        addFirestore.addOnSuccessListener {
                            Log.d("firebase", "addOnSuccessListener")
                            Toast.createToast(this, "경고 추가 성공", R.drawable.ic_check).show()
                            startActivity(Intent(this, AdminActivity::class.java))
                            finish()
//                      AdminActivity.onResume()
                        }
                        addFirestore.addOnFailureListener {
                            Log.d("firebase", "addOnFailureListener")
                            Toast.createToast(this, "경고 추가 실패", R.drawable.ic_redx).show()
                        }
                    }

                    false -> {
                        Toast.createToast(
                            this,
                            "인터넷 연결 확인\n인터넷에 연결되면 자동으로 추가됩니다.",
                            R.drawable.ic_redx
                        ).show()
                        startActivity(Intent(this, AdminActivity::class.java))
                        finish()
                    }
                }
            }

        }

        Repository().postDimigoinLogin().accessToken


    }

    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//            if (TextUtils.isEmpty(write_admin_num.getText()) || TextUtils.isEmpty(write_admin_name.getText())) {
//                write_admin_btn.setEnabled(false)
//                write_admin_btn.setTextColor(
//                    ContextCompat.getColor(
//                        this@WriteAdminActivity,
//                        R.color.colorPrimary
//                    )
//                )
//            } else {
//                write_admin_btn.setEnabled(true)
//                write_admin_btn.setTextColor(Color.parseColor("#FFFFFF"))
//            }
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

    fun checkInternetConnection(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        if (activeNetwork != null)
            return true

        return false
    }


}
