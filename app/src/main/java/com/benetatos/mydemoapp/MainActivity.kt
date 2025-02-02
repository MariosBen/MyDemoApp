package com.benetatos.mydemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.benetatos.livepage_presentation.LiveGameScreen
import com.benetatos.mydemoapp.navigation.Routes
import com.benetatos.mydemoapp.ui.theme.MyDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyDemoAppTheme {
                val controller = rememberNavController()
                NavHost(navController = controller, startDestination = Routes.LIVE_PAGE ){

                    composable(Routes.LIVE_PAGE){
                        LiveGameScreen()
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyDemoAppTheme {
        Greeting("Android")
    }
}