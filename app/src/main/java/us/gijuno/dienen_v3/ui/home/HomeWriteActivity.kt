package us.gijuno.dienen_v3.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_home_write.*
import us.gijuno.dienen_v3.R

class HomeWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_write)

        val klass = resources.getStringArray(R.array.klass)

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, klass)

        home_write_first_klass.adapter = spinnerAdapter

        home_write_first_klass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> {

                    }
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO()//각 반별 정보 저장
            }
        }


    }
}
