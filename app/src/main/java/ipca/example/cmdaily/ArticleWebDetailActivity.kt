package ipca.example.cmdaily

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.widget.Toast
import org.json.JSONObject

class ArticleWebDetailActivity : AppCompatActivity() {

    lateinit var article : Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_web_detail)

        article = Article.fromJSON(JSONObject( intent.getStringExtra(MainActivity.EXTRA_ARTICLE_STRING)))
        title = article.title
        article.url?.let {
            findViewById<WebView>(R.id.webView).loadUrl(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_web_detail,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_share -> {
                //Toast.makeText(this,"hello",Toast.LENGTH_LONG).show()
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, article.url)
                intent.putExtra(Intent.EXTRA_TITLE, article.title)
                intent.setType("text/plain");
                val shareIntent = Intent.createChooser(intent, article.title)

                startActivity(shareIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}