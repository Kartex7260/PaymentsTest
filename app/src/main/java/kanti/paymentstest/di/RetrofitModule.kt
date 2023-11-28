package kanti.paymentstest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kanti.paymentstest.Const
import kanti.paymentstest.data.model.authorization.datasource.remote.AuthorizationService
import kanti.paymentstest.net.HeadInterceptor
import kanti.paymentstest.net.gson.paymentsGsonConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit {
		return Retrofit.Builder()
			.baseUrl(Const.BASE_URL)
			.client(OkHttpClient.Builder()
				.addInterceptor(HeadInterceptor)
				.build())
			.addConverterFactory(paymentsGsonConverterFactory())
			.build()
	}

	@Provides
	@Singleton
	fun provideAuthorizationService(retrofit: Retrofit): AuthorizationService {
		return retrofit.create(AuthorizationService::class.java)
	}

}