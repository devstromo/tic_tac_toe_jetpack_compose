package com.devstromo.advancedtictactoe.presentation.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devstromo.advancedtictactoe.presentation.components.DraggableToggleSwitch

@Composable
fun AnimatedDialog(
    buttonAction: () -> Unit,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    val toggleState = remember { mutableStateOf(false) }

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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    DraggableToggleSwitch(state = toggleState)
                }
                Button(onClick = {
                    buttonAction.invoke()
                    dialogHelper::triggerAnimatedDismiss.invoke()
                }) {
                    Text("Close Button")
                }
            }
        }
    }
}