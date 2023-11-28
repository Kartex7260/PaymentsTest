package kanti.paymentstest.data.model.payments.datasource.remote

interface PaymentsRemoteDataSource {

	suspend fun getPayments(authToken: String): PaymentsRemoteResult

}