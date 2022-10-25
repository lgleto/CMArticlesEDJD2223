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

        Backend.fetchLatestArticles(lifecycleScope, "pt"){
            articles = it
            adapter.notifyDataSetChanged()
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
            textViewArticleDate.text = articles[position].publishedAt?.toHourDateString()

            articles[position].urlToImage?.let {
                Backend.fetchImage(lifecycleScope, it){ bitmap ->
                    imageViewArticleImage.setImageBitmap(bitmap)
                }
            }

            rootView.setOnClickListener {
                Log.d("MainActivity", articles[position].title?:"" )
                //val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                //intent.putExtra("title", articles[position].title)
                //intent.putExtra("body", articles[position].content)

                val intent = Intent(this@MainActivity, ArticleWebDetailActivity::class.java)
                intent.putExtra(EXTRA_ARTICLE_STRING, articles[position].toJSON().toString() )
                startActivity(intent)

            }


            return rootView
        }
    }

    companion object {
        const val EXTRA_ARTICLE_STRING = "article_string"
    }

}