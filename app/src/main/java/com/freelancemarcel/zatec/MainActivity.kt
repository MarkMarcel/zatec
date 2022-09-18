package com.freelancemarcel.zatec

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.freelancemarcel.zatec.core.IceAndFireApplicationViewModel
import com.freelancemarcel.zatec.core.models.ErrorType
import com.freelancemarcel.zatec.core.ui.HOUSE_PATH
import com.freelancemarcel.zatec.core.ui.ROOT
import com.freelancemarcel.zatec.core.ui.components.ErrorSnackbar
import com.freelancemarcel.zatec.core.ui.components.ScreenLoading
import com.freelancemarcel.zatec.core.ui.theme.GOTFont
import com.freelancemarcel.zatec.core.ui.theme.IceTheme
import com.freelancemarcel.zatec.houses.ListOfHousesScreen
import com.freelancemarcel.zatec.houses.house.HouseScreen

class MainActivity : ComponentActivity() {
    val viewModel: IceAndFireApplicationViewModel by viewModels { IceAndFireApplicationViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Ice)
        super.onCreate(savedInstanceState)
        setContent {
            val error by viewModel.getValueAsState(IceAndFireApplicationViewModel.State::error)
            val isAScreenLoading by viewModel.getValueAsState(IceAndFireApplicationViewModel.State::isAScreenLoading)
            val navController = rememberNavController()
            val scaffoldState = SnackbarHostState()

            IceTheme {
                Box(Modifier.fillMaxSize()){
                    NavHost(navController = navController, startDestination = ROOT) {
                        composable(
                            HOUSE_PATH,
                            arguments = listOf(navArgument("url") {
                                type = NavType.StringType
                            }),
                        ) {
                            val houseUrl = """https://www.anapioficeandfire.com/api/houses/${it.arguments!!.getString("url","")}"""
                            HouseScreen(houseUrl = houseUrl, viewModel = viewModel)
                        }
                        composable(ROOT) {
                            ListOfHousesScreen(viewModel) { houseUrl ->
                                navController.navigate(HOUSE_PATH.replace("{url}", houseUrl.split("/").last()))
                            }
                        }
                    }
                    if (isAScreenLoading) {
                        ScreenLoading()
                    }
                    ErrorSnackbar(modifier = Modifier.align(Alignment.TopStart), hostState = scaffoldState)
                }
            }
            LaunchedEffect(key1 = error) {
                error?.let {
                    val message = when(it.type){
                        ErrorType.LOAD_FAILURE_HOUSE -> getString(R.string.failure_loadhouse)
                        ErrorType.LOAD_FAILURE_HOUSES -> getString(R.string.failure_loadhouses)
                    }
                    scaffoldState.showSnackbar(message)
                    viewModel.onError(null)
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