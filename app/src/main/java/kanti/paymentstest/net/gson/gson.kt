package kanti.paymentstest.net.gson

import com.google.gson.GsonBuilder
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentDAO
import kanti.paymentstest.data.model.payments.datasource.remote.PaymentsDAODeserializer
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

fun paymentsGsonConverterFactory(): Converter.Factory {
	val builder = GsonBuilder()
	builder.registerTypeAdapter(PaymentDAO::class.java, PaymentsDAODeserializer)
	val gson = builder.create()
	return GsonConverterFactory.create(gson)
}