package us.gijuno.dienen_v3.data

import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        var registerResponseCode: String = service.postRegister(name,userID,password,verificationKey).execute().code().toString()
        Log.d("Requester", registerResponseCode)
        if(registerResponseCode == "200") {
            //TODO Success Alert
        } else if (registerResponseCode == "403") {
            //TODO VerificationKey Dismatch Alert
        } else if (registerResponseCode == "409") {
            //TODO Used UserID
        }
        return service.postRegister(name,userID,password,verificationKey).execute().code()
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
