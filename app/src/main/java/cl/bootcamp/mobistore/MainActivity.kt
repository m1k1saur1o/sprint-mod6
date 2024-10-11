package cl.bootcamp.mobistore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cl.bootcamp.mobistore.navigation.NavManager
import cl.bootcamp.mobistore.ui.theme.MobiStoreTheme
import cl.bootcamp.mobistore.viewModels.PhoneViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val phoneVM: PhoneViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            MobiStoreTheme {
                NavManager(phoneVM)
            }
        }
    }
}

