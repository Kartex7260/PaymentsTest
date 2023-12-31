package kanti.paymentstest.data.model.authorization.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kanti.paymentstest.Const
import kanti.paymentstest.data.model.authorization.LoginToken
import kanti.paymentstest.data.model.authorization.TokenResult
import kanti.paymentstest.data.model.common.localTryCatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "")

class AuthorizationDataStoreDataSource @Inject constructor(
	@ApplicationContext private val context: Context
) : AuthorizationLocalDataSource {

	private val tokenKey = stringPreferencesKey(Const.DATA_STORE_KEY_TOKEN)

	override val token: Flow<TokenResult>
		get() = context.dataStore.data.map { preferences ->
			val token = preferences[tokenKey]
			if (token == null) {
				TokenResult.NotLoggedIn
			} else {
				TokenResult.Success(
					LoginToken(
						token = token
					)
				)
			}
		}.catch {
			TokenResult.Fail(it.message ?: "[Not message]", it)
		}

	override suspend fun setToken(token: LoginToken): Result<Unit> {
		return localTryCatch {
			context.dataStore.edit { preferences ->
				preferences[tokenKey] = token.token
			}
		}
	}

	override suspend fun deleteToken(): Result<Unit> {
		return localTryCatch {
			context.dataStore.edit { preferences ->
				preferences.minusAssign(tokenKey)
			}
		}
	}
}