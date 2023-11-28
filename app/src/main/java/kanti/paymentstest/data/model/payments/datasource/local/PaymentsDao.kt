package kanti.paymentstest.data.model.payments.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentsDao {

	@Query("SELECT * FROM payments")
	fun getAll(): Flow<List<PaymentEntity>>

	@Insert
	suspend fun insert(vararg payment: PaymentEntity)

	@Query("DELETE FROM payments")
	suspend fun deleteAll()

}