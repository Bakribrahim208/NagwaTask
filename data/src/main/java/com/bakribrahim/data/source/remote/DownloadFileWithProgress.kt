package com.bakribrahim.data.source.remote

import com.bakribrahim.data.model.ProgressResponseBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class DownloadFileWithProgress @Inject constructor() {

   lateinit var progressListener: ProgressListener

    @Throws(Exception::class)
    operator fun invoke(fileUrl: String) {
        try {


            val request = Request.Builder()
                .url(fileUrl)
                .build()


            val client = OkHttpClient.Builder()
                .addNetworkInterceptor { chain: Interceptor.Chain ->
                    val originalResponse = chain.proceed(chain.request())
                    originalResponse.newBuilder()
                        .body(ProgressResponseBody(originalResponse.body!!, progressListener))
                        .build()
                }
                .build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) throw IOException(" Unexpected  code $response")


            // download file
            response.body?.string()

        } catch (e: Exception) {
            println("print exception")
            println("print exception")
            progressListener.failure(e.message!!)
             e.printStackTrace();
        }
    }

    interface ProgressListener {

        public fun update(bytesRead: Long, contentLength: Long, done: Boolean)
        public fun failure(errorMessage:String)

    }

}