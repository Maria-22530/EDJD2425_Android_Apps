package ipca.examples.topnews.models

import ipca.examples.topnews.parseDate
import org.json.JSONObject
import java.util.Date

class Article (
    var title: String? = null,
    var description: String? = null,
    var urlToImage: String? = null,
    var url: String? = null,
    var publishedAt: Date? = null,) {

    companion object {
        fun fromJson(json: JSONObject): Article {
            return Article(
                title = json.getString("title"),
                description = json.getString(("description")),
                urlToImage = json.getString("urlToImage"),
                url = json.getString("url"),
//                                publishedAt = articleJson.getDate("publishedAt")
                publishedAt = json.getString("publishedAt").parseDate()

            )
        }
    }}