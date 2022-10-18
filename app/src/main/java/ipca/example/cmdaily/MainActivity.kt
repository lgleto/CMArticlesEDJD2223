package ipca.example.cmdaily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    // model
    var articles = arrayListOf<Article>()

    val adapter = ArticleAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewArticles = findViewById<ListView>(R.id.listViewArticles)
        listViewArticles.adapter = adapter

        lifecycleScope.launch (Dispatchers.IO){
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
                    val jsonArrayArticles = jsonObject.getJSONArray("articles")
                    for( index in 0 until  jsonArrayArticles.length()){
                        val jsonObjectArticle = jsonArrayArticles.getJSONObject(index)
                        val article = Article.fromJSON(jsonObjectArticle)
                        articles.add(article)
                    }
                    lifecycleScope.launch (Dispatchers.Main){
                        adapter.notifyDataSetChanged()
                    }

                }

            }
        }


    }

    inner class ArticleAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return articles.size
        }

        override fun getItem(position: Int): Any {
            return articles[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_article,parent,false)

            val textViewArticleTitle = rootView.findViewById<TextView>(R.id.textViewArticleTitle)
            val textViewArticleBody = rootView.findViewById<TextView>(R.id.textViewArticleBody)
            val textViewArticleDate = rootView.findViewById<TextView>(R.id.textViewArticleDate)
            val imageViewArticleImage = rootView.findViewById<ImageView>(R.id.imageViewArticleImage)

            textViewArticleTitle.text = articles[position].title
            textViewArticleBody.text = articles[position].content
            textViewArticleDate.text = articles[position].publishedAt.toString()

            rootView.setOnClickListener {
                Log.d("MainActivity", articles[position].title?:"" )
                val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                intent.putExtra("title", articles[position].title)
                intent.putExtra("body", articles[position].content)
                startActivity(intent)

            }


            return rootView
        }
    }

}