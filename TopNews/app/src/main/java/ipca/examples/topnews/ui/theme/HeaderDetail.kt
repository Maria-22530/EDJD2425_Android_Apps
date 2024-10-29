package ipca.examples.topnews.ui.theme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import okhttp3.internal.http2.Header

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun headerDetail(title: String){
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        },
        colors = TopAppBarColors(
            containerColor = Color.Magenta,
            scrolledContainerColor = Color.Blue,
            navigationIconContentColor = Color.Cyan,
            titleContentColor = Color.Gray,
            actionIconContentColor = Color.Yellow
        ),
       //backgroundColor = MaterialTheme.colorScheme.primary,
       // contentColor = MaterialTheme.colorScheme.onPrimary,
       // elevation = 8.dp
    )
}

@Preview()
@Composable
fun DefaultPreview() {
    headerDetail("My Header")

}