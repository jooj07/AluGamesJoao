package br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Aluguel
import br.com.alura.alugames.modelo.Periodo
import br.com.alura.alugames.servicos.ConsumoApi
import java.time.LocalDate

fun main() {
    val consumo = ConsumoApi()
    val listaGamers = consumo.buscaGamers()
    val listaJogosJson = consumo.buscaJogosJson()

    val gamerCaroline = listaGamers.get(3)
    val jogoResidentVllage = listaJogosJson.get(10)
    val jogoSpider = listaJogosJson.get(13)
    val jogoTheLastOfUs = listaJogosJson.get(2)

    val periodo1 = Periodo(LocalDate.now(), LocalDate.now().plusDays(7))
    val periodo2 = Periodo(LocalDate.now(), LocalDate.now().plusDays(3))
    val periodo3 = Periodo(LocalDate.now(), LocalDate.now().plusDays(10))


    gamerCaroline.alugaJogo(jogoResidentVllage,periodo1)
    gamerCaroline.alugaJogo(jogoSpider,periodo2)
    gamerCaroline.alugaJogo(jogoTheLastOfUs,periodo3)

    println(gamerCaroline.jogosAlugados)
}