package us.gijuno.dienen_v3

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import us.gijuno.dienen_v3.databinding.ToastBinding

object Toast {
    fun createToast(context: Context, message: String, icon: Int): Toast {
        val inflater = LayoutInflater.from(context)
        val binding: ToastBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast, null, false)

        binding.toastText.text = message
        binding.toastIcon.setImageResource(icon)

        return Toast(context).apply {
            setGravity(Gravity.TOP or Gravity.CENTER, 0, 16.toPx())
            duration = Toast.LENGTH_LONG
            view = binding.root
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}