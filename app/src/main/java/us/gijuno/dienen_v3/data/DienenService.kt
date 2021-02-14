package us.gijuno.dienen_v3.data

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

data class Notice (
    val _id: String,
    val title: String,
    val context: String,
    val writer: String,
    val createdAt: String
)

data class Register (
    val name: String,
    val userID: String,
    val password: String,
    val verificationKey: String,
)

data class Login (
    val accessToken: String,
)

data class Meal (val meal: Menu) {
    data class Menu (
        val breakfast: List<String>,
        val lunch: List<String>,
        val dinner: List<String>,
    )
}



interface DienenService {

    @GET("noticelist")
    fun getNoticeList(): Call<List<Notice>>

    @GET("notice/recent")
    fun getNoticeRecent(): Call<Notice>

    @POST("login")
    @FormUrlEncoded
    fun postLogin(
        @Field("userID") userID: String,
        @Field("password") password: String
    ): Call<Login>

    @POST("register")
    @FormUrlEncoded
    fun postRegister(
        @Field("name") name: String,
        @Field("userID") userID: String,
        @Field("password") password: String,
        @Field("verificationKey") verificationKey: String
    ): Call<Register>
}

interface Dimibob {
    @GET("meal/date/{date}")
    fun getMenu(@Path("date") date: String): Call<Meal>
}

