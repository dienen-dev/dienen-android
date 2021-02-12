package us.gijuno.dienen_v3.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class Repository {
    private val _meal = MutableLiveData<Meal>()
    val menu: LiveData<Meal> = _meal
    val menuGetFailedEvent = SingleLiveEvent<Void>()
    suspend fun getMenu() {
        try {
            DimibobRequester.getMenu(SimpleDateFormat("yyyy-MM-dd").format(Date()).toString()).let {
                withContext(Dispatchers.Main) {
                    _meal.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
                    _meal.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
                    Log.d("Notice List", it.toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Repository","Get Notice List : $e")
            withContext(Dispatchers.Main) {
                notilistGetFailedEvent.call()
            }
        }
    }

    private val _notirecent = MutableLiveData<Notice>()
    val notirecent: LiveData<Notice> = _notirecent
    val notirecentGetFailedEvent = SingleLiveEvent<Void>()
    suspend fun getNoticeRecent() {
        try {
            DienenServiceRequester.getNoticeRecent().let {
                withContext(Dispatchers.Main) {
                    _notirecent.value = it
                    Log.d("Repo Notice Recent", it.toString())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Repository", "Get Notice Recent : $e")
            withContext(Dispatchers.Main) {
                notirecentGetFailedEvent.call()
            }
        }
    }

    suspend fun postRegister(name: String, userID: String, password: String, verificationkey: String) {
        try {
            DienenServiceRequester.postRegister(name, userID, password, verificationkey).let {
                withContext(Dispatchers.Main) {
                    Log.d("PostRegister","GG")
                }
            }
        } catch (e: Exception) {
            Log.d("PostRegister","XX")
            e.printStackTrace()
        }
    }

}