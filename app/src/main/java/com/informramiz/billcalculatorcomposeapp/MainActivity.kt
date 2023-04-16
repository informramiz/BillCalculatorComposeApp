package com.informramiz.billcalculatorcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.informramiz.billcalculatorcomposeapp.ui.theme.BillCalculatorComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenContent()
        }
    }
}

@Composable
fun ScreenContent() {
    ScreenCanvas {
        ScreenUI()
    }
}

@Composable
fun ScreenCanvas(content: @Composable () -> Unit) {
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
fun ScreenUI() {
    Column {
        PerPersonBill()
    }
}

@Preview
@Composable
fun PerPersonBill(perPersonBill: Float = 0f) {
    Surface(
        modifier = Modifier.fillMaxWidth()
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
@Preview
fun DefaultPreview() {
    ScreenContent()
}
