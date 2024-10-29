package ipca.examples.topnews.ui.theme

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImage
import ipca.examples.topnews.R
import ipca.examples.topnews.Screen
import ipca.examples.topnews.encodeURL
import ipca.examples.topnews.models.Article
import ipca.examples.topnews.toYYYYMMDD
import ipca.examples.topnews.ui.theme.TopNewsTheme
import java.util.Date


@Composable
fun RowArticle(modifier: Modifier = Modifier,
               article: Article,
               onImageClick : (String)->Unit) {
    val context = LocalContext.current //TBD Make it also open in app

    Row(modifier = modifier) {
        article.urlToImage?.let {
            AsyncImage(
                model = it,
                contentDescription = "image article",
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(5.dp)
                    .clickable { //TBD Make it also open in App
                        //val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.urlToImage))
                        //context.startActivity(intent)

                        article.urlToImage?.encodeURL()?.let { it1 -> onImageClick(it1) }
                    },
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Image(
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(6.dp),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "image article",
                contentScale = ContentScale.Crop,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)) {
            Text(text = article.title?: "",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,)
            Text(text = article.description?: "",
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,)
            Text(text = article.publishedAt?.toYYYYMMDD()?:"")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RowArticlePreview() {
    TopNewsTheme {
        RowArticle(article = Article(
            "Title",
            "description",
            null,
            "url",
            Date()
        )){

        }
    }
}