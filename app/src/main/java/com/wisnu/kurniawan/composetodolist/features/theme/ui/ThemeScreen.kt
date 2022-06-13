package com.wisnu.kurniawan.composetodolist.features.theme.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wisnu.kurniawan.composetodolist.R
import com.wisnu.kurniawan.composetodolist.foundation.uicomponent.PgModalBackHeader
import com.wisnu.kurniawan.composetodolist.foundation.uicomponent.PgModalCell
import com.wisnu.kurniawan.composetodolist.foundation.uicomponent.PgModalLayout
import com.wisnu.kurniawan.composetodolist.model.Theme

@Composable
fun ThemeScreen(
    navController: NavController,
    viewModel: ThemeViewModel
) {
    val state by viewModel.state.collectAsState()

    PgModalLayout(
        title = {
            PgModalBackHeader(
                text = stringResource(R.string.setting_theme),
                onClickBack = {
                    navController.navigateUp()
                }
            )
        },
        content = {
            items(state.items) { item ->
                ThemeItem(
                    onClick = {
                        viewModel.dispatch(ThemeAction.SelectTheme(item))
                    },
                    item = item
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    )
}

@SuppressLint("InlinedApi")
@Composable
private fun ThemeItem(
    onClick: () -> Unit,
    item: ThemeItem,
) {
    PgModalCell(
        onClick = onClick,
        text = stringResource(item.title),
        color = if (item.applied) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondaryContainer
        },
        leftIcon = @Composable {
            val brush = if (item.theme == Theme.WALLPAPER) {
                val context = LocalContext.current
                Brush.linearGradient(
                    colors = if (isSystemInDarkTheme()) {
                        listOf(
                            Color(context.resources.getColor(android.R.color.system_accent1_200, context.theme)),
                            Color(context.resources.getColor(android.R.color.system_accent2_700, context.theme)),
                        )
                    } else {
                        listOf(
                            Color(context.resources.getColor(android.R.color.system_accent1_600, context.theme)),
                            Color(context.resources.getColor(android.R.color.system_accent2_100, context.theme)),
                        )
                    }
                )
            } else {
                item.brush
            }
            Box(
                modifier = Modifier.size(34.dp)
                    .background(
                        brush = brush,
                        shape = CircleShape
                    )
            )
        },
        rightIcon = if (item.applied) {
            @Composable {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        } else {
            null
        }
    )
}
