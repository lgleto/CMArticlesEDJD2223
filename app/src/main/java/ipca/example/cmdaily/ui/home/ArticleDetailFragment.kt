package ipca.example.cmdaily.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ipca.example.cmdaily.R
import ipca.example.cmdaily.databinding.FragmentArticleDetailBinding
import ipca.example.cmdaily.models.Article
import org.json.JSONObject

class ArticleDetailFragment : Fragment() {

    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private var article: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = Article.fromJSON(JSONObject(it.getString(ARTICLE_STRING)))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        article?.url?.let {
            binding.webView.loadUrl(it)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_web_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_share ->{
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, article?.url)
                intent.putExtra(Intent.EXTRA_TITLE, article?.title)
                intent.setType("text/plain");
                val shareIntent = Intent.createChooser(intent, article?.title)
                startActivity(shareIntent)
                true
            }
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            else ->{
                false
            }
        }
    }


    companion object {

        const val ARTICLE_STRING = "article_string"

        @JvmStatic
        fun newInstance(articleJsonString: String) =
            ArticleDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARTICLE_STRING, articleJsonString)
                }
            }
    }
}