package us.gijuno.dienen_v3.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import us.gijuno.dienen_v3.data.Repository

class NotificationsViewModel : ViewModel() {
    val repository = Repository()
    val notilist = repository.notilist.also {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNoticeList()
        }
    }

    val notilistGetFailedEvent = repository.notilistGetFailedEvent

}