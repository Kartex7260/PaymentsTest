package kanti.paymentstest.ui.fragments.loginscreen

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kanti.paymentstest.R

class LoginUiBehavior(
	private val context: Context,
	private val loginField: TextInputEditText,
	private val passwordField: TextInputEditText,
	private val loginButton: MaterialButton,
	private val loginClick: LoginClickListener,
) {

	private var loginState: FieldState = FieldState.Empty
		set(value) {
			field = value
			updateButton()
			updateEdiText(loginField, value)
		}

	private var passwordState: FieldState = FieldState.Empty
		set(value) {
			field = value
			updateButton()
			updateEdiText(passwordField, value)
		}

	init {
		loginField.addTextChangedListener(
			onTextChanged = { text, _, _, _ ->
				loginState = if (text.isNullOrBlank())
					FieldState.Empty
				else
					FieldState.Ok
			}
		)

		passwordField.addTextChangedListener(
			onTextChanged = { text, _, _, _ ->
				passwordState = if (text.isNullOrBlank())
					FieldState.Empty
				else
					FieldState.Ok
			}
		)

		loginButton.setOnClickListener {
			allEnabled(false)
			loginClick.onClick(
				loginField.text?.toString() ?: "",
				passwordField.text?.toString() ?: ""
			) { callbackType ->
				allEnabled(true)
				when (callbackType) {
					LoginClickCallback.CallbackType.Success -> {}
					LoginClickCallback.CallbackType.IncorrectCredentials -> {
						loginState = FieldState.Incorrect
						passwordState = FieldState.Incorrect
					}
				}
			}
		}
	}

	fun resetStates() {
		allEnabled(true)
		loginField.apply {
			text?.clear()
			error = null
		}
		passwordField.apply {
			text?.clear()
			error = null
		}
		loginButton.isEnabled = false
	}

	private fun updateEdiText(editText: TextInputEditText, fieldState: FieldState) {
		when (fieldState) {
			FieldState.Ok -> {
				editText.error = null
			}
			FieldState.Empty -> {
				editText.error = context.getString(R.string.edit_text_empty_error)
			}
			FieldState.Incorrect -> {
				editText.error = context.getString(R.string.invalid_credentials)
			}
		}
	}

	private fun updateButton() {
		loginButton.isEnabled = loginState.isOk && passwordState.isOk
	}

	private fun allEnabled(enabled: Boolean) {
		loginField.isEnabled = enabled
		passwordField.isEnabled = enabled
		loginButton.isEnabled = enabled
	}

}

enum class FieldState {

	Ok,
	Empty,
	Incorrect;

	val isOk: Boolean get () = this == Ok

}
