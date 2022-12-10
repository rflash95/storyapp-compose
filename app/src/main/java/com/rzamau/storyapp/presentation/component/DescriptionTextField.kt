package com.rzamau.storyapp.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.rzamau.storyapp.R
import kotlinx.coroutines.delay

@Composable
fun DescriptionTextField(
    modifier: Modifier,
    singleLine: Boolean,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: @Composable (() -> Unit)? = null,
    maxLines: Int = 1,
    placeholder: @Composable (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current
    val textValue = remember { mutableStateOf(value) }
    val errorValue = remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            modifier = modifier,
            value = textValue.value,
            onValueChange = {
                textValue.value = it
                onValueChange(it)
            },
            label = label,
            singleLine = singleLine,
            leadingIcon = leadingIcon,
            isError = errorValue.value.isNotEmpty(),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            placeholder = placeholder,
            visualTransformation = visualTransformation,
        )
        if (errorValue.value.isNotEmpty()) {
            Text(
                text = errorValue.value,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        LaunchedEffect(key1 = textValue.value, block = {
            errorValue.value = ""
            delay(800)
            if (textValue.value.isEmpty()) {
                errorValue.value = context.getString(R.string.description_cant_empty)
            }
        })

    }

}