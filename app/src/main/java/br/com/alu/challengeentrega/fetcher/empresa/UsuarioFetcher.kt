package br.com.alu.challengeentrega.fetcher.empresa

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException

class UsuarioFetcher {
    val client = OkHttpClient()

    fun createUsuario(body: RequestBody, callback: (Response) -> Unit){
        val url = "https://backend-challenge-mobile.vercel.app/auth/register"

        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                throw IOException("Falha na requisicao -> ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }
        })
    }

    fun updateSenha(body: RequestBody, callback: (Response) -> Unit){
        val url = "https://backend-challenge-mobile.vercel.app/auth/pass_forgot"

        val request: Request = Request.Builder()
            .url(url)
            .put(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                throw IOException("Falha na requisicao -> ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }
        })
    }

    fun loginUsuario(body: RequestBody, callback: (Response) -> Unit){
        val url = "https://backend-challenge-mobile.vercel.app/auth/login"

        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                throw IOException("Falha na requisicao -> ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }
        })
    }

    fun findByEmail(email: String, callback: (Response) -> Unit){
        val url = "https://backend-challenge-mobile.vercel.app/find/by/email/${email}"

        val request: Request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                throw IOException("Falha na requisicao -> ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }
        })
    }

    fun deleteByEmail(email: String, callback: (Response) -> Unit) {
        val url = "https://backend-challenge-mobile.vercel.app/account/remove/${email}"

        val request: Request = Request.Builder()
            .url(url)
            .delete()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                throw IOException("Falha na requisicao -> ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response)
            }
        })
    }
}