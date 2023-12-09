package com.codewithfk.eventhub.core.presentation


import androidx.compose.ui.window.ComposeUIViewController
import com.codewithfk.eventhub.di.AppModule
import com.codewithfk.eventhub.scorecard.App
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController() = ComposeUIViewController  {
    val isDarkTheme =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle ==
                UIUserInterfaceStyle.UIUserInterfaceStyleDark
    App(
        darkTheme  = isDarkTheme,
        dynamicColor = false,
        appModule = AppModule(),
    )
}