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
	private val loginClick: (
		login: String,
		password: String,
		callback: () -> Unit
	) -> Unit,
) {

	private var loginOk: Boolean = false
		set(value) {
			field = value
			updateButton()
			updateEdiText(loginField, value)
		}

	private var passwordOk: Boolean = false
		set(value) {
			field = value
			updateButton()
			updateEdiText(passwordField, value)
		}

	init {
		loginField.addTextChangedListener(
			onTextChanged = { text, _, _, _ ->
				loginOk = !text.isNullOrBlank()
			}
		)

		passwordField.addTextChangedListener(
			onTextChanged = { text, _, _, _ ->
				passwordOk = !text.isNullOrBlank()
			}
		)

		loginButton.setOnClickListener {
			allEnabled(false)
			loginClick(
				loginField.text?.toString() ?: "",
				passwordField.text?.toString() ?: ""
			) {
				allEnabled(true)
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

	private fun updateEdiText(editText: TextInputEditText, isOk: Boolean) {
		if (isOk) {
			editText.error = null
		} else {
			editText.error = context.getString(R.string.edit_text_empty_error)
		}
	}

	private fun updateButton() {
		loginButton.isEnabled = loginOk && passwordOk
	}

	private fun allEnabled(enabled: Boolean) {
		loginField.isEnabled = enabled
		passwordField.isEnabled = enabled
		loginButton.isEnabled = enabled
	}

}