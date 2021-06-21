package us.gijuno.dienen_v3.data

import android.util.Log
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import us.gijuno.dienen_v3.EmptyResponse
import java.lang.reflect.Type

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
        return Converter<ResponseBody, Any> {
            if (it.contentLength() == 0L) return@Converter EmptyResponse()
            delegate.convert(it)
        }
    }
}

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
        return service.getNoticeRecent().execute().body() ?: Notice(
            "err",
            "err",
            "err",
            "err",
            "err"
        )

    }

    fun postRegister(name: String, userID: String, password: String, verificationKey: String): Int {
        val registerResponseCode: Int =
            service.postRegister(name, userID, password, verificationKey).execute().code()
        Log.d("Register Response", registerResponseCode.toString())
        return registerResponseCode
    }

    fun postLogin(userID: String, password: String): Int {
        val loginResponse = service.postLogin(userID, password).execute()
        val accessToken: String =
            loginResponse.body()?.accessToken ?: throw Exception("Failed Get ACCESS_TOKEN")
        SharedPreference.prefs.setString(Keys.ACCESS_TOKEN.name, accessToken)
        Log.d("Login Response", loginResponse.code().toString())
        return loginResponse.code()
    }

    fun postNoticeWrite(accessToken: String, title: String, contents: String): Int {
        val noticeWriteResponse =
            service.postNoticeWrite(accessToken, title, contents).execute().code()
        Log.d("NoticeWrite Response", noticeWriteResponse.toString())
        return noticeWriteResponse
    }
}


object DimiRequester {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.dimigo.in/")
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val dimigoinService = retrofit.create(Dimigoin::class.java)

    fun getMenu(date: String): Meal {
        return dimigoinService.getMenu(date).execute().body()
            .also { Log.d("DienenServiceRequester", "Menu : ${it.toString()}") }
            ?: throw IllegalArgumentException()
    }

    fun postDimigoinLogin(): DimigoinAuth {
        val postDimigoinLogin = dimigoinService.postDimigoinLogin(DimigoinLogin("dindevdin", "dindevdin")).execute()
//        Log.d("DimigoinLogin-token", postDimigoinLogin.body().toString())
        Log.d("DimigoinLogin-code", postDimigoinLogin.code().toString())
        getDimigoinStudents(postDimigoinLogin.body()?.accessToken.toString())
        return postDimigoinLogin.body()?: throw Exception()
    }

    fun getDimigoinStudents(dimigoinToken: String): StudnetsArray {
        val getDimigoinStudents = dimigoinService.getDimigoinStudent("Bearer $dimigoinToken").execute().body()?: throw Exception()
        Log.d("getDimigoinStu", getDimigoinStudents.toString())
        Log.d("DimigoinStu-code", dimigoinService.getDimigoinStudent("Bearer $dimigoinToken").execute().code().toString())
        return getDimigoinStudents
    }

}

