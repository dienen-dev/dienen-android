package us.gijuno.dienen_v3.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import kotlinx.android.synthetic.main.notification_recycler_item.view.*
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.Toast
import us.gijuno.dienen_v3.data.LoggedIn
import us.gijuno.dienen_v3.data.Notice
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
            Toast.createToast(this.requireContext(), "공지 불러오기 실패", R.drawable.ic_redx).show()
        }

        notificationsViewModel.notilist.observe(viewLifecycleOwner) {
            (viewAdapter as MyAdapter).setItem(it)
            (viewAdapter as MyAdapter).notifyDataSetChanged()
        }

        root.go_to_write_notice_btn.setOnClickListener {
            startActivity(Intent(context, NoticeWriteActivity::class.java))
        }


        return root
    }

    override fun onResume() {
        super.onResume()

        when {
            LoggedIn().isLoggedIn(LoggedIn().ACCESS_TOKEN) -> {

            }
            else -> {
                go_to_write_notice_btn.visibility = View.GONE
            }
        }
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
        ): MyViewHolder {
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
                val notifyCreatedAt: String = items[position].createdAt //2020-12-03T18:07:14.359Z

                val (_, _, date, hour, minute) =
                    """(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):\d{2}.\d{3}Z""".toRegex()
                        .matchEntire(notifyCreatedAt)?.destructured?.let { (year, month, date, hour, minute) ->
                            Component5(
                                year.toInt(),
                                month.toInt(),
                                date.toInt(),
                                hour.toInt(),
                                minute.toInt(),
                            )
                        }
                        ?: throw IllegalStateException("notifyCreatedAt not following ISO time format: $notifyCreatedAt")

                val mealTime: String =
                    when (val time = "%d%02d".format(hour, minute).toInt() % 2400 + 900) {
                        in 0..800 -> "아침"
                        in 801..1350 -> "점심"
                        in 1351..2359 -> "저녁"
                        else -> throw IllegalStateException("Time must be between 0 and 2359: given $time")
                    }

                val dateToday = Calendar.getInstance().get(Calendar.DATE)

                val formattedDate = if (dateToday == date) "오늘 $mealTime" else "${date}일 $mealTime"

                noti_when.text = formattedDate
                noti_title.text = items[position].title
                noti_contents.text = items[position].context
            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = items.size
    }
}

data class Component5<A, B, C, D, E>(val a: A, val b: B, val c: C, val d: D, val e: E)