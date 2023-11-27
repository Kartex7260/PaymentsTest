package kanti.paymentstest.data.model.common

suspend fun <T> localResultTryCatch(block: suspend () -> LocalResult<T>): LocalResult<T> {
	return try {
		block()
	} catch (th: Throwable) {
		LocalResult.Fail(th.message ?: "[No message]", th)
	}
}

suspend fun localTryCatch(block: suspend () -> Unit): LocalResult<Unit> {
	return localResultTryCatch {
		block()
		LocalResult.Success(Unit)
	}
}