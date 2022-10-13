package ipca.example.cmdaily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ArticleDetailActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")

        findViewById<TextView>(R.id.textViewTitle).text = title
        findViewById<TextView>(R.id.textViewBody).text = body

    }


}