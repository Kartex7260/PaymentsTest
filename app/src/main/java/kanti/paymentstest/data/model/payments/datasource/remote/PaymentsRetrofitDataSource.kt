package kanti.paymentstest.data.model.payments.datasource.remote

import java.io.IOException
import javax.inject.Inject

class PaymentsRetrofitDataSource @Inject constructor(
	private val paymentsService: PaymentsService
) : PaymentsRemoteDataSource {

	override suspend fun getPayments(authToken: String): PaymentsRemoteResult {
		return try {
			val payments = paymentsService.getPayments(authToken)

			if (payments.success && payments.response != null) {
				PaymentsRemoteResult.Success(
					payments = payments.response.map { it.toPayment() }
				)
			} else if (!payments.success && payments.error != null) {
				when (payments.error.errorCode) {
					1001 -> PaymentsRemoteResult.IncorrectToken
					else -> PaymentsRemoteResult.Error(payments.error)
				}
			} else {
				PaymentsRemoteResult.Fail("Unexpected error")
			}
		} catch(ex: IOException) {
			PaymentsRemoteResult.NoConnection
		} catch(th: Throwable) {
			PaymentsRemoteResult.Fail(th.message ?: "[Not message]", th)
		}
	}
}