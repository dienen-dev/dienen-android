package us.gijuno.dienen_v3.data

class LoggedIn {
    val ACCESS_TOKEN: String = SharedPreference.prefs.getString(Keys.ACCESS_TOKEN.name, "")

    fun isLoggedIn(ACCESS_TOKEN: String): Boolean = ACCESS_TOKEN.isNotEmpty()
}