package com.informramiz.billcalculatorcomposeapp.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@Composable
fun InputField(
    valueSate: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    isEnabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onKeyboardAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(
        value = valueSate.value,
        onValueChange = { valueSate.value = it },
        modifier = modifier,
        label = { Text(text = label) },
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        leadingIcon = leadingIcon?.let{ { Icon(imageVector = leadingIcon, contentDescription = null) } },
        enabled = isEnabled,
        singleLine = isSingleLine,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onKeyboardAction
    )
}