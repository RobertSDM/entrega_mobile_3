package br.com.alu.challengeentrega.model

data class Empresa(
    val id: String,
    val usuario_id: String,
    val usuario: Usuario,
    val analise_mercado: AnaliseMercado?
)