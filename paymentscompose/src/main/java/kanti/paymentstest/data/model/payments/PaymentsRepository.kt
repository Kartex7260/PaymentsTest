package kanti.paymentstest.data.model.payments

import kotlinx.coroutines.flow.Flow

interface PaymentsRepository {

	val payments: Flow<List<Payment>>

	suspend fun getPayments(authToken: String): PaymentsResult

	suspend fun deleteCache(): Result<Unit>

}