package kanti.paymentstest.data.model.payments.datasource.remote

data class PaymentDAO(
	val id: Int,
	val title: String,
	val amount: Double?,
	val created: Long?
)
