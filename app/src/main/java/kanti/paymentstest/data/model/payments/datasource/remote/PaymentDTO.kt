package kanti.paymentstest.data.model.payments.datasource.remote

import kanti.paymentstest.data.model.payments.PaymentImpl

data class PaymentDTO(
	val id: Int?,
	val title: String?,
	val amount: Double?,
	val created: Long?
)

fun PaymentDTO.toPayment(): PaymentImpl? {
	if (id == null || title == null)
		return null
	return PaymentImpl(
		id = id,
		title = title,
		amount = amount,
		created = created
	)
}
