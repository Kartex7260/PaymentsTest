package kanti.paymentstest.data.model.payments

import androidx.compose.runtime.Stable
import kanti.paymentstest.data.model.common.ErrorMessage

sealed class PaymentsResult {

	@Stable
	class Success(
		val payments: List<Payment>
	) : PaymentsResult()

	data object NoConnection : PaymentsResult()

	data object IncorrectToken : PaymentsResult()

	class Error(
		val error: ErrorMessage
	) : PaymentsResult()

	class Fail(
		val message: String,
		val th: Throwable? = null
	) : PaymentsResult()

}