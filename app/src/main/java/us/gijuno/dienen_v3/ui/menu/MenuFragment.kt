package us.gijuno.dienen_v3.ui.menu

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.data.Meal
import java.text.SimpleDateFormat
import java.util.*

class MenuFragment : Fragment() {

    private val menuViewModel: MenuViewModel by viewModels()

    val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    val min = Calendar.getInstance().get(Calendar.MINUTE)
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val compareTime = "%d%02d".format(hour, min).toInt() % 2400

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_menu, container, false)
        getDatas()

        menuViewModel.menuGetFailedEvent.observe(viewLifecycleOwner) {
            when (compareTime) {
                in 0..1950 -> {
                    showYoil()
                } else -> {
                showTomYoil()
                }
            }
            menu_breakfast.text = "급식 정보가 없습니다"
            menu_lunch.text = "급식 정보가 없습니다"
            menu_dinner.text = "급식 정보가 없습니다"
            //TODO 새로고침하세요 스낵바
            Log.d("HomeFrag", "menu get failed event called")
        }

//        root.menu_refresh_layout.setOnRefreshListener {
//            getDatas()
//            Handler().postDelayed({
//                root.menu_refresh_layout.isRefreshing = false
//            }, 1000)
//        }

        return root
    }

    private fun getDatas() {
        menuViewModel.menu.observe(viewLifecycleOwner) { meal ->
            when (compareTime) {
                in 0..800 -> {
                    showTodayMenu(meal)
                    showYoil()
                    menu_ahchim_layout.setBackgroundResource(R.drawable.round_layout_primary)
                    menu_ahchim.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    menu_breakfast.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                in 801..1350 -> {
                    showTodayMenu(meal)
                    showYoil()
                    menu_jeomshim_layout.setBackgroundResource(R.drawable.round_layout_primary)
                    menu_jeomshim.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    menu_lunch.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                in 1351..1950 -> {
                    showTodayMenu(meal)
                    showYoil()
                    menu_jeonyeonk_layout.setBackgroundResource(R.drawable.round_layout_primary)
                    menu_jeonyeonk.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    menu_dinner.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }
                else -> {
                    showTomYoil()
                    showTodayMenu(meal)
                }
            }
        }
    }

    fun showTodayMenu(meal: Meal) {
        menu_breakfast.text = meal.meal.breakfast.joinToString()
        menu_lunch.text = meal.meal.lunch.joinToString()
        menu_dinner.text = meal.meal.dinner.joinToString()

    }

    fun showYoil() {
        val yoil = "일월화수목금토"[dayOfWeek-1]
        menu_today_date.text = SimpleDateFormat("yyyy-MM-dd").format(Date()) + " ${yoil}요일"
    }

    fun showTomYoil() {
        today_s_meal.text = "내일의 급식"
        val cal_ins = Calendar.getInstance()
        cal_ins.add(Calendar.DATE, 1)
        val yoil = "일월화수목금토"[dayOfWeek%7]
        menu_today_date.text = SimpleDateFormat("yyyy-MM-dd").format(cal_ins.time) + " ${yoil}요일"
    }


}