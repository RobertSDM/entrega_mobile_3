package br.com.alu.challengeentrega.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import br.com.alu.challengeentrega.R
import br.com.alu.challengeentrega.model.Usuario
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class AnaliseMercadoActivity : Activity() {
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.analise_mercado_layout)

        val url =
            "https://backend-challenge-mobile.vercel.app/find/by/email/${intent.extras?.getString("email")}"

        val cliques = findViewById<TextView>(R.id.cliques_view)
        val tempoMedioNav = findViewById<TextView>(R.id.textView3)

        val taxaRedirecionamento = findViewById<TextView>(R.id.taxa_redirecionamento)
        val progress = findViewById<ProgressBar>(R.id.progressBar)

        val gson = Gson()
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR-LOGIN", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val jsonContent = response.body?.string()
                    Log.v("API-RESPONSE", jsonContent!!)
                    val usuario: Usuario =
                        gson.fromJson(jsonContent, Usuario::class.java)
                    runOnUiThread {
                        cliques.text = usuario.empresa?.analise_mercado!!.cliques[0].cliques.toString()
                        tempoMedioNav.text =
                            usuario.empresa?.analise_mercado?.media_navegacao.toString() + "min"
                        taxaRedirecionamento.text =
                            usuario.empresa?.analise_mercado?.taxa_redirecionamento.toString() + "%"
                        progress.progress =
                            usuario.empresa?.analise_mercado?.taxa_redirecionamento!!.toInt()
                    }
                } else {
                    runOnUiThread {
                        val toast = Toast.makeText(
                            this@AnaliseMercadoActivity,
                            "Erro ao realizar a requisição para o servidor",
                            Toast.LENGTH_LONG
                        )
                        toast.show()
                    }
                }
            }
        }
        client.newCall(request).enqueue(response)

    }
}