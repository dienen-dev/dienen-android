package us.gijuno.dienen_v3.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*
import us.gijuno.dienen_v3.LoginActivity
import us.gijuno.dienen_v3.MainActivity
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.ui.home.SettingViewModel

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        root.verify_dienen_btn.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }

        return root
    }
}