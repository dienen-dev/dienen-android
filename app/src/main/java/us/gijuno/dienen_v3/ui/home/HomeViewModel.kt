package us.gijuno.dienen_v3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import us.gijuno.dienen_v3.data.Repository
import java.util.*

class HomeViewModel : ViewModel() {
    val repository = Repository()
    val menu = repository.menu.also {
        GlobalScope.launch {
            val min = Calendar.getInstance().get(Calendar.MINUTE)
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            val compareTime = "%d%02d".format(hour, min).toInt() % 2400

            when(compareTime) {
                in 0..1950 -> {
                    repository.getMenu()
                }
                else -> {
                    repository.getTomMenu()
                }
            }
        }
    }

    val menuGetFailedEvent = repository.menuGetFailedEvent

}