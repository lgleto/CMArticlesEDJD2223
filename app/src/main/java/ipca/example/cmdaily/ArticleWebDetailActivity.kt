package ipca.example.cmdaily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import org.json.JSONObject

class ArticleWebDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_web_detail)

        val article = Article.fromJSON(JSONObject( intent.getStringExtra(MainActivity.EXTRA_ARTICLE_STRING)))

        title = article.title

        article.url?.let {
            findViewById<WebView>(R.id.webView).loadUrl(it)
        }

    }

}