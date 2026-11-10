package com.mysketch.feature.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mysketch.shared.domain.model.User
import com.mysketch.feature.shared.theme.Sizes
import com.mysketch.feature.shared.theme.Spacing

/**
 * Example usage of UserAvatarWithStatus in a list item
 */
@Composable
fun UserListItem(
    user: User,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.space16, vertical = Spacing.space8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatarWithStatus(
            userId = user.uid,
            avatarUrl = user.avatar,
            size = Sizes.AvatarDefault,
            showActiveStatus = true,
            displayName = user.displayName ?: user.username
        )
        
        Spacer(modifier = Modifier.width(Spacing.space12))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = user.displayName ?: user.username ?: "Unknown",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            
            user.bio?.let { bioText ->
                Text(
                    text = bioText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}
