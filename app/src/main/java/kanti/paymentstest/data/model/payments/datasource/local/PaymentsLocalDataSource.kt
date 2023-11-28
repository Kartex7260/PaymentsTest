package kanti.paymentstest.data.model.payments.datasource.local

import kanti.paymentstest.data.model.payments.Payment
import kanti.paymentstest.data.model.payments.PaymentImpl
import kotlinx.coroutines.flow.Flow

interface PaymentsLocalDataSource {

	val payments: Flow<List<Payment>>

	suspend fun cachePayments(payments: List<Payment>): Result<Unit>

	suspend fun deleteCache(): Result<Unit>

}