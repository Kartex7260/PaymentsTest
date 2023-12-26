package kanti.paymentstest.data.model.payments.datasource.local

import kanti.paymentstest.data.model.payments.Payment
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentsRoomDataSource @Inject constructor(
	private val dao: PaymentsDao
) : PaymentsLocalDataSource {

	override val payments: Flow<List<Payment>>
		get() = dao.getAll()

	override suspend fun cachePayments(payments: List<Payment>): Result<Unit> {
		return try {
			dao.insert(*payments.map { it.toPaymentEntity() }.toTypedArray())
			Result.success(Unit)
		} catch (th: Throwable) {
			Result.failure(th)
		}
	}

	override suspend fun deleteCache(): Result<Unit> {
		return try {
			dao.deleteAll()
			Result.success(Unit)
		} catch (th: Throwable) {
			Result.failure(th)
		}
	}
}