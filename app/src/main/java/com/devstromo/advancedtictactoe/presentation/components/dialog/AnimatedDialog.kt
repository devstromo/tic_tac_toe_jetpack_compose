package com.devstromo.advancedtictactoe.presentation.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedDialog(
    buttonAction: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AnimatedTransitionDialog(onDismissRequest = onDismissRequest) { dialogHelper ->
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
                Text("This is an animated dialog")
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