package kanti.paymentstest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kanti.paymentstest.ui.screens.login.LoginScreen
import kanti.paymentstest.ui.theme.PaymentsTestTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			PaymentsTestTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.surface
				) {
					NavHost(
						navController = navController,
						startDestination = Const.NavDestinations.Login
					) {
						composable(
							route = Const.NavDestinations.Payments
						) {
							Column {
								Text("Succes login")
								Button(onClick = { navController.navigate(
									Const.NavDestinations.Login
								) }) {
									Text("To login screen")
								}
							}
						}
						composable(
							route = Const.NavDestinations.Login
						) {
							LoginScreen {
								navController.navigate(Const.NavDestinations.Payments)
							}
						}
					}
				}
			}
		}
	}
}