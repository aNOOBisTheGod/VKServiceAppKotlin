package api

import com.google.gson.Gson
import pojo.Request
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

open class Api {
    fun fetchData(): Request {
        /** open connection to API */
        val url = URL("https://mobile-olympiad-trajectory.hb.bizmrg.com/semi-final-data.json")
        val connection = url.openConnection() as HttpsURLConnection
        /** checking if everything is fine */
        if (connection.responseCode == 200) {
            val inputSystem = connection.inputStream
            val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
            val request = Gson().fromJson(inputStreamReader, Request::class.java)
            inputStreamReader.close()
            inputSystem.close()
            return request
        } else {
            throw Exception("No internet connection")
        }
    }
}