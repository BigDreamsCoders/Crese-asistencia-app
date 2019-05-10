package com.bigdreamcoders.creseasistencia.services.networkService.models.faq

data class FaqResponse(
    val message: String
)

data class FaqBody(
    val email:String,
    val name:String,
    val content: String
)