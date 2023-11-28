package kanti.paymentstest.data.model.payments

data class Payment(
	val id: Int,
	val title: String,
	val amount: Double?,
	val created: Long?
)
