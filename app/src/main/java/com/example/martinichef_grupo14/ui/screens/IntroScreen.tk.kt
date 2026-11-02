package com.example.martinichef_grupo14.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.martinichef_grupo14.R
import kotlinx.coroutines.delay

@Composable
fun IntroScreen(
    onTerminar: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    val terminarActual by rememberUpdatedState(onTerminar)

    val transparencia by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 700),
        label = "desvanecerLogo"
    )

    LaunchedEffect(Unit) {
        delay(1400)
        visible = false
        delay(700)
        terminarActual()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8EE))
            .safeDrawingPadding(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.martini_chef_logo
            ),
            contentDescription = "Martini Chef",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(32.dp)
                .widthIn(max = 320.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .alpha(transparencia)
        )
    }
}