package us.gijuno.dienen_v3.ui.setting

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_home_write.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import us.gijuno.dienen_v3.*
import us.gijuno.dienen_v3.data.Keys
import us.gijuno.dienen_v3.data.LoggedIn
import us.gijuno.dienen_v3.data.SharedPreference
import us.gijuno.dienen_v3.ui.home.SettingViewModel

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        val klass = resources.getStringArray(R.array.klass)

        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, klass)

        root.settingSpinner.adapter = spinnerAdapter

        root.settingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val myGrade: String = getGrade(position)
                val myKlass: String = getKlass(position)

                SharedPreference.prefs.setString("myIndex", position.toString())
                SharedPreference.prefs.setString("myGrade", myGrade)
                SharedPreference.prefs.setString("myKlass", myKlass)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO()//각 반별 정보 저장
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()

        val myIndex: Int = SharedPreference.prefs.getString("myIndex", "0").toInt()
        settingSpinner.setSelection(myIndex)


        if(LoggedIn().isLoggedIn(LoggedIn().ACCESS_TOKEN)) {
            identify_dienen.text = "디넌으로 이미 인증되었습니다."
            identify_image.setImageResource(R.drawable.ic_check)
            verify_dienen_btn.setOnClickListener {
                logoutDialog()

            }
        } else {
            identify_dienen.text = "디넌이신가요? 로그인 해주세요."
            identify_image.setImageResource(0)
            verify_dienen_btn.setOnClickListener {
                startActivity(Intent(context, LoginActivity::class.java)) // Log In
            }
        }
    }

    fun logoutDialog() {
        val getDialog = Dialog(this.requireContext())

        getDialog.setOnOKClickedListener {
            SharedPreference.prefs.setString(Keys.ACCESS_TOKEN.name, "") // Log Out
            onResume()
            Toast.createToast(this.requireActivity(), "로그아웃 되었습니다.", R.drawable.ic_check).show()
        }
        getDialog.start("로그아웃 하시겠습니까?")
    }

    fun getGrade(index: Int): String {
        val myGrade: String = ((index / 6) + 1).toString()
        return myGrade
    }

    fun getKlass(index: Int): String {
        val myKlass: String = ((index % 6) + 1).toString()
        return myKlass
    }

}