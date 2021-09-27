package us.gijuno.dienen_v3

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin.*
import kotlinx.android.synthetic.main.activity_summary.*
import org.apache.commons.lang3.StringUtils
import us.gijuno.dienen_v3.R
import us.gijuno.dienen_v3.data.GetWarning

class SummaryActivity : AppCompatActivity() {
    lateinit var summaryAdapter: SummaryAdapter
    val datas = mutableListOf<GetWarning>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection("warning").get().addOnSuccessListener {
            Log.d("AdminFirebase", "doc.data_size : ${it.documents.size}")

            for (i in 1 until (it.documents.size)) {
                Log.d("AdminFirebase", "doc.$it : ${it.documents[i].data}")

                val num_name = "${
                    it.documents[i].data?.get("num").toString()
                } ${it.documents[i].data?.get("name").toString()}"
                val content = it.documents[i].data?.get("content").toString()
                val times_num = StringUtils.countMatches(content, "\n") + 1
                val times = "누적 ${times_num}회"


                initRecycler(num_name, times, content, times_num)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler(num_name: String, times: String, content: String, times_num: Int) {
        summaryAdapter = SummaryAdapter(this)
        summary_recyclerview.adapter = summaryAdapter
        datas.apply {
            if(times_num>=3) {
                add(
                    GetWarning(
                        num_name = num_name,
                        times = times,
                        content = content,
                        times_num = times_num
                    )
                )
            }
        }
        summaryAdapter.datas = datas
        summaryAdapter.notifyDataSetChanged()
    }


}

class SummaryAdapter(private val context: Context) :
    RecyclerView.Adapter<SummaryAdapter.ViewHolder>() {

    var datas = mutableListOf<GetWarning>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.admin_recycler_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val num_name: TextView = itemView.findViewById(R.id.warning_name)
        private val times: TextView = itemView.findViewById(R.id.warning_times)
        private val content: TextView = itemView.findViewById(R.id.warning_content)

        fun bind(item: GetWarning) {
            num_name.text = item.num_name
            times.text = item.times
            content.text = item.content
            if (item.times_num!! >= 5) {
                times.setTextColor(ContextCompat.getColor(context, R.color.warning))
            } else if (item.times_num >= 3) {
                times.setTextColor(ContextCompat.getColor(context, R.color.caution))
            }

        }
    }
}
