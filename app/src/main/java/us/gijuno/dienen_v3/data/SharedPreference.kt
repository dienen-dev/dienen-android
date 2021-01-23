package us.gijuno.dienen_v3.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SharedPreference : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }
}

// 데이터 저장
// SharedPreference.prefs.setString("email", "abcd@gmail.com")
// 데이터 조회
// SharedPreference.prefs.getString("email", "no email")