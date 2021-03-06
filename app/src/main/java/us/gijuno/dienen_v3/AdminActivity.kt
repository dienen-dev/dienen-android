package us.gijuno.dienen_v3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_admin.*
import org.apache.commons.lang3.StringUtils
import us.gijuno.dienen_v3.data.GetWarning

class AdminActivity : AppCompatActivity() {
    lateinit var adminAdapter: AdminAdapter
    val datas = mutableListOf<GetWarning>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        go_to_write_admin.setOnClickListener {
            startActivity(Intent(this, WriteAdminActivity::class.java))
            finish()
        }

        go_to_summary.setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }

        val fireStore = FirebaseFirestore.getInstance()
        val firestoreDB = fireStore.collection("warning").get().addOnSuccessListener {
            Log.d("AdminFirebase", "doc.data_size : ${it.documents.size}")

            for (i in 1 until (it.documents.size)) {
                Log.d("AdminFirebase","doc.$it : ${it.documents[i].data}")

                val num_name = "${it.documents[i].data?.get("num").toString()} ${it.documents[i].data?.get("name").toString()}"
                val content = it.documents[i].data?.get("content").toString()
                val times_num = StringUtils.countMatches(content, "\n") + 1
                val times = "누적 ${times_num}회"


                initRecycler(num_name, times, content, times_num)
            }
        }
    }

    private fun initRecycler(num_name: String, times: String, content: String, times_num: Int) {
        adminAdapter = AdminAdapter(this)
        admin_recycler.adapter = adminAdapter

        datas.apply {
            add(
                GetWarning(
                    num_name = num_name,
                    times = times,
                    content = content,
                    times_num = times_num
                )
            )

            adminAdapter.datas = datas
            adminAdapter.notifyDataSetChanged()
            warning_prograss.visibility = View.GONE
        }

    }

    class AdminAdapter(private val context: Context) :
        RecyclerView.Adapter<AdminAdapter.ViewHolder>() {

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
                when {
                    item.times_num!! >= 5 -> {
                        times.setTextColor(ContextCompat.getColor(context, R.color.warning))
                    }
                    item.times_num >= 3 -> {
                        times.setTextColor(ContextCompat.getColor(context, R.color.caution))
                    }
                    else -> {
                        times.setTextColor(ContextCompat.getColor(context, R.color.blackColor))
                    }
                }

            }
        }
    }
    class AdminViewModel : ViewModel() {

    }
}


