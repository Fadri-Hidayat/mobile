package myplayground.example.learningq.ui.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import myplayground.example.learningq.R
import myplayground.example.learningq.di.Injection
import myplayground.example.learningq.model.User
import myplayground.example.learningq.ui.utils.debugPlaceholder
import myplayground.example.learningq.utils.AuthManager

@Composable
fun DrawerHeader(
    modifier: Modifier = Modifier,
    user: User? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (user != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = user.image_url,
                    contentDescription = "Profile Photo",
                    placeholder = debugPlaceholder(debugPreview = R.drawable.avatar_placeholder),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .width(120.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = user.name,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Composable
fun DrawerBody(
    modifier: Modifier = Modifier,
    items: List<MenuItem> = listOf(),
    onItemClick: (menuItems: MenuItem) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        items(items) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DrawerBodyStudent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authManager: AuthManager = Injection.provideAuthManager(LocalContext.current),
    closeDrawer: () -> Unit = {},
) {
    val isLogoutModalOpen = remember {
        mutableStateOf(false)
    }

    DrawerBody(modifier = modifier, items = listOf(
        MenuItem(
            id = "home",
            title = "Home",
            contentDescription = "Home",
            icon = Icons.Default.Home,
        ),
        MenuItem(
            id = "presence",
            title = "Presence",
            contentDescription = "Presence",
            icon = Icons.Default.CalendarMonth,
        ),
        MenuItem(
            id = "quiz",
            title = "Quiz",
            contentDescription = "Quiz",
            icon = Icons.Default.Quiz,
        ),
        MenuItem(
            id = "logout",
            title = "Logout",
            contentDescription = "Logout",
            icon = Icons.Default.Logout,
        ),
    ), onItemClick = { menuItem ->
        when (menuItem.id) {
            "home" -> {

            }

            "presence" -> {

            }

            "quiz" -> {

            }

            "logout" -> {
                isLogoutModalOpen.value = true
            }
        }

        closeDrawer()
    })

    if (isLogoutModalOpen.value) {
        DialogLogout(
            onDismissRequest = {
                isLogoutModalOpen.value = false
            },
            logout = {
                authManager.logout()
            }
        )
    }
}

@Composable
fun DialogLogout(
    onDismissRequest: () -> Unit = {},
    logout: () -> Unit = {},
) {
    AlertDialog(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        onDismissRequest = onDismissRequest,
        icon = { //                        Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = "Logout")
        },
        text = {
            Text(text = "Apakah anda ingin keluar?")
        },
        confirmButton = {
            Button(onClick = {
                logout()
            }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismissRequest()
            }) {
                Text("Cancel")
            }
        })
}

data class MenuItem(
    val id: String,
    val title: String,
    val contentDescription: String?,
    val icon: ImageVector,
)


