package com.bigdreamcoders.creseasistencia.services.NetworkService

import com.bigdreamcoders.creseasistencia.services.NetworkService.models.login.Login
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.LoginAdapter
import com.bigdreamcoders.creseasistencia.services.NetworkService.models.login.LoginResponse
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface RequestService {

    @POST("user/login")
    fun login(@Body log: Login): Observable<Response<LoginResponse>>

    companion object {
        val gsonLogin:GsonBuilder=GsonBuilder().registerTypeAdapter(Login::class.java, LoginAdapter())
        fun createLogin(): RequestService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gsonLogin.create()))
                .baseUrl("https://crese-asistencia.herokuapp.com/API/v1/")
                .build()
            return retrofit.create(RequestService::class.java)
        }
    }

}