package com.bigdreamcoders.creseasistencia.services.networkService

import com.bigdreamcoders.creseasistencia.services.networkService.models.LoginAdapter
import com.bigdreamcoders.creseasistencia.services.networkService.models.faq.FaqResponse
import com.bigdreamcoders.creseasistencia.services.networkService.models.forgot.ForgotResponse
import com.bigdreamcoders.creseasistencia.services.networkService.models.login.Login
import com.bigdreamcoders.creseasistencia.services.networkService.models.login.LoginResponse
import com.bigdreamcoders.creseasistencia.services.networkService.models.manuals.ManualsResponse
import com.bigdreamcoders.creseasistencia.services.networkService.models.register.Register
import com.bigdreamcoders.creseasistencia.services.networkService.models.videos.VideosResponse
import com.bigdreamcoders.creseasistencia.utils.Constants
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RequestService {

    @POST("user/forgot-password")
    fun forgot(@Query(Constants.REQUEST_QUERY_EMAIL) email:String): Observable<Response<ForgotResponse>>

    @POST("user/login")
    fun login(@Body log: Login): Observable<Response<LoginResponse>>

    @POST("user")
    fun register(@Body register: Register): Observable<Response<Any>>

    @GET("manual")
    fun fetchManuals(
        @Header("Authorization") auth: String,
        @Query(Constants.REQUEST_QUERY_SEARCH) search: String,
        @Query(Constants.REQUEST_QUERY_CATEGORY) category: String
    ): Observable<Response<ManualsResponse>>

    @GET("video")
    fun fetchVideos(
        @Header("Authorization") auth: String,
        @Query(Constants.REQUEST_QUERY_SEARCH) search: String,
        @Query(Constants.REQUEST_QUERY_CATEGORY) category: String
    ): Observable<Response<VideosResponse>>

    @GET("faq")
    fun fetchFaq(
        @Header("Authorization") auth:String
    ):Observable<Response<FaqResponse>>

    companion object {
        private val gsonLogin: GsonBuilder =
            GsonBuilder().registerTypeAdapter(Login::class.java, LoginAdapter())

        fun createLogin(): RequestService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonLogin.create()))
                .baseUrl("https://crese-asistencia.herokuapp.com/API/v1/")
                .build()
            return retrofit.create(RequestService::class.java)
        }

        fun create(): RequestService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://crese-asistencia.herokuapp.com/API/v1/")
                .build()
            return retrofit.create(RequestService::class.java)
        }
    }

}