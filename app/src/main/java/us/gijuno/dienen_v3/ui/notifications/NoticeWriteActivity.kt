package us.gijuno.dienen_v3.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_notice_write.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import us.gijuno.dienen_v3.Dialog
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.data.Keys
import us.gijuno.dienen_v3.data.Repository
import us.gijuno.dienen_v3.data.SharedPreference


class NoticeWriteActivity : AppCompatActivity() {
    private var postNoticeWrite: Int = 500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_write)

        notice_write_title_et.addTextChangedListener(textWatcher)
        notice_write_context_et.addTextChangedListener(textWatcher)

        write_chip.setOnClickListener {
            notice_write_title_et.setText("")
            notice_write_context_et.setText("")
        }

        late_chip.setOnClickListener {
            notice_write_title_et.setText("급식시간 지연 안내")
            notice_write_context_et.setText("로 인하여 급식시간이 분 지연됨을 알려드립니다.")
        }

        change_chip.setOnClickListener {
            notice_write_title_et.setText("급식순서 변경 안내")
            notice_write_context_et.setText("로 인하여 급식순서가 아래와 같이 변경됨을 알려드립니다.")
        }

        menu_chip.setOnClickListener {
            notice_write_title_et.setText("급식매뉴 변경 안내")
            notice_write_context_et.setText("로 인하여 급식 매뉴가 아래와 같이 변경됨을 알려드립니다.")
        }

        notice_write_x_btn.setOnClickListener {
            finish()
        }

    }

    var textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (TextUtils.isEmpty(notice_write_title_et.getText()) || TextUtils.isEmpty(notice_write_context_et.getText())) {
                notice_write_btn.setEnabled(false)
                notice_write_btn.setTextColor(ContextCompat.getColor(this@NoticeWriteActivity, R.color.borderColor2))

            } else {
                notice_write_btn.setEnabled(true)
                notice_write_btn.setTextColor(ContextCompat.getColor(this@NoticeWriteActivity, R.color.colorPrimary))
                notice_write_btn.setOnClickListener {
                    //TODO 파베 노티스 포스트
                    noticeWrite()
                    //한번만 눌러주세요
                    notice_write_btn.setEnabled(false)
                    notice_write_btn.setTextColor(ContextCompat.getColor(this@NoticeWriteActivity, R.color.borderColor2))
                }
                notice_write_x_btn.setOnClickListener {
                    val getDialog = Dialog(this@NoticeWriteActivity)
                    getDialog.setOnOKClickedListener {
                        finish()
                    }
                    getDialog.start("내용이 저장되지 않습니다.\n나가시겠습니까?")
                }
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

    fun noticeWrite() {
        val accessToken = SharedPreference.prefs.getString(Keys.ACCESS_TOKEN.name, "")
        val title: String = notice_write_title_et.text.toString()
        val contents: String = notice_write_context_et.text.toString()

        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                postNoticeWrite = Repository().postNoticeWrite(accessToken, title, contents)
            }
            when(postNoticeWrite) {
                200 -> {
                    us.gijuno.dienen_v3.Toast.createToast(this@NoticeWriteActivity, "공지 작성 성공", R.drawable.ic_check).show()
                    finish()
                    NotificationsFragment().onResume()
                }
                500 -> {
//                    us.gijuno.dienen_v3.Toast.createToast(this@NoticeWriteActivity, "공지 작성 실패", R.drawable.ic_redx).show()
                    us.gijuno.dienen_v3.Toast.createToast(this@NoticeWriteActivity, "공지 작성 성공", R.drawable.ic_check).show()

                }
            }
        }
    }


}
