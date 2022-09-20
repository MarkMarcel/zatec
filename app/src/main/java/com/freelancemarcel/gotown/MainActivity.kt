package com.freelancemarcel.gotown

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
import com.freelancemarcel.gotown.core.IceAndFireApplicationViewModel
import com.freelancemarcel.gotown.core.models.ErrorType
import com.freelancemarcel.gotown.core.ui.HOUSE_PATH
import com.freelancemarcel.gotown.core.ui.ROOT
import com.freelancemarcel.gotown.core.ui.components.ErrorSnackbar
import com.freelancemarcel.gotown.core.ui.components.ScreenLoading
import com.freelancemarcel.gotown.core.ui.theme.GOTFont
import com.freelancemarcel.gotown.core.ui.theme.IceTheme
import com.freelancemarcel.gotown.houses.ListOfHousesScreen
import com.freelancemarcel.gotown.houses.house.HouseScreen
import java.net.URL
import java.net.URLDecoder
import java.net.URLEncoder

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
                            val houseUrl = URLDecoder.decode(it.arguments!!.getString("url",""),"utf-8")
                            HouseScreen(houseUrl = houseUrl, viewModel = viewModel){ navController.popBackStack() }
                        }
                        composable(ROOT) {
                            ListOfHousesScreen(viewModel) { houseUrl ->
                                navController.navigate(HOUSE_PATH.replace("{url}", URLEncoder.encode(houseUrl,"utf-8")))
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
                        ErrorType.LOAD_FAILURE_NETWORK -> getString(R.string.failure_network)
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