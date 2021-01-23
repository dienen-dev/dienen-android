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

data class Menu (
    val breakfast: String,
    val lunch: String,
    val dinner: String,
)

interface DienenService {

    @GET("noticelist")
    fun getNoticeList(): Call<List<Notice>>

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
    @GET("dimibobs/{date}")
    fun getMenu(@Path("date") date: String): Call<Menu>
}

