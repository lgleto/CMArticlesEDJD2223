package ipca.example.cmdaily

import org.json.JSONObject
import java.util.*

class Article {

    var title       : String? = null
    var url         : String? = null
    var publishedAt : Date? = null
    var content     : String? = null
    var urlToImage  : String? = null

    fun toJSON() : JSONObject{
        // TODO:
        return JSONObject()
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject) : Article {
            return Article().apply {
                title           = jsonObject.getString("title")
                url             = jsonObject.getString("url")
                publishedAt     = Date() //jsonObject.getString("publishedAt")
                content         = jsonObject.getString("content")
                urlToImage      = jsonObject.getString("urlToImage")
            }
        }
    }
}