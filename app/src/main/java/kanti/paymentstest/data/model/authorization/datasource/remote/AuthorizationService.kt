package kanti.paymentstest.data.model.authorization.datasource.remote

import kanti.paymentstest.data.model.common.MetaData
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationService {

	@POST("login")
	suspend fun login(@Body user: LoginDTO): MetaData<TokenDTO>

}