package com.settlr.app.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

private const val TRANSITION_DURATION = 300

val enterTransition: EnterTransition =
    slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = tween(TRANSITION_DURATION)
    ) + fadeIn(animationSpec = tween(TRANSITION_DURATION))

val exitTransition: ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { -it / 3 },
        animationSpec = tween(TRANSITION_DURATION)
    ) + fadeOut(animationSpec = tween(TRANSITION_DURATION))

val popEnterTransition: EnterTransition =
    slideInHorizontally(
        initialOffsetX = { -it / 3 },
        animationSpec = tween(TRANSITION_DURATION)
    ) + fadeIn(animationSpec = tween(TRANSITION_DURATION))

val popExitTransition: ExitTransition =
    slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = tween(TRANSITION_DURATION)
    ) + fadeOut(animationSpec = tween(TRANSITION_DURATION))