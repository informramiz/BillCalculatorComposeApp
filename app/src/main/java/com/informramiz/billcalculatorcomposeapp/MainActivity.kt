@file:OptIn(ExperimentalComposeUiApi::class)

package com.informramiz.billcalculatorcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
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
    Column {
        PerPersonBill()
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
        mutableStateOf("")
    }

    val isValidBillAmount = remember(currentBillAmountState.value) {
        currentBillAmountState.value.isNotEmpty() && currentBillAmountState.value.isDigitsOnly()
    }

    BillTextField(currentBillAmountState.value) { newBillValue ->
        currentBillAmountState.value = newBillValue
    }

    if (isValidBillAmount) {
        Text(text = "Valid Bill Amount")
    } else {
        Text(text = "Invalid Bill Amount")
    }
}

@Composable
private fun BillTextField(value: String, onValueChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, color = Color.LightGray)
    ) {
        Column {
            InputField(
                value = value,
                label = "Enter Bill",
                leadingIcon = Icons.Rounded.AttachMoney,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
                keyboardType = KeyboardType.Number,
                onKeyboardAction = KeyboardActions {
                    keyboardController?.hide()
                }
            ) { newValue ->
                onValueChange(newValue.trim())
            }
        }
    }
}

@Composable
@Preview
private fun DefaultPreview() {
    ScreenContent()
}
