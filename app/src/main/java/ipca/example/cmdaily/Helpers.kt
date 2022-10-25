package ipca.example.cmdaily

import java.text.SimpleDateFormat
import java.util.*

fun String.parseDate() : Date {
    //2022-10-25T12:22:59Z
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = format.parse(this)
    return date
}

fun Date.toHourDateString() : String {
    val format = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())
    return format.format(this)
}

fun Date.toServerDateString() : String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    return format.format(this)
}