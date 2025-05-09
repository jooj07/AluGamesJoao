package br.com.alura.alugames.modelo

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Scanner
import kotlin.random.Random

//todas as classes para guardar informações é recomendado usar data classes
data class Gamer(var nome: String, var email: String): Recomendavel {
    var dataNascimentoInterno: String? = null
    var usuarioInterno: String? = null // pode ser nulo
        set(value) {
            field = value
            if (idInterno.isNullOrBlank()) {
                criarIdInterno()
            }
        }
    var idInterno: String? = null
        private set
    var plano: Plano = PlanoAvulso("BRONZE")
    val jogosBuscados = mutableListOf<Jogo?>()
    val jogosAlugados = mutableListOf<Aluguel>()
    private val listaNotas: MutableList<Int> = mutableListOf<Int>()
    val jogosRecomendados: MutableList<Jogo> = mutableListOf<Jogo>()

    override val media: Double
        get() = listaNotas.average()

    override fun recomendar(nota: Int) {
        if(nota > 10 || nota < 0) {
            throw IllegalArgumentException("Nota inválida, apenas notas entre 1 a 10 são aceitas!")
        }
        listaNotas.add(nota)
    }

    fun recomendarJogo(jogo: Jogo, nota: Int) {
        jogo.recomendar(nota)
        jogosRecomendados.add(jogo)
    }

    //uma segunda possibilidade de criar um construtor
    // dentro do this passamos quais parâmetros do construtor primário vamos usar
    constructor(
        nome: String,
        email: String,
        dataNascimentoConstrutorSecundario: String,
        usuarioConstSec: String
    ) : this(nome, email) {
        this.dataNascimentoInterno = dataNascimentoConstrutorSecundario
        this.usuarioInterno = usuarioConstSec
        criarIdInterno()
    }

    init {
        if (nome.isNullOrBlank()) {
            throw IllegalArgumentException("Nome está em branco!")
        }
        this.validarEmail()
    }

    override fun toString(): String {
        return "Gamer(\n" +
                "nome='$nome\n', " +
                "email='$email\n', " +
                "dataNascimentoInterno=$dataNascimentoInterno\n, " +
                "usuarioInterno=$usuarioInterno\n, " +
                "idInterno=$idInterno\n" +
                "Reputação=$media\n)"
    }

    fun criarIdInterno() {
        val numero = Random.nextInt(10000)
        val tag = String.format("%04d", numero) // 4 digitos

        idInterno = "$usuarioInterno#$tag"
    }

    fun validarEmail(): String {
        val regex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        if (regex.matches(email)) {
            return email
        } else {
            throw IllegalArgumentException("Email inválido")
        }
    }

    fun alugaJogo(jogo: Jogo, periodo: Periodo): Aluguel {
        //como ta dentro da classe Gamer, eu posso usar o this
        val aluguel = Aluguel(this, jogo, periodo)
        jogosAlugados.add(aluguel)
        return aluguel
    }

    fun jogosDoMes(mes:Int): List<Jogo> {
        return jogosAlugados
            .filter { aluguel ->  aluguel.periodo.dataInicial.monthValue == mes}
            .map { aluguel ->  aluguel.jogo}
    }


    companion object {
        fun criarGamer(leitura: Scanner): Gamer {
            println("Boas vindas ao AluGames! Vamos fazer seu cadastro. Digite seu nome:")
            val nome = leitura.nextLine()
            println("Digite seu e-mail:")
            val email = leitura.nextLine()
            println("Deseja completar seu cadastro com usuário e data de nascimento? (S/N)")
            val opcao = leitura.nextLine()

            if (opcao.equals("s", true)) {
                var nascimento: String? = null
                while (nascimento == null) {
                    println("Digite sua data de nascimento(DD/MM/AAAA):")
                    val entrada = leitura.nextLine()

                    val validarData = runCatching {
                        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        val data = LocalDate.parse(entrada, formatter)
                        //No Kotlin, require(condição) serve para validar se um valor está correto.
                        // Se a condição for false, ele joga uma IllegalArgumentException e já corta o rolê ali mesmo.
                        require(formatter.format(data) == entrada && data <= LocalDate.now()) { "data inválida" }
                        entrada // Retorna a data válida
                    }

                    validarData.onFailure {
                        println("Data inválida, tente novamente.")
                    }.onSuccess {
                        nascimento = it
                    }
                }

                println("Digite seu nome de usuário:")
                val usuario = leitura.nextLine()
                return Gamer(nome, email, nascimento!!, usuario)
            } else {
                return Gamer(nome, email)
            }
        }
    }

}
