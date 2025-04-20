package br.com.alura.alugames.modelo

/**
De volta ao código de PlanoAvulso, ainda está indicando erro em Plano.
Para estender, vamos passar sempre o parâmetro que está
vindo na nossa classe.
Vamos informar que queremos pegar o tipo lá do Plano.
Assim não precisaremos criar um novo tipo no construtor da PlanoAvulso.
Vamos simplesmente referenciar ele. Então podemos apagar o val.
Está indicando um erro em tipo: String porque estamos em um data class.
O data class traz algumas facilidades, porém ele precisa ter no construtor
os atributos criados. Mas estamos tentando utilizar um tipo que foi criado
lá na superclasse.
Então, em vez de data class, deixaremos apenas class. Vamos transformar o PlanoAvulso
em uma classe para ele aceitar essa herança.
 **/
//data
class PlanoAvulso(tipo: String) : Plano(tipo) {
    override fun obterValor(aluguel: Aluguel): Double {
        var valorOriginal = super.obterValor(aluguel)
        if (aluguel.gamer.media > 8) {
            valorOriginal -= valorOriginal * 0.1
        }
        return valorOriginal
    }
}
