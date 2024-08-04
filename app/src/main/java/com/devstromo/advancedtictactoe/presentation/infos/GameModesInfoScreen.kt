package com.devstromo.advancedtictactoe.presentation.infos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.data.item.MenuItem
import com.devstromo.advancedtictactoe.navigation.Screen
import com.devstromo.advancedtictactoe.presentation.components.MenuItemsList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameModesInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val currentContext = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val typo = MaterialTheme.typography

    val menuItems = listOf(
        MenuItem(
            title = currentContext.getString(R.string.mode_classic),
            onClick = {
                navController.navigate(
                    route = Screen.GameModeInfo.createRoute(
                        R.string.mode_classic,
                        R.string.mode_classic_details
                    )
                )
            },
            isActive = true
        ),
        MenuItem(
            title = currentContext.getString(R.string.game_mode_advance_title),
            onClick = {
            },
            isActive = true
        ),
        MenuItem(
            title = currentContext.getString(R.string.mode_against_bot),
            onClick = {
            },
            isActive = true
        ),
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.White,
            ),
            title = {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 10.dp
                        ),
                    text = currentContext.getString(R.string.game_modes_title),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = typo.bodyLarge
                )
            },
            navigationIcon = {
                IconButton(
                    modifier = Modifier
                        .height(45.dp)
                        .width(45.dp)
                        .background(
                            color = Color.Transparent,
                        ),
                    onClick = {
                        navController.navigate(route = Screen.Initial.route)
                    },
                ) {
                    Icon(
                        Icons.Rounded.Home,
                        contentDescription = "Home",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
        Spacer(modifier = Modifier.weight(1F))
        MenuItemsList(
            menuItems = menuItems
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}