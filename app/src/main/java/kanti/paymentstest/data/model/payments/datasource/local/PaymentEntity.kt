package kanti.paymentstest.data.model.payments.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kanti.paymentstest.data.model.payments.Payment

@Entity(tableName = "payments")
data class PaymentEntity(
	@PrimaryKey override val id: Int = 0,
	override val title: String,
	override val amount: Double?,
	override val created: Long?
) : Payment

fun Payment.toPaymentEntity(): PaymentEntity {
	return PaymentEntity(
		id = id,
		title = title,
		amount = amount,
		created = created
	)
}
