package us.gijuno.dienen_v3.data

import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.gijuno.dienen_v3.EmptyResponse
import java.lang.Exception
import kotlin.Exception as Exception1

object DienenServiceRequester {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dienen-backend-waxwg4t74q-uc.a.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(DienenService::class.java)

    fun getNoticeList(): List<Notice> {
        return service.getNoticeList().execute().body() ?: listOf()
    }

    fun getNoticeRecent(): Notice {
        return service.getNoticeRecent().execute().body() ?: Notice("err","err","err","err","err")
    }

    fun postRegister(name: String, userID: String, password: String, verificationKey: String): Int {
        val registerResponseCode: Int = service.postRegister(name,userID,password,verificationKey).execute().code()
        Log.d("Register Response", registerResponseCode.toString())
        return registerResponseCode
    }

    fun postLogin(userID: String, password: String): Int {
        val loginResponse = service.postLogin(userID, password).execute()
        val accessToken: String = loginResponse.body()?.accessToken ?: throw Exception("Failed Get ACCESS_TOKEN") //TODO 다시 로그인 하세요 Alert 추가
        SharedPreference.prefs.setString(Keys.ACCESS_TOKEN.name, accessToken)
        Log.d("Login Response", loginResponse.code().toString())
        return loginResponse.code()
    }
}

object DimibobRequester {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.dimigo.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(Dimibob::class.java)

    fun getMenu(date: String): Meal {
        return service.getMenu(date).execute().body().also { Log.d("DienenServiceRequester", "Menu : ${it.toString()}") } ?: throw IllegalArgumentException()
    }
}
