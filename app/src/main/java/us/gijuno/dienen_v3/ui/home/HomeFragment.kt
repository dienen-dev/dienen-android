package us.gijuno.dienen_v3.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_home.*
import us.gijuno.dienen_v3.R
import java.util.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.menuGetFailedEvent.observe(viewLifecycleOwner) {
            menu_mola.text = "급식 정보가 없습니다"
            menu_idk.text = "급식 정보가 없습니다"
            //TODO 새로고침하세요 스낵바
            Log.d("HomeFrag", "menu get failed event called")
        }

        homeViewModel.menu.observe(viewLifecycleOwner) { menu ->
            val min = Calendar.getInstance().get(Calendar.MINUTE)
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            val compareTime = "%d%02d".format(hour, min).toInt() % 2400

            when (compareTime) {
                in 0..800 -> {
                    menu_mola.text = "아침"
                    menu_idk.text = menu.breakfast
                }
                in 801..1350 -> {
                    menu_mola.text = "점심"
                    menu_idk.text = menu.lunch
                }
                in 1351..1950 -> {
                    menu_mola.text = "저녁"
                    menu_idk.text = menu.dinner
                }
                else -> {
                    menu_mola.text = "아침"
                    menu_idk.text = menu.breakfast
                }
            }
        }



        return root
    }
}