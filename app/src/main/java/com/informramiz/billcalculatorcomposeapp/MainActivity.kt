@file:OptIn(ExperimentalComposeUiApi::class)

package com.informramiz.billcalculatorcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.informramiz.billcalculatorcomposeapp.components.InputField
import com.informramiz.billcalculatorcomposeapp.ui.theme.BillCalculatorComposeAppTheme

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenContent()
        }
    }
}

@Composable
private fun ScreenContent() {
    ScreenCanvas {
        ScreenUI()
    }
}

@Composable
private fun ScreenCanvas(content: @Composable () -> Unit) {
    BillCalculatorComposeAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            content()
        }
    }
}

@Composable
private  fun ScreenUI() {
    val perPersonBill = remember {
        mutableStateOf(1f)
    }
    Column {
        PerPersonBill(perPersonBill = perPersonBill.value)
        BillCalculator()
    }
}

@Composable
private fun PerPersonBill(perPersonBill: Float = 0f) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(20.dp)
            .clip(RoundedCornerShape(12.dp)),
        color = MaterialTheme.colors.secondary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Per Person Bill",
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "$%.2f".format(perPersonBill),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}


@Composable
private fun BillCalculator() {
    val currentBillAmountState = remember {
        mutableStateOf("1")
    }

    val isValidBillAmount = remember(currentBillAmountState.value) {
        currentBillAmountState.value.isNotEmpty() && currentBillAmountState.value.isDigitsOnly()
    }

    val totalSplitsState = remember {
        mutableStateOf(1)
    }

    val tipPercentageState = remember {
        mutableStateOf(0.1f)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            BillTextField(currentBillAmountState.value) { newBillValue ->
                currentBillAmountState.value = newBillValue
            }

            if (!isValidBillAmount) return@Column

            SplitBillBetweenButtons(value = totalSplitsState.value) { newValue ->
                totalSplitsState.value = newValue
            }

            TipValue(value = tipPercentageState.value * currentBillAmountState.value.toFloat())

            Text(
                text = "${(tipPercentageState.value * 100).toInt()}%",
                Modifier
                    .padding(top = 12.dp, bottom = 12.dp)
                    .align(Alignment.CenterHorizontally)
            )

            TipPercentageBar(tipPercentageState.value) { newValue ->
                tipPercentageState.value = newValue
            }
        }
    }
}

@Composable
private fun BillTextField(value: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    InputField(
        value = value,
        label = "Enter Bill",
        leadingIcon = Icons.Rounded.AttachMoney,
        modifier = Modifier
            .fillMaxWidth(),
        keyboardType = KeyboardType.Number,
        onKeyboardAction = KeyboardActions {
            keyboardController?.hide()
        }
    ) { newValue ->
        onValueChange(newValue.trim())
    }
}

@Composable
private fun SplitBillBetweenButtons(value: Int, onValueChange: (Int) -> Unit) {
    val validValue = if (value >= 1) value else 1
    Row(
        modifier = Modifier.padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "Split")
        Spacer(modifier = Modifier.weight(1f, true))

        OutlinedButton(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            elevation = ButtonDefaults.elevation(),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                if (validValue > 1) {
                    onValueChange(validValue - 1)
                } else {
                    onValueChange(validValue)
                }
            }
        ) {
            Icon(imageVector = Icons.Rounded.Remove, contentDescription = "", tint = MaterialTheme.colors.onBackground)
        }

        Text(
            modifier = Modifier.padding(horizontal = 6.dp),
            text = "$validValue"
        )

        OutlinedButton(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            elevation = ButtonDefaults.elevation(),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                onValueChange(validValue + 1)
            }
        ) {
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "", tint = MaterialTheme.colors.onBackground)
        }
    }
}

@Composable
private fun TipValue(value: Float) {
    Row(
        modifier = Modifier.padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Tip")
        Spacer(modifier = Modifier.weight(1f, true))
        Text(text = "%.1f$".format(value))
    }
}

@Composable
fun TipPercentageBar(value: Float, onValueChange: (Float) -> Unit) {
    Slider(value = value, onValueChange = {onValueChange(it)})
}

@Composable
@Preview
private fun DefaultPreview() {
    ScreenContent()
}
