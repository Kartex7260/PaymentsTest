package kanti.paymentstest.data.model.payments.datasource.remote

import kanti.paymentstest.data.model.payments.PaymentsResult

interface PaymentsRemoteDataSource {

	suspend fun getPayments(authToken: String): PaymentsResult

}