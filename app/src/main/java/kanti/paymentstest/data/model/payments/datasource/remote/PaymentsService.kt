package kanti.paymentstest.data.model.payments.datasource.remote

import kanti.paymentstest.data.model.common.MetaArray
import retrofit2.http.GET
import retrofit2.http.Header

interface PaymentsService {

	@GET
	suspend fun getPayments(@Header("token") authToken: String): MetaArray<PaymentDAO>

}