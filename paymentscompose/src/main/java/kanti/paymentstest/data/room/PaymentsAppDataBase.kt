package kanti.paymentstest.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kanti.paymentstest.data.model.payments.datasource.local.PaymentEntity
import kanti.paymentstest.data.model.payments.datasource.local.PaymentsDao

@Database(
	version = 1,
	entities = [
		PaymentEntity::class
	]
)
abstract class PaymentsAppDataBase : RoomDatabase() {

	abstract fun paymentsDao(): PaymentsDao

}