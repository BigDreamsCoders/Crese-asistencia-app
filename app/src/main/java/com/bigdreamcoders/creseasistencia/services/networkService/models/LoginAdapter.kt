package com.bigdreamcoders.creseasistencia.services.networkService.models

import com.bigdreamcoders.creseasistencia.services.networkService.models.login.Login
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class LoginAdapter : TypeAdapter<Login>() {
    override fun write(out: JsonWriter?, value: Login?) {
        out?.beginObject()
        if (!value?.email.isNullOrEmpty()){
            out?.name("email")?.value(value?.email)
        }else{
            out?.name("account")?.value(value?.account)
        }
        out?.name("password")?.value(value?.password)
        out?.endObject()
    }

    override fun read(`in`: JsonReader?): Login {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}