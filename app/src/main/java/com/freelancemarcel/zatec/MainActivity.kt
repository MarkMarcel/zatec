package com.freelancemarcel.zatec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.freelancemarcel.zatec.core.IceAndFireApplication
import com.freelancemarcel.zatec.core.IceAndFireApplicationViewModel
import com.freelancemarcel.zatec.core.ui.theme.GOTFont
import com.freelancemarcel.zatec.core.ui.theme.IceTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val viewModel: IceAndFireApplicationViewModel by viewModels { IceAndFireApplicationViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ice)
        super.onCreate(savedInstanceState)
        setContent {
            IceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                        Card(Modifier.size(300.dp, 40.dp), shape = RoundedCornerShape(50), backgroundColor = Color.White) {

                        }
                        Greeting("Android")
                    }
                }
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", fontFamily = GOTFont)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IceTheme {
        Greeting("Android")
    }
}