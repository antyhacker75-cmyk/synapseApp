package com.mysketch.feature.shared.components
import com.mysketch.ui.navigation.DeepLink

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.lifecycleScope
import com.mysketch.feature.shared.components.compose.FollowListScreen
import com.mysketch.core.util.IntentUtils
import com.mysketch.shared.domain.repository.AuthRepository
import com.mysketch.feature.shared.theme.SynapseTheme
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FollowListActivity : AppCompatActivity() {
    @Inject
    lateinit var authRepository: AuthRepository

    companion object {
        const val EXTRA_USER_ID = "user_id"
        const val EXTRA_INITIAL_TAB = "initial_tab"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra(EXTRA_USER_ID)
        val initialTab = intent.getIntExtra(EXTRA_INITIAL_TAB, 0)

        if (userId == null) {
            finish()
            return
        }

        setContent {
            SynapseTheme {
                FollowListScreen(
                    userId = userId,
                    initialTab = initialTab,
                    onNavigateBack = { finish() },
                    onUserClick = { targetUserId ->
                        IntentUtils.openUrl(this@FollowListActivity, DeepLink.profile(targetUserId))
                    },
                    onMessageClick = { targetUserId, _, _ ->
                        startDirectChat(targetUserId)
                    }
                )
            }
        }
    }

    private fun startDirectChat(targetUserId: String) {
        lifecycleScope.launch {
            try {
                val currentUserId = authRepository.getCurrentUserId()

                if (currentUserId == null) {
                    return@launch
                }

                if (targetUserId == currentUserId) {
                    return@launch
                }

                Toast.makeText(this@FollowListActivity, "Chat feature not implemented", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
            }
        }
    }
}
