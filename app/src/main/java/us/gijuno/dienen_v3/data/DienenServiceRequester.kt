package us.gijuno.dienen_v3.data

import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DienenServiceRequester {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://docker.hanukoon.com:9473/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(DienenService::class.java)

    fun getNoticeList(): List<Notice> {
        return service.getNoticeList().execute().body() ?: listOf()
    }

    fun postRegister(name: String, userID: String, password: String, verificationKey: String): Int {
        return service.postRegister(name,userID,password,verificationKey).execute().code()
    }

}

object DimibobRequester {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dev-api.dimigo.in/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(Dimibob::class.java)

    fun getMenu(date: String): Menu {
        return service.getMenu(date).execute().body().also { Log.d("DienenServiceRequester", "Menu : ${it.toString()}") } ?: throw IllegalArgumentException()
    }
}
