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
import java.util.*

class MainActivity : AppCompatActivity() {

    // model
    var articles = arrayListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        articles.add(Article("Cotrim de Figueiredo: mudanças no IRS são “uma cócega no enorme sufoco das famílias", Date(),"Use as ferramentas de partilha que encontra na página de artigo.\n" +
                "Todos os conteúdos do PÚBLICO são protegidos por Direitos de Autor ao abrigo da legislação portuguesa, conforme os Termos e Condições.Os assinantes do jornal PÚBLICO têm direito a oferecer até 6 artigos exclusivos por mês a amigos ou familiares, usando a opção “Oferecer artigo” no topo da página. Apoie o jornalismo de qualidade do PÚBLICO.\n" +
                "https://www.publico.pt/2022/10/13/politica/entrevista/cotrim-figueiredo-mudancas-irs-sao-cocega-enorme-sufoco-familias-2023778\n" +
                "\n" +
                "A IL já considerou que este é um mau orçamento. Diz que há um optimismo excessivo nas previsões económicas e que os portugueses vão continuar a perder poder de compra. O que é que faria de diferente se tivesse de fazer um orçamento?\n" +
                "Este orçamento foi uma enorme decepção. E não é só por uma questão de optimismo ou falta dele. É um orçamento que demonstra um enorme enfoque na dívida pública e não responde àquilo que eu chamo “uma dúvida pública”, que é porque é que Portugal não cresce tanto como outros países que estão no nosso campeonato.",null))
        articles.add(Article("PPP na alta velocidade: privados vão construir e manter linha a troco de rendas", Date(),"Use as ferramentas de partilha que encontra na página de artigo.\n" +
                "Todos os conteúdos do PÚBLICO são protegidos por Direitos de Autor ao abrigo da legislação portuguesa, conforme os Termos e Condições.Os assinantes do jornal PÚBLICO têm direito a oferecer até 6 artigos exclusivos por mês a amigos ou familiares, usando a opção “Oferecer artigo” no topo da página. Apoie o jornalismo de qualidade do PÚBLICO.\n" +
                "https://www.publico.pt/2022/10/13/economia/noticia/ppp-alta-velocidade-privados-vao-construir-manter-linha-troco-rendas-2023664\n" +
                "\n" +
                "Vice-presidente da IP, Carlos Fernandes, confirma que a construção e manutenção da linha entre o Porto e Soure estará a cargo de um consórcio privado que será remunerado pela capacidade da infra-estrutura.",null))

        val listViewArticles = findViewById<ListView>(R.id.listViewArticles)
        listViewArticles.adapter = ArticleAdapter()


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
            textViewArticleBody.text = articles[position].body
            textViewArticleDate.text = articles[position].pubDate.toString()

            rootView.setOnClickListener {
                Log.d("MainActivity", articles[position].title?:"" )
                val intent = Intent(this@MainActivity, ArticleDetailActivity::class.java)
                intent.putExtra("title", articles[position].title)
                intent.putExtra("body", articles[position].body)
                startActivity(intent)

            }


            return rootView
        }
    }

}