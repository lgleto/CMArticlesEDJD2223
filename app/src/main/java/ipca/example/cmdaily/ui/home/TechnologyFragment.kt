package ipca.example.cmdaily.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ipca.example.cmdaily.Backend
import ipca.example.cmdaily.R
import ipca.example.cmdaily.databinding.FragmentHomeBinding
import ipca.example.cmdaily.models.Article
import ipca.example.cmdaily.toHourDateString

class TechnologyFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // model
    var articles = arrayListOf<Article>()

    val adapter = ArticleAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listViewArticles.adapter = adapter

        Backend.fetchLatestArticles(lifecycleScope, getString(R.string.country), "technology"){
            articles = it
            adapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

                //val intent = Intent(this@MainActivity, ArticleWebDetailActivity::class.java)
                //intent.putExtra(EXTRA_ARTICLE_STRING, articles[position].toJSON().toString() )
                //startActivity(intent)

                val bundle = Bundle()
                bundle.putString(ArticleDetailFragment.ARTICLE_STRING,  articles[position].toJSON().toString())
                findNavController().navigate(R.id.action_technologyFragment_to_articleDetailFragment,bundle)

            }


            return rootView
        }
    }


}