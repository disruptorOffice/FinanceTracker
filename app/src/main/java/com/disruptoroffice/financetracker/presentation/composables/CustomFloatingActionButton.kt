package com.disruptoroffice.financetracker.presentation.composables

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlin.math.exp

/**
 * Created by Alberto Avantes on 14/12/2025.
 */
// Source - https://stackoverflow.com/a
// Posted by MannB1023, modified by community. See post 'Timeline' for change history
// Retrieved 2025-12-14, License - CC BY-SA 4.0

// Custom fab that allows for displaying extended content

@Composable
fun CustomFloatingActionButton(
    expandable: Boolean,
    onFabClick: () -> Unit,
    fabIcon: ImageVector,
    onItemClick: (value: String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    if (!expandable) { // Close the expanded fab if you change to non expandable nav destination
        isExpanded = false
    }

    val items = mapOf(
        "scheduled" to "Pago programado",
        "payment_now" to "Pago normal"
    ).values.toList()
    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(visible = expandable,
            enter = fadeIn()+ slideInVertically(initialOffsetY = {it}) + expandVertically(),
            exit = fadeOut()+ slideOutVertically(targetOffsetY = {it}) + shrinkVertically()
        ) {
            LazyColumn(modifier = Modifier.padding(bottom = 12.dp)) {
                items(items.size) {
                    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End) {
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .height(40.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.secondaryContainer,
                                    shape = RoundedCornerShape(50),
                                )
                                .padding(6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = items[it], style = MaterialTheme.typography.bodyMedium)
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        FloatingActionButton(
                            onClick = {onItemClick(
                                if (items[it].contains("programado")) "scheduled" else "payment_now"
                            )},
                            modifier = Modifier.size(45.dp),
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            if (items[it].contains("programado")) {
                                Icon(imageVector = Icons.Filled.Schedule, contentDescription = "pago programado")
                            } else {
                                Icon(imageVector = Icons.Filled.Paid, contentDescription = "pago normal")
                            }
                        }
                    }
                }
            }
        }

        val transition = updateTransition(targetState = expandable, label = "FAB Transition")
        val rotation by transition.animateFloat(label = "Rotation Animation") {
            if (it) 315f else 0f
        }


        FloatingActionButton(
            onClick = {
                onFabClick()
                if (expandable) {
                    isExpanded = !isExpanded
                }
            },
            modifier = Modifier
                .rotate(rotation)
        ) {

            Icon(
                imageVector = fabIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(x = animateDpAsState(if (isExpanded) (-70).dp else 0.dp, animationSpec = spring(dampingRatio = 3f)).value)
            )

        }
    }
}
