package com.rzamau.storyapp.presentation.component

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rzamau.storyapp.R

@Composable
fun TopBar(
    title: String,
    upAvailable: Boolean = false,
    onBack: () -> Unit = {},
    isLogin: Boolean = false,
    onLogout: () -> Unit = {}
) {

    val appBarHorizontalPadding = 4.dp
    val titleIconModifier = Modifier
        .fillMaxHeight()
        .width(72.dp - appBarHorizontalPadding)

    val context = LocalContext.current


    TopAppBar({
        Box {
            if (upAvailable) {
                Row(titleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(
                            onClick = {
                                onBack()
                            },
                            enabled = true,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back_button),
                            )
                        }
                    }
                }
            }

            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ProvideTextStyle(value = MaterialTheme.typography.body1) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = title,
                        )
                    }
                }
            }



            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                ) {
                    Row {
                        IconButton(
                            onClick = {
                                context.startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                            },
                            enabled = true,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Language,
                                contentDescription = "",
                            )
                        }
                        if (isLogin) {
                            IconButton(
                                onClick = {
                                    onLogout()
                                },
                                enabled = true,
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Logout,
                                    contentDescription = "",
                                )
                            }
                        }
                    }
                }
            }
        }

    })
}