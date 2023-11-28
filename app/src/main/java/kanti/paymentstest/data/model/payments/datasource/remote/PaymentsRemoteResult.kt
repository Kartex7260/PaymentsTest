package kanti.paymentstest.data.model.payments.datasource.remote

import kanti.paymentstest.data.model.common.ErrorMessage
import kanti.paymentstest.data.model.payments.PaymentImpl

sealed class PaymentsRemoteResult {

	class Success(
		val payments: List<PaymentImpl>
	) : PaymentsRemoteResult()

	data object NoConnection : PaymentsRemoteResult()

	data object IncorrectToken : PaymentsRemoteResult()

	class Error(
		val error: ErrorMessage
	) : PaymentsRemoteResult()

	class Fail(
		val message: String,
		val th: Throwable? = null
	) : PaymentsRemoteResult()

}
