package br.com.alu.challengeentrega.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alu.challengeentrega.R
import br.com.alu.challengeentrega.model.Usuario
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

class ConfiguracaoActivity : AppCompatActivity(R.layout.login_layout){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configuracao_layout)

        val excluirConta = findViewById<TextView>(R.id.text_excluir_conta_option)
        val url = "https://backend-challenge-mobile.vercel.app/account/remove/${intent.getStringExtra("email")}"
        val loginActivity = Intent(this@ConfiguracaoActivity, LoginActivity::class.java)
        val client = OkHttpClient()

        excluirConta.setOnClickListener {
            val response = object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ERROR-LOGIN", e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 200) {
                        startActivity(loginActivity)
                    } else {
                        Log.v("API-ERROR", response.body?.string().toString())
                        runOnUiThread{
                            val toast = Toast.makeText(
                                this@ConfiguracaoActivity,
                                "Erro ao excluir conta", Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    }
                }
            }

            client.newCall(
                Request.Builder()
                .url(url)
                .delete()
                .build()).enqueue(response)
        }

    }
}