package kanti.paymentstest.ui.fragments.loginscreen

fun interface LoginClickListener {

	fun onClick(
		login: String,
		password: String,
		callback: LoginClickCallback
	)

}

fun interface LoginClickCallback {

	fun onCallback(type: CallbackType)

	enum class CallbackType {
		Success,
		IncorrectCredentials
	}

}
