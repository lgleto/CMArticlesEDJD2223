package ipca.example.cmdaily

import org.json.JSONObject
import java.util.*

class Article {

    var title       : String? = null
    var url         : String? = null
    var publishedAt : Date?   = null
    var content     : String? = null
    var urlToImage  : String? = null

    fun toJSON() : JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("title"      , title      )
        jsonObject.put("url"        , url        )
        jsonObject.put("publishedAt", publishedAt?.toServerDateString())
        jsonObject.put("content"    , content    )
        jsonObject.put("urlToImage" , urlToImage )

        return jsonObject
    }

    companion object {
        fun fromJSON(jsonObject: JSONObject) : Article {
            return Article().apply {
                title       = jsonObject.getString("title"      )
                url         = jsonObject.getString("url"        )
                publishedAt = jsonObject.getString("publishedAt").parseDate()
                content     = jsonObject.getString("content"    )
                urlToImage  = jsonObject.getString("urlToImage" )
            }
        }
    }

}