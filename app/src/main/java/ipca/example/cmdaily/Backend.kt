package ipca.example.cmdaily

import android.content.Context
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object Backend {
    /*
    fun fetchLatestArticles (scope: CoroutineScope, country : String ) {
        scope.launch (Dispatchers.IO){
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://newsapi.org/v2/top-headlines?country=pt&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val result = response.body!!.string()

                val jsonObject = JSONObject(result)
                val jsonObjectStatus = jsonObject.getString("status")
                if (jsonObjectStatus == "ok"){
                    var articles = arrayListOf<Article>()
                    val jsonArrayArticles = jsonObject.getJSONArray("articles")
                    for( index in 0 until  jsonArrayArticles.length()){
                        val jsonObjectArticle = jsonArrayArticles.getJSONObject(index)
                        val article = Article.fromJSON(jsonObjectArticle)
                        articles.add(article)
                    }
                    scope.launch (Dispatchers.Main){
                        return articles
                    }

                }

            }
        }
    }*/

}