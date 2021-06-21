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

data class NoticeWrite(
    val title: String,
    val contents: String,
)

data class DimigoinLogin(
    val username: String,
    val password: String,
)

data class DimigoinAuth(
    val accessToken: String,
    val refreshToken: String,
)

data class StudnetsArray(
    val students: List<Students>,
)

data class Students(
    val _id: String,
    val idx: Int,
    val username: String,
    val name: String,
    val userType: String,
    val gender: String,
    val createdAt: String,
    val updatedAt: String,
    val klass: Int,
    val grade: Int,
    val number: Int,
    val serial: Int,
)

data class PostWarning(
    var num: String? = null,
    var name: String? = null,
    var content: String? = null,
)

data class GetWarning(
    val num_name: String? = null,
    val times: String? = null,
    val content: String? = null,
    val times_num: Int? = null,
)



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

    @POST("writenotice")
    @FormUrlEncoded
    fun postNoticeWrite(
        @Header("x-access-dienen") accessToken: String,
        @Field("title") title: String,
        @Field("context") contents: String
    ): Call<NoticeWrite>
}


interface Dimigoin {
    @GET("meal/date/{date}")
    fun getMenu(@Path("date") date: String): Call<Meal>

    @POST("auth")
    fun postDimigoinLogin(
        @Body dimigoinLogin: DimigoinLogin): Call<DimigoinAuth>

    @GET("user/student")
    fun getDimigoinStudent(
        @Header("Authorization") refreshToken: String
    ): Call<StudnetsArray>
}
