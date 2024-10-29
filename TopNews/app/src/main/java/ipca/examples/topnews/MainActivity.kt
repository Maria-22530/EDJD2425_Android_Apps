package ipca.examples.topnews

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.examples.topnews.models.HomeView
import ipca.examples.topnews.ui.theme.ArticleDetail
import ipca.examples.topnews.ui.theme.TopNewsTheme
import ipca.examples.topnews.ui.theme.headerDetail

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TopNewsTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        headerDetail("My Header")  // Chame a função headerDetail aqui
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.Home.route) {
                            HomeView(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        composable(route = Screen.ArticleDetail.route) {
                            val url = it.arguments?.getString("articleUrl") ?: ""
                            ArticleDetail(
                                modifier = Modifier.padding(innerPadding), url = url
                            )
                        }
                        composable(route = Screen.ImageDetail.route) {
                            val url = it.arguments?.getString("urlToImage")
                            ArticleDetail(
                                modifier = Modifier.padding(innerPadding),
                                url = url ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen (val route:String){
    object Home: Screen(route = "Home")
    object ArticleDetail : Screen(route = "article_detail/{articleUrl}")
    object ImageDetail : Screen(route = "image_detail/{urlToImage}")
}