package kanti.paymentstest.data.model.payments.datasource.remote

import kanti.paymentstest.data.model.payments.Payment

data class PaymentDAO(
	val id: Int,
	val title: String,
	val amount: Double?,
	val created: Long?
)

fun PaymentDAO.toPayment(): Payment {
	return Payment(
		id = id,
		title = title,
		amount = amount,
		created = created
	)
}
