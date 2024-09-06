package br.com.alu.challengeentrega.model

data class Usuario(
    val id: String,
    val email: String,
    val senha: String,
    val nome: String,
    val empresa: Empresa?
)

data class Empresa(
    val id: String,
    val usuario_id: String,
    val usuario: Usuario,
    val analise_mercado: AnaliseMercado?
)

data class AnaliseMercado(
    val id: Int,
    val empresa_id: String,
    val media_navegacao: Int,
    val taxa_redirecionamento: Int,
    val empresa: Empresa,
    val cliques: List<Cliques>
)

data class Cliques(
    val id: Int,
    val cliques: Int,
    val analise_mercado_id: Int,
    val analise_mercado: AnaliseMercado
)
