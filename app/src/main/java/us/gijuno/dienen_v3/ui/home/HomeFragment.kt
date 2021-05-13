package us.gijuno.dienen_v3.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.data.Keys
import us.gijuno.dienen_v3.data.SharedPreference
import us.gijuno.dienen_v3.ui.notifications.NoticeWriteActivity
import java.util.*

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        getDatas()
        homeViewModel.menuGetFailedEvent.observe(viewLifecycleOwner) {
            menu_mola.text = "급식 정보가 없습니다"
            menu_idk.text = "급식 정보가 없습니다"
            Log.d("HomeFrag", "menu get failed event called")
        }

//        root.home_refresh_layout.setOnRefreshListener {
//            getDatas()
//            Handler().postDelayed({
//                root.home_refresh_layout.isRefreshing = false
//            }, 1000)
//        }


        root.order_layout.setOnClickListener {
            startActivity(Intent(context, MealOrderActivity::class.java))
        }

        val klassArray = resources.getStringArray(R.array.klass)
        val myIndex = SharedPreference.prefs.getString("myIndex", "0").toInt()

        val myklass = klassArray.get(myIndex)
        root.my_grade_class.text = myklass

        Log.d("asdf", Keys.ACCESS_TOKEN.toString())


        return root
    }

    private fun getDatas() {
        homeViewModel.menu.observe(viewLifecycleOwner) { menu ->
            val min = Calendar.getInstance().get(Calendar.MINUTE)
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            val compareTime = "%d%02d".format(hour, min).toInt() % 2400

            when (compareTime) {
                in 0..800 -> {
                    menu_mola.text = "아침"
                    menu_idk.text = menu.meal.breakfast.joinToString()
                }
                in 801..1350 -> {
                    menu_mola.text = "점심"
                    menu_idk.text = menu.meal.lunch.joinToString()
                }
                in 1351..1950 -> {
                    menu_mola.text = "저녁"
                    menu_idk.text = menu.meal.dinner.joinToString()
                }
                else -> {
                    menu_mola.text = "아침"
                    menu_idk.text = menu.meal.breakfast.joinToString()
                }
            }
        }

        homeViewModel.notirecent.observe(viewLifecycleOwner) { recent ->
            noti_mola.text = recent.title
            noti_idk.text = recent.context
        }

        homeViewModel.notirecentGetFailedEvent.observe(viewLifecycleOwner) {
            noti_mola.text = "서버 펑 .."
            noti_idk.text = "서버 펑 .."
            Log.d("HomeFrag", "notice recent get failed event called")
        }


    }
}