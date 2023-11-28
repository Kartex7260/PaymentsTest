package kanti.paymentstest.data.model.payments

data class PaymentImpl(
	override val id: Int,
	override val title: String,
	override val amount: Double?,
	override val created: Long?
) : Payment

fun Payment.toPayment(): PaymentImpl {
	return PaymentImpl(
		id = id,
		title = title,
		amount = amount,
		created = created
	)
}
