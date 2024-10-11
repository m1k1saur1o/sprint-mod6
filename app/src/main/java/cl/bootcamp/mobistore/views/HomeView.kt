package cl.bootcamp.mobistore.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cl.bootcamp.mobistore.R
import cl.bootcamp.mobistore.components.PhoneCard
import cl.bootcamp.mobistore.util.isInternetAvailable
import cl.bootcamp.mobistore.viewModels.PhoneViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    phoneVM: PhoneViewModel
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (isInternetAvailable(context)) {
            phoneVM.getAllApi()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home_title),
                        color = colorResource(id = R.color.white)
                    )
                }
            )
        }
    ) { paddingValues ->
        HomeViewContent(
            paddingValues = paddingValues,
            navController = navController,
            phoneVM = phoneVM
        )
    }
}

@Composable
fun HomeViewContent(
    paddingValues: PaddingValues,
    navController: NavController,
    phoneVM: PhoneViewModel
) {

    val phones by phoneVM.phones.collectAsState(listOf())

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(paddingValues),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(phones) { item ->
            PhoneCard(
                name = item.name,
                price = item.price,
                image = item.image,
                onClick = { navController.navigate("Details/${item.id}") }
            )
        }

    }
}