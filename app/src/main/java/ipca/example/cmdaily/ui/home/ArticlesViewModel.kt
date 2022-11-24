package ipca.example.cmdaily.ui.home

import androidx.lifecycle.*
import ipca.example.cmdaily.Backend
import ipca.example.cmdaily.R
import ipca.example.cmdaily.models.Article
import java.util.Locale.Category

class ArticlesViewModel : ViewModel() {

    private val category : MutableLiveData<String> = MutableLiveData()

    fun setCategory(category : String){
        val update = category
        if (this.category.value == update) return
        this.category.value = update
    }

    val getArticles : LiveData<List<Article>> =
        Transformations.switchMap(category){
            Backend.fetchLatestArticles( "pt" , it)
        }

}