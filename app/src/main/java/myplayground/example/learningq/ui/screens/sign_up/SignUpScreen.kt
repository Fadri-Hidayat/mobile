package myplayground.example.learningq.ui.screens.sign_up

import android.app.Application
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.components.CustomOutlinedTextField
import myplayground.example.learningq.ui.components.PasswordOutlinedTextField
import myplayground.example.learningq.ui.navigation.Screen
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    vm: SignUpViewModel = viewModel(
        factory = ViewModelFactory(
            LocalContext.current.applicationContext as Application,
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
    navController: NavHostController = rememberNavController(),
) {
    val inputData by vm.uiState

    SignUpContent(
        modifier = modifier,
        inputData.name,
        inputData.username,
        inputData.password,
        inputData.confirmPassword,
        inputData.hasNameError,
        inputData.hasUsernameError,
        inputData.hasPasswordError,
        inputData.hasConfirmPasswordError,
        vm::onEvent,
    ) {
        navController.navigate(Screen.SignIn.route) {
            popUpTo(Screen.Landing.route) {
                inclusive = false
            }
        }
    }
}

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    name: String = "",
    username: String = "",
    password: String = "",
    confirmPassword: String = "",
    hasNameError: Boolean = false,
    hasUsernameError: Boolean = false,
    hasPasswordError: Boolean = false,
    hasConfirmPasswordError: Boolean = false,
    onEvent: (SignUpUIEvent) -> Unit = {},
    navigateToSignIn: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp, 0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = {
                onEvent(SignUpUIEvent.NameChanged(it))
            },
            label = {
                Text(
                    "Name",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            isError = hasNameError,
            supportingText = {
                if (hasNameError) {
                    Text(
                        "Temporary Input Error",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = {
                onEvent(SignUpUIEvent.UsernameChanged(it))
            },
            label = {
                Text(
                    "Email",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            isError = hasUsernameError,
            supportingText = {
                if (hasUsernameError) {
                    Text(
                        "Temporary Input Error",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        PasswordOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                onEvent(SignUpUIEvent.PasswordChanged(it))
            },
            label = {
                Text(
                    "Password",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            isError = hasPasswordError,
            supportingText = {
                if (hasPasswordError) {
                    Text(
                        "Temporary Input Error",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))


        PasswordOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = {
                onEvent(SignUpUIEvent.ConfirmPasswordChanged(it))
            },
            label = {
                Text(
                    "Confirm Password",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            isError = hasConfirmPasswordError,
            supportingText = {
                if (hasConfirmPasswordError) {
                    Text(
                        "Temporary Input Error",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))


        Button(
            onClick = {
                onEvent(SignUpUIEvent.Submit)
            }, shape = MaterialTheme.shapes.small, modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Register",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(modifier = Modifier
            .align(Alignment.Start)
            .clickable {
                navigateToSignIn()
            }
            .padding(0.dp, 4.dp, 10.dp, 8.dp)) {
            Text(
                "Already have an Account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                "Sign In",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SignUpContentPreview() {
    LearningQTheme {
        SignUpContent()
    }
}

