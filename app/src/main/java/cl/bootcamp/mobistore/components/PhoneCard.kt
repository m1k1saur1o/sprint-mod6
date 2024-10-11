package cl.bootcamp.mobistore.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cl.bootcamp.mobistore.R
import coil.compose.rememberAsyncImagePainter

@Composable
fun PhoneCard(
    name: String,
    price: Int,
    image: String,
    onClick: () -> Unit
) {

    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .height(300.dp)
            .width(300.dp)
            .fillMaxSize()
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = null,
                modifier = Modifier
                    .size(180.dp)
                    .border(2.dp, colorResource(id = R.color.black)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = name,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$${price}",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}