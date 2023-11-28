package kanti.paymentstest.data.model.payments

interface Payment {

	val id: Int
	val title: String
	val amount: Double?
	val created: Long?

}