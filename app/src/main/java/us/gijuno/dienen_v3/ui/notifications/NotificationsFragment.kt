package us.gijuno.dienen_v3.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notification_recycler_item.view.*
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.data.Notice
import java.text.SimpleDateFormat
import java.util.*

class NotificationsFragment : Fragment() {

    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        viewAdapter = MyAdapter()

        recyclerView = root.findViewById<RecyclerView>(R.id.notification_recycler).apply {
            adapter = viewAdapter
            setHasFixedSize(true)
        }

        notificationsViewModel.notilistGetFailedEvent.observe(viewLifecycleOwner) {
            Log.d("NotificationFragment", "Server Err")
            //스낵바나 토스트로 알림
        }

        notificationsViewModel.notilist.observe(viewLifecycleOwner) {
            (viewAdapter as MyAdapter).setItem(it)
            (viewAdapter as MyAdapter).notifyDataSetChanged()
        }

        return root
    }

    class MyAdapter() :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        private var items: List<Notice> = arrayListOf()

        fun setItem(items: List<Notice>) {
            this.items = items.reversed()
        }

        class MyViewHolder(val noticeLayout: LinearLayout) : RecyclerView.ViewHolder(noticeLayout)

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MyAdapter.MyViewHolder {
            // create a new view
            val noticeLayout = LayoutInflater.from(parent.context)
                .inflate(R.layout.notification_recycler_item, parent, false) as LinearLayout
            // set the view's size, margins, paddings and layout parameters

            return MyViewHolder(noticeLayout)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.noticeLayout.apply {
                var noti_time: String = items[position].createdAt //2020-12-03T18:07:14.359Z
                var split_noti_time: List<String> = noti_time.split("T")
                var splited_date: String = split_noti_time[0] //2020-12-03
                var splited_time_not: String = split_noti_time[1] //18:07:14.359Z
                var split_date: List<String> = splited_date.split("-")
                var split_time: List<String> = splited_time_not.split(".")
                var split_time_not: String = split_time[0] //18:07:14
                var split_splited_time: List<String> = split_time_not.split(":")

                var splited_date_year: Int = split_date[0].toInt() //2020
                var splited_date_month: Int = split_date[1].toInt() //12
                var splited_date_day: Int = split_date[2].toInt() //03
                var split_time_hour: Int = split_splited_time[0].toInt()+9 //18
                var split_time_min: Int = split_splited_time[1].toInt() // 07

                var compare_time: Int

                if (split_time_min<10) {
                    compare_time = (split_time_hour.toString() + "0" + split_time_min.toString()).toInt()
                    Log.d("time check", compare_time.toString())
                } else {
                    compare_time = (split_time_hour.toString() + split_time_min.toString()).toInt()
                    Log.d("time check", compare_time.toString())
                }

                var oh: String

                if(compare_time>=2400) {
                    compare_time -= 2400
                }

                when(compare_time) {
                    in 0..800 -> oh = "아침"
                    in 801..1350 -> oh = "점심"
                    in 1351..2359 -> oh = "저녁"
                    else -> oh = ""
                }

                var compare_date: String

                Log.d("date", " splited_date_day : $splited_date_day / Calendar.Day_Of_Month : ${
                    SimpleDateFormat("dd").format(
                        Date()
                    )}")

                var compare_day: String = splited_date_day.toString()

                if (splited_date_day < 10) {
                    compare_day = "0" + splited_date_day.toString()
                }

                if (compare_day.toString() == SimpleDateFormat("dd").format(Date()).toString()) {
                    compare_date = "오늘 " + oh
                } else {
                    compare_date = splited_date_day.toString() + "일 " + oh
                }

                noti_when.text = compare_date
                noti_title.text = items[position].title
                noti_contents.text = items[position].context
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = items.size
    }
}