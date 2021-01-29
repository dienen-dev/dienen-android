package us.gijuno.dienen_v3.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import us.gijuno.dienen_v3.data.Repository

class NotificationsViewModel : ViewModel() {
    val repository = Repository()
    val notilist = repository.notilist.also {
        GlobalScope.launch {
            repository.getNoticeList()
        }
    }

    val notilistGetFailedEvent = repository.notilistGetFailedEvent

}