package com.example.financemanager

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpGetRequest : AsyncTask<String, Void, String>() {

  override fun doInBackground(vararg urls: String): String {
    val url = URL(urls[0])
    val connection = url.openConnection() as HttpURLConnection

    try {
      // Устанавливаем заголовок User-Agent
      connection.setRequestProperty(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36"
      )

      val inputStream = connection.inputStream
      val reader = BufferedReader(InputStreamReader(inputStream))
      val response = StringBuilder()
      var line: String?

      while (reader.readLine().also { line = it } != null) {
        response.append(line)
      }

      return response.toString()
    } finally {
      connection.disconnect()
    }
  }
}