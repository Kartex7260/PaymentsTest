package kanti.paymentstest.net

import kanti.paymentstest.Const
import okhttp3.Interceptor
import okhttp3.Response

object HeadInterceptor : Interceptor {

	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request().newBuilder()
			.header(Const.APP_KEY_KEY, Const.APP_KEY_VALUE)
			.header(Const.API_VERSION_KEY, Const.API_VERSION_VALUE)
			.build()
		return chain.proceed(request)
	}

}