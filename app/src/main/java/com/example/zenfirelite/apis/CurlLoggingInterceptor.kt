package com.example.zenfirelite.apis

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CurlLoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Build the cURL command
        val curlCommand = StringBuilder("curl -X ${request.method} ")
        request.headers.forEach { header ->
            curlCommand.append("-H \"${header.first}: ${header.second}\" ")
        }
        request.body?.let { body ->
            val buffer = okio.Buffer()
            body.writeTo(buffer)
            curlCommand.append("-d '${buffer.readUtf8()}' ")
        }
        curlCommand.append("\"${request.url}\"")

        // Log the cURL command to Logcat
        Log.d("CurlLogger", curlCommand.toString())

        return chain.proceed(request)
    }
}
