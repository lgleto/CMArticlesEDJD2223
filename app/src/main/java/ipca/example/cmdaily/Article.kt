package ipca.example.cmdaily

import java.util.*

class Article {

    var title : String? = null
    var pubDate : Date? = null
    var body : String? = null
    var imageUrl : String? = null

    constructor(title: String?, pubDate: Date?, body: String?, imageUrl: String?) {
        this.title = title
        this.pubDate = pubDate
        this.body = body
        this.imageUrl = imageUrl
    }

}