package com.devstromo.advancedtictactoe.presentation.rules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.presentation.components.GradientDivider

@Composable
fun RulesScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val color = MaterialTheme.colorScheme
    val typos = MaterialTheme.typography
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .align(Alignment.TopStart)
        ) {
            IconButton(
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .background(
                        color = color.primary,
                        shape = RoundedCornerShape(25)
                    ),

                onClick = { navController.popBackStack() },
            ) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "Favorite",
                    tint = color.secondary,
                    modifier = Modifier
                        .size(48.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(
                    horizontal = 25.dp
                ),
        ) {
            Text(
                text = "Game Rules",
                style = typos.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Divider(
                modifier = Modifier
                    .width(50.dp)
                    .padding(
                        top = 10.dp,
                        bottom = 20.dp
                    )
                    .height(2.dp)
            )
            RuleInfo(
                header = "Win",
                subtitle = "Place three of your marks in a horizontal, vertical, or diagonal row.",
                iconId = R.drawable.ic_win
            )
            GradientDivider(
                Modifier
                    .padding(
                        vertical = 10.dp
                    )
            )
            RuleInfo(
                header = "Defeat",
                subtitle = "Allow the opponent to place three marks in a row.",
                iconId = R.drawable.ic_defeat
            )
            GradientDivider(
                Modifier
                    .padding(
                        vertical = 10.dp
                    )
            )
            RuleInfo(
                header = "Draw",
                subtitle = "All cells are filled without any player having three marks in a row.",
                iconId = R.drawable.ic_draw
            )
        }
    }
}

@Composable
fun RuleInfo(
    modifier: Modifier = Modifier,
    header: String,
    subtitle: String,
    iconId: Int = R.drawable.ic_defeat,
) {
    val typos = MaterialTheme.typography
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            modifier = Modifier
                .weight(1f)
                .padding(end = 10.dp),
            painter = painterResource(
                id = iconId
            ),
            contentDescription = ""
        )
        Column(
            modifier = Modifier.weight(3.5f)
        ) {
            Text(
                text = header,
                style = typos.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Text(
                text = subtitle,
                style = typos.labelSmall
            )
        }
        Box(
            modifier = Modifier
                .weight(1.5f)
                .background(color = Color.Red)
        ) {

        }
    }
}