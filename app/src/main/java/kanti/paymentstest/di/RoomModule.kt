package kanti.paymentstest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kanti.paymentstest.Const
import kanti.paymentstest.data.model.payments.datasource.local.PaymentsDao
import kanti.paymentstest.data.room.PaymentsAppDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

	@Provides
	@Singleton
	fun provideRoom(@ApplicationContext context: Context): PaymentsAppDataBase {
		return Room.databaseBuilder(
			context,
			PaymentsAppDataBase::class.java,
			Const.DATABASE_NAME
		).build()
	}

	@Provides
	@Singleton
	fun providePaymentsDao(database: PaymentsAppDataBase): PaymentsDao {
		return database.paymentsDao()
	}

}