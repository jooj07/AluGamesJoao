package br.com.alura.alugames.modelo

import java.time.LocalDate
import java.time.Period

data class Aluguel(
    val gamer: Gamer,
    val jogo: Jogo,
    val periodo: Periodo,
    val valorDoAluguel: Double = jogo.preco * periodo.emDias
    ) {
    override fun toString(): String {
        return "Aluguel do jogo ${jogo.titulo} por ${gamer.nome} pelo valor de R$ ${valorDoAluguel}"
    }
}