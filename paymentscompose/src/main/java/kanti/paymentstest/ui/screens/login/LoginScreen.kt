package kanti.paymentstest.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import kanti.paymentstest.R
import kanti.paymentstest.data.model.common.ErrorMessage
import kanti.paymentstest.ui.theme.PaymentsTestTheme
import java.lang.IllegalStateException

@Composable
fun LoginScreen(
	vm: LoginScreenViewModel = hiltViewModel<LoginScreenViewModelImpl>(),
	successLogin: () -> Unit
) {
	val state by vm.loginUiState.collectAsState()
	if (state is LoginUiState.Success)
		successLogin()
	LoginScreenContent(
		state = state,
		login = { login, password ->
			vm.login(login, password)
		}
	)
}

@Composable
private fun LoginScreenContent(
	state: LoginUiState,
	login: (login: String, password: String) -> Unit
) {
	var incorrectCredentials by remember(key1 = state) {
		mutableStateOf(state is LoginUiState.IncorrectCredentials)
	}
	Column {
		if (state is LoginUiState.Process) {
			LinearProgressIndicator(
				modifier = Modifier.fillMaxWidth()
			)
		}
	}
	Column {
		var loginText by rememberSaveable {
			mutableStateOf("")
		}
		CredentialTextField(
			value = loginText,
			onValueChange = {
				loginText = it
				incorrectCredentials = false
			},
			label = { Text(text = stringResource(id = R.string.login)) },
			modifier = Modifier
				.padding(
					start = 16.dp,
					end = 16.dp,
					top = 16.dp
				)
				.fillMaxWidth(),
			isError = incorrectCredentials
		)

		var passwordText by rememberSaveable {
			mutableStateOf("")
		}
		CredentialTextField(
			value = passwordText,
			onValueChange = {
				passwordText = it
				incorrectCredentials = false
			},
			label = { Text(text = stringResource(id = R.string.password)) },
			modifier = Modifier
				.padding(
					start = 16.dp,
					end = 16.dp,
					top = 16.dp
				)
				.fillMaxWidth(),
			isError = incorrectCredentials,
			keyboardOptions = KeyboardOptions(
				keyboardType = KeyboardType.Password
			),
			visualTransformation = PasswordVisualTransformation()
		)

		ConstraintLayout(
			modifier = Modifier
				.padding(top = 16.dp)
				.fillMaxWidth()
		) {
			val (textRef, buttonRef) = createRefs()
			val text = if (incorrectCredentials) {
				stringResource(id = R.string.invalid_credentials)
			} else if (state is LoginUiState.NoConnection) {
				stringResource(id = R.string.no_connection)
			} else if (state is LoginUiState.Error) {
				val stg = stringResource(id = R.string.unexpected_error)
				"$stg: ${state.errorMessage}"
			} else if (state is LoginUiState.Fail) {
				val stg = stringResource(id = R.string.unexpected_error)
				"$stg: ${state.message}\n${state.throwable?.toString()}"
			} else {
				""
			}
			Text(
				text = text,
				modifier = Modifier
					.constrainAs(textRef) {
						top.linkTo(parent.top)
				  		start.linkTo(parent.start, margin = 16.dp)
						end.linkTo(buttonRef.start, margin = 16.dp)
						width = Dimension.fillToConstraints
					},
				color = MaterialTheme.colorScheme.error,
			)

			Button(
				onClick = { login(loginText, passwordText) },
				modifier = Modifier
					.constrainAs(buttonRef) {
						top.linkTo(parent.top)
						end.linkTo(parent.end, margin = 16.dp)
					},
				enabled = loginText.isNotBlank() && passwordText.isNotBlank()
			) { Text(text = stringResource(id = R.string.logon)) }
		}
	}
}

@Composable
fun CredentialTextField(
	value: String,
	onValueChange: (String) -> Unit,
	label: @Composable () -> Unit,
	modifier: Modifier = Modifier,
	isError: Boolean = false,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
	visualTransformation: VisualTransformation = VisualTransformation.None
) {
	var valueEdited by rememberSaveable {
		mutableStateOf(false)
	}
	OutlinedTextField(
		value = value,
		onValueChange = {
			onValueChange(it)
			if (!valueEdited)
				valueEdited = true
		},
		label = label,
		modifier = modifier,
		isError = value.isBlank() && valueEdited || isError,
		keyboardOptions = keyboardOptions,
		visualTransformation = visualTransformation
	)
}

@Preview(
	name = "Error",
	showBackground = true
)
@Composable
private fun PreviewLoginScreenError() {
	PaymentsTestTheme {
		LoginScreenContent(
			state = LoginUiState.Error(
				ErrorMessage(
					101,
					"Unexpected error ohayo iasjskj dkasd kaksj ddkas "
				)
			),
			login = { _, _ -> }
		)
	}
}

@Preview(
	name = "Fail",
	showBackground = true
)
@Composable
private fun PreviewLoginScreenFail() {
	PaymentsTestTheme {
		LoginScreenContent(
			state = LoginUiState.Fail(
				message = "Error",
				throwable = IllegalStateException("Tested exception")
			),
			login = { _, _ -> }
		)
	}
}

@Preview(
	name = "Normal",
	showBackground = true
)
@Composable
private fun PreviewLoginScreenNormal() {
	PaymentsTestTheme {
		LoginScreenContent(
			state = LoginUiState.Empty,
			login = { _, _ -> }
		)
	}
}