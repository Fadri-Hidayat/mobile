package myplayground.example.learningq.ui.screens.sign_in

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.local_storage.DatastoreSettings
import myplayground.example.learningq.local_storage.dataStore
import myplayground.example.learningq.ui.components.PasswordOutlinedTextField
import myplayground.example.learningq.ui.theme.LearningQTheme
import myplayground.example.learningq.ui.utils.ViewModelFactory

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    vm: SignInViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
    navController: NavHostController = rememberNavController(),
) {
    val inputData by vm.uiState

    SignInContent(
        modifier = modifier,
        inputData.username,
        inputData.password,
        vm::onEvent,
    )
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    username: String = "",
    password: String = "",
    onEvent: (SignInUIEvent) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp)
            .offset(y = (-100).dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.titleLarge,
        )

        Box(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = {
                onEvent(SignInUIEvent.UsernameChanged(it))
            },
            label = {
                Text(
                    "Email",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
        )

        Box(modifier = Modifier.height(12.dp))

        PasswordOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                onEvent(SignInUIEvent.PasswordChanged(it))
            },
            label = {
                Text(
                    "Password",
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
        )

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .clickable { }
                .padding(10.dp, 8.dp, 0.dp, 8.dp),
            text = "Forgot Password",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
        )

        Box(
            modifier = Modifier.height(12.dp),
        )

        Button(
            onClick = {},
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Login",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Box(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { }
                .padding(0.dp, 4.dp, 10.dp, 8.dp)
        ) {
            Text(
                "Don't have an Account? ",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                "Sign Up",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SignInContentPreview() {
    LearningQTheme {
        SignInContent()
    }
}

