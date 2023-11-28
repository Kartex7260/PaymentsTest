package kanti.paymentstest.data.model.payments.datasource.remote

import kanti.paymentstest.data.model.payments.PaymentsResult
import javax.inject.Inject

class PaymentsRetrofitDataSource @Inject constructor(
	private val paymentsService: PaymentsService
) : PaymentsRemoteDataSource {

	override suspend fun getPayments(authToken: String): PaymentsResult {
		return try {
			val payments = paymentsService.getPayments(authToken)

			if (payments.success && payments.response != null) {
				PaymentsResult.Success(
					payments = payments.response.map { it.toPayment() }
				)
			} else if (!payments.success && payments.error != null) {
				when (payments.error.errorCode) {
					1001 -> PaymentsResult.IncorrectToken
					else -> PaymentsResult.Error(payments.error)
				}
			} else {
				PaymentsResult.Fail("Unexpected error")
			}
		} catch (th: Throwable) {
			PaymentsResult.Fail(th.message ?: "[Not message]", th)
		}
	}
}