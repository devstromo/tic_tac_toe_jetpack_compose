package com.devstromo.advancedtictactoe.presentation.infos.rules

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.presentation.components.GradientDivider
import com.devstromo.advancedtictactoe.presentation.components.RuleInfo

@Composable
fun AdvanceModeInfoScreen(
    modifier: Modifier = Modifier,
    popBackStack: ()-> Unit
) {

    val typos = MaterialTheme.typography
    val infiniteTransition = rememberInfiniteTransition(
        label = "divider_animation_transition"
    )

    val animatedWidth by infiniteTransition.animateFloat(
        initialValue = 50f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "divider_animation"
    )
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
                    .height(45.dp)
                    .width(45.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(25)
                    ),

                onClick = { popBackStack() },
            ) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "Favorite",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
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
                text = stringResource(R.string.game_mode_advance_title),
                style = typos.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            HorizontalDivider(
                modifier = Modifier
                    .width(animatedWidth.dp)
                    .padding(
                        top = 10.dp,
                        bottom = 20.dp
                    )
                    .height(2.dp)
            )
            RuleInfo(
                header = stringResource(R.string.header_win),
                subtitle = stringResource(R.string.subtitle_win),
                iconId = R.drawable.ic_win
            )
            GradientDivider(
                Modifier
                    .padding(
                        vertical = 10.dp
                    )
            )
            RuleInfo(
                header = stringResource(R.string.header_defeat),
                subtitle = stringResource(R.string.subtitle_defeat),
                iconId = R.drawable.ic_defeat
            )
            GradientDivider(
                Modifier
                    .padding(
                        vertical = 10.dp
                    )
            )
            RuleInfo(
                header = stringResource(R.string.header_draw),
                subtitle = stringResource(R.string.subtitle_draw_1v1_advance),
                iconId = R.drawable.ic_draw
            )
        }
    }
}