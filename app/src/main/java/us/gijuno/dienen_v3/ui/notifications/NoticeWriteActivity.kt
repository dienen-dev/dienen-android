package us.gijuno.dienen_v3.ui.notifications

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_notice_write.*
import us.gijuno.dienen_v3.R


class NoticeWriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_write)

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

    }
}
