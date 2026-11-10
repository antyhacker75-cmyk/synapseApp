import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mysketch.desktop.theme.SynapseTheme
import com.mysketch.desktop.ui.DesktopMainScreen
import com.mysketch.desktop.ui.LoginScreen
import com.mysketch.desktop.di.desktopModule
import com.mysketch.shared.core.network.SupabaseClient
import com.mysketch.shared.domain.model.auth.AuthSessionStatus
import com.mysketch.shared.domain.repository.AuthRepository
import org.koin.core.context.startKoin
import io.github.aakira.napier.Napier
import org.koin.compose.koinInject

fun main() = application {
    val koin = remember {
        try {
            startKoin {
                modules(desktopModule)
            }.koin
        } catch (e: Exception) {
            Napier.e("Failed to initialize Koin", e)
            null
        }
    }

    LaunchedEffect(Unit) {
        try {
            // Ensure Supabase is initialized
            SupabaseClient.client
        } catch (e: Exception) {
            Napier.e("Failed to initialize Supabase client", e)
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Synapse Desktop",
        state = rememberWindowState(width = 1200.dp, height = 800.dp)
    ) {
        SynapseTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation(
    authRepository: AuthRepository = koinInject()
) {
    val sessionStatus by authRepository.sessionStatus.collectAsState(AuthSessionStatus.INITIALIZING)

    when (sessionStatus) {
        AuthSessionStatus.AUTHENTICATED -> {
            DesktopMainScreen()
        }
        AuthSessionStatus.INITIALIZING, AuthSessionStatus.REFRESHING -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        else -> {
            LoginScreen()
        }
    }
}
