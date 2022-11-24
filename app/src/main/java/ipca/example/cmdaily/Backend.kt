package ipca.example.cmdaily

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import ipca.example.cmdaily.models.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object Backend {

    val client = OkHttpClient()

    fun fetchLatestArticles (scope: CoroutineScope, country : String, category: String?, callback: (ArrayList<Article>)->Unit )  {
        scope.launch (Dispatchers.IO){

            var url = "https://newsapi.org/v2/top-headlines?country=$country&apiKey=1765f87e4ebc40229e80fd0f75b6416c"
            category?.let {
                url = "https://newsapi.org/v2/top-headlines?country=$country&category=$it&apiKey=1765f87e4ebc40229e80fd0f75b6416c"
            }
            val request = Request.Builder()
                .url(url)
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
                        callback(articles)
                    }
                }
            }
        }
    }

    fun fetchLatestArticles ( country : String, category: String?) : LiveData<List<Article>> = liveData(IO) {
        var url = "https://newsapi.org/v2/top-headlines?country=$country&apiKey=1765f87e4ebc40229e80fd0f75b6416c"
        category?.let {
            url = "https://newsapi.org/v2/top-headlines?country=$country&category=$it&apiKey=1765f87e4ebc40229e80fd0f75b6416c"
        }
        val request = Request.Builder()
            .url(url)
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
                // articles
                emit(articles)
            }
        }
    }

    fun fetchImage(scope: CoroutineScope, url:String, callback:(Bitmap)->Unit) {
        scope.launch (Dispatchers.IO){
            if (!url.contains("http")) return@launch
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute().use { response ->
                val inputStream = response.body?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                scope.launch (Dispatchers.Main){
                    callback(bitmap)
                }
            }
        }
    }

}