package com.example.lab5

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab5.ui.AirportsViewModel
import com.example.lab5.ui.AppBar
import com.example.lab5.ui.FavoritesScreen
import com.example.lab5.ui.RoutesScreen
import com.example.lab5.ui.SearchScreen
import com.example.lab5.ui.theme.Lab5Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    var airportsViewModel: AirportsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Lab5Theme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: AirportsViewModel = viewModel(
                        it,
                        "AirportsViewModel",
                        UserViewModelFactory(LocalContext.current.applicationContext as Application)
                    )
                    airportsViewModel = viewModel
                    airportsViewModel!!.Load()
                    AirportsApp(viewModel)
                }
            }
        }
    }
}

enum class Screens {
    Favorites,
    Search,
    Routes
}

class UserViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AirportsViewModel( application ) as T
    }
}

@Composable
fun AirportsApp(
    airportsViewModel: AirportsViewModel = viewModel(),
    navHostController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val uiState = airportsViewModel.uiState.collectAsState()
    val queryCoroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AppBar()

        Row(Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Spacer(modifier = Modifier.weight(0.05f))
            OutlinedTextField(
                value = uiState.value.query,
                onValueChange = { value ->
                    queryCoroutineScope.launch {
                        airportsViewModel.SetQuery(value)
                        if (value.length == 0) {
                            airportsViewModel.SetRoute(Screens.Favorites.name)
                            navHostController.navigate(Screens.Favorites.name)
                        } else if (navHostController.currentBackStackEntry?.destination?.route != Screens.Search.name) {
                            airportsViewModel.SetRoute(Screens.Search.name)
                            navHostController.navigate(Screens.Search.name)
                        }
                        airportsViewModel.Save()
                    }
                },
                singleLine = true,
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
                label = { Text(stringResource(R.string.enter_departure_airport), color = Color(0, 102, 102)) },
                shape = RoundedCornerShape(32.dp),
                textStyle = TextStyle(color = Color(0, 102, 102)),
                modifier = Modifier.weight(0.9f)
            )
            Spacer(modifier = Modifier.weight(0.05f))
        }



        NavHost(
            navController = navHostController,
            startDestination = airportsViewModel.uiState.collectAsState().value.route
        ) {
            composable(route = Screens.Favorites.name) {
                val coroutineScope = rememberCoroutineScope()
                FavoritesScreen(
                    favorites = airportsViewModel.GetFavorites(),
                    changeIsFavoriteButtonClick = { route ->
                        coroutineScope.launch {
                            airportsViewModel.deleteFavorite(
                                departureAirportCode = route.departureAirportCode,
                                arrivalAirportCode = route.arrivalAirportCode
                            )
                        }
                    }
                )
            }
            composable(route = Screens.Search.name) {
                val coroutineScope = rememberCoroutineScope()
                SearchScreen(
                    departureAirportCodesAndNamesList = airportsViewModel.GetSearchAirports(),
                    onRowClick = { departureAirportCode ->
                        coroutineScope.launch {
                            airportsViewModel.SetQuery(departureAirportCode)
                            airportsViewModel.SetRoute(Screens.Routes.name)
                            navHostController.navigate(Screens.Routes.name)
                            airportsViewModel.Save()
                        }
                    },
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            composable(route = Screens.Routes.name) {
                val coroutineScope = rememberCoroutineScope()
                RoutesScreen(
                    departureAirportCode = uiState.value.query,
                    routes = airportsViewModel.GetArrivalAirports(),
                    changeIsFavoriteButtonClick = { route ->
                        coroutineScope.launch {
                            if (route.isFavorite) {
                                airportsViewModel.deleteFavorite(
                                    departureAirportCode = route.departureAirportCode,
                                    arrivalAirportCode = route.arrivalAirportCode
                                )
                            } else {
                                airportsViewModel.addFavorite(
                                    departureAirportCode = route.departureAirportCode,
                                    arrivalAirportCode = route.arrivalAirportCode
                                )
                            }
                        }
                    }
                )
            }
        }

    }
}

@Preview(
    showBackground = true,
    heightDp = 915,
    widthDp = 412
)
@Composable
fun GreetingPreview() {
    Lab5Theme {
        AirportsApp()
    }
}