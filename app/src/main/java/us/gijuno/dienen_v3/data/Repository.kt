package us.gijuno.dienen_v3.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.util.*


class Repository {
    private val _menu = MutableLiveData<Menu>()
    val menu: LiveData<Menu> = _menu
    val menuGetFailedEvent = SingleLiveEvent<Void>()
    suspend fun getMenu() {
        try {
            DimibobRequester.getMenu(SimpleDateFormat("yyyy-MM-dd").format(Date()).toString()).let {
                withContext(Dispatchers.Main) {
                    _menu.value = it
                }
            }
        } catch (e: IllegalArgumentException) {
            Log.e("Repository","Today get Menu : $e")
            withContext(Dispatchers.Main) {
                menuGetFailedEvent.call()
            }
        } catch (e: SocketTimeoutException) {
            Log.e("Repository","Today get Menu : $e")
            withContext(Dispatchers.Main) {
                menuGetFailedEvent.call()
            }
        }
    }

    suspend fun getTomMenu() {
        try {
            val cal_ins = Calendar.getInstance()
            cal_ins.add(Calendar.DATE, 1)
            DimibobRequester.getMenu(SimpleDateFormat("yyyy-MM-dd").format(cal_ins.time).toString()).let {
                withContext(Dispatchers.Main) {
                    _menu.value = it
                }
            }
        } catch (e: IllegalArgumentException) {
            Log.e("Repository","Tomorrow get Menu : $e")
            withContext(Dispatchers.Main) {
                menuGetFailedEvent.call()
            }
        } catch (e: SocketTimeoutException) {
            Log.e("Repository","Tomorrow get Menu : $e")
            withContext(Dispatchers.Main) {
                menuGetFailedEvent.call()
            }
        }

    }

    private val _notilist = MutableLiveData<List<Notice>>()
    val notilist: LiveData<List<Notice>> = _notilist
    val notilistGetFailedEvent = SingleLiveEvent<Void>()
    suspend fun getNoticeList() {
        try {
            DienenServiceRequester.getNoticeList().let {
                withContext(Dispatchers.Main) {
                    _notilist.value = it
                }
            }
        } catch (e: IllegalArgumentException) {
            Log.e("Repository", "Get Notice List : $e")
            withContext(Dispatchers.Main) {
                notilistGetFailedEvent.call()
            }
        } catch (e: SocketTimeoutException) {
            Log.e("Repository", "Get Notice List : $e")
            withContext(Dispatchers.Main) {
                notilistGetFailedEvent.call()
            }
        }
    }
}