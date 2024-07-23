package com.devstromo.advancedtictactoe.presentation.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.R
import com.devstromo.advancedtictactoe.config.LocalAppLanguage
import com.devstromo.advancedtictactoe.presentation.components.DraggableToggleSwitch

@Composable
fun AnimatedDialog(
    buttonAction: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    val currentLanguage = LocalAppLanguage.current.value
    val toggleState = remember { mutableStateOf(currentLanguage == "es") }
    val typo = MaterialTheme.typography

    LaunchedEffect(currentLanguage) {
        toggleState.value = (currentLanguage == "es")
    }

    AnimatedTransitionDialog(
        onDismissRequest = onDismissRequest
    ) { dialogHelper ->
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = context.getString(R.string.select_language_title),
                    style = typo.bodyMedium.copy(
                        color = Color.White
                    )
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    DraggableToggleSwitch(state = toggleState)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        val selectedLanguage = if (toggleState.value) "es" else "en"
                        buttonAction(selectedLanguage)
                        dialogHelper::triggerAnimatedDismiss.invoke()
                    }) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            text = context.getString(R.string.title_accept),
                            style = typo.bodySmall.copy(
                                color = Color.White
                            )
                        )
                    }
                    Button(onClick = {
                        buttonAction("")
                        dialogHelper::triggerAnimatedDismiss.invoke()
                    }) {
                        Text(
                            text = context.getString(R.string.title_cancel),
                            style = typo.bodySmall.copy(
                                color = Color.White
                            )
                        )
                    }
                }
            }
        }
    }
}
