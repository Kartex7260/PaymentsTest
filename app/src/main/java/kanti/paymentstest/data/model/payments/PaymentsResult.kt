package kanti.paymentstest.data.model.payments

import kanti.paymentstest.data.model.common.ErrorMessage

sealed class PaymentsResult {

	class Success(
		val payments: List<Payment>
	) : PaymentsResult()

	data object IncorrectToken : PaymentsResult()

	class Error(
		val error: ErrorMessage
	) : PaymentsResult()

	class Fail(
		val message: String,
		val th: Throwable? = null
	) : PaymentsResult()

}