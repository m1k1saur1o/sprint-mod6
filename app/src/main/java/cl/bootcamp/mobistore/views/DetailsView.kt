package cl.bootcamp.mobistore.views

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import cl.bootcamp.mobistore.viewModels.PhoneViewModel
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cl.bootcamp.mobistore.R
import cl.bootcamp.mobistore.util.isInternetAvailable
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView(
    navController: NavController,
    phoneVM: PhoneViewModel = hiltViewModel(),
    id: Int
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (isInternetAvailable(context)) {
            phoneVM.getPhoneById(id)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            phoneVM.clean()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = phoneVM.state.name,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = colorResource(id = R.color.white)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("Home") {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                            } }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            tint = colorResource(id = R.color.white),
                            contentDescription = null
                        )
                    } },
            )
        },
    ) { paddingValues ->
        ContentDetailView(
            paddingValues = paddingValues,
            phoneVM = phoneVM,
            id = id
        )
    }
}

@Composable
fun ContentDetailView(
    paddingValues: PaddingValues,
    phoneVM: PhoneViewModel,
    id: Int
) {

    val email = stringResource(id = R.string.email)
    val subject = stringResource(id = R.string.email_subject) +" ${phoneVM.state.name} - $id"
    val message = "Hola Me gustaría obtener más información del móvil ${phoneVM.state.name} de código $id. Quedo atento."
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = phoneVM.state.image),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .border(2.dp, colorResource(id = R.color.black)),
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .padding(16.dp),
            text = phoneVM.state.description
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            
            Column {
                
                Text(
                    text = stringResource(id = R.string.last_price)
                )
                
                Text(
                    text = "$${phoneVM.state.lastPrice}"
                )
            }

            Column {

                Text(
                    text = stringResource(id = R.string.price),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$${phoneVM.state.price}",
                    fontWeight = FontWeight.SemiBold
                )
            }
            
        }
        
        if (phoneVM.state.credit) {
            Text(
                text = stringResource(id = R.string.accepts_credit)
            )
        } else {
            Text(
                text = stringResource(id = R.string.does_not_accept_credit)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "Send Email"
                },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.dark_purple),
            ),
            onClick = {
                val intent = Intent(Intent.ACTION_SEND)
                val emailAddress = arrayOf(email)
                intent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, message)
                intent.type = "message/rfc822"
                context.startActivity(Intent.createChooser(intent, "Email del cliente"))
            },
        ) {
            Text(text = stringResource(id = R.string.details_button))
        }

    }
}
