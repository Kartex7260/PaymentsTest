package kanti.paymentstest.data.model.payments

interface PaymentsRepository {

	suspend fun getPayments(authToken: String): PaymentsResult

}