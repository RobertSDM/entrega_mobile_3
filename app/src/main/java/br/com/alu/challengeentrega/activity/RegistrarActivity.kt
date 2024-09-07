package br.com.alu.challengeentrega.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.alu.challengeentrega.R
import br.com.alu.challengeentrega.utils.Validations
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException

class RegistrarActivity : AppCompatActivity(R.layout.registrar_layout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val linkLoginTela = findViewById<TextView>(R.id.entre_text)
        val btnEntrar = findViewById<Button>(R.id.button_registrar)
        val nome = findViewById<EditText>(R.id.nome_registrar)
        val email = findViewById<EditText>(R.id.email_registrar)
        val senha = findViewById<EditText>(R.id.senha_registrar)
        val confirmarSenha = findViewById<EditText>(R.id.confirmar_senha_registrar)
        val loginActivity = Intent(this@RegistrarActivity, LoginActivity::class.java)

        val gson = Gson()
        val client = OkHttpClient()

        val registerURL = "https://backend-challenge-mobile.vercel.app/auth/register"

        btnEntrar.setOnClickListener{
            if(!Validations.validadeEmail(email.text.toString())){
                val toast = Toast.makeText(
                    this@RegistrarActivity,
                    "O email é inválido", Toast.LENGTH_LONG
                )
                toast.show()
                return@setOnClickListener
            }else if(!Validations.validateTwoStrings(senha.text.toString(), confirmarSenha.text.toString()) &&
                (senha.text.toString().isEmpty() || confirmarSenha.text.toString().isEmpty())){

                val toast = Toast.makeText(
                    this@RegistrarActivity,
                    "As senhas não são iguais", Toast.LENGTH_LONG
                )
                toast.show()
                return@setOnClickListener
            }

            val body = """
                {
                    "nome" : "${nome.text}",
                    "email": "${email.text}",
                    "senha": "${senha.text}"
                }
            """.trimIndent()

            val request = Request.Builder()
                .url(registerURL)
                .post(body.toRequestBody("application/json".toMediaTypeOrNull()))
                .build()

            val response = object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ERROR-REGISTRAR", e.message.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Log.v("CONTEUDO", response.body?.string()!!)
                    if(response.code == 200){
                        loginActivity.putExtra("email", email.text.toString())
                        startActivity(loginActivity)
                    }
                }
            }
            client.newCall(request).enqueue(response)
        }

        linkLoginTela.setOnClickListener{
            startActivity(loginActivity)
        }

    }

}