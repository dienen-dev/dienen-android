package us.gijuno.dienen_v3

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView

class Dialog(context : Context) {
    private val dialog = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lblDesc : TextView
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var listener : MyDialogOKClickedListener
    private var response : Boolean = false

    fun start(content : String) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dialog.setContentView(R.layout.dialog)     //다이얼로그에 사용할 xml 파일을 불러옴
        dialog.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함


        lblDesc = dialog.findViewById(R.id.content)
        lblDesc.text = content

        btnOK = dialog.findViewById(R.id.ok)
        btnOK.setOnClickListener {
            listener.onOKClicked(true)
            dialog.dismiss()
        }

        btnCancel = dialog.findViewById(R.id.cancel)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun setOnOKClickedListener(listener: (Boolean) -> Unit) {
        this.listener = object: MyDialogOKClickedListener {
            override fun onOKClicked(response: Boolean) {
                listener(response)
            }

        }
    }


    interface MyDialogOKClickedListener {
        fun onOKClicked(response : Boolean)
    }

}
