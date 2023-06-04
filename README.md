# Alest2-Map

# Trabalho 2 da disciplina de Algoritmos e Estrutuda de Dados II, PUCRS.

Segue o enunciado:

# Os fenícios estão chegando
VocÊ sabe que os fenícios foram grandes navegadores e comerciantes que percorriam os mares Egeu, Adriático e Mediterrâneo levando produtos entre nações. O que você não sabe é que eles estão se preparando para voltar a viajar e desta vez estão dispostos a usar todas as tecnologias modernas para encurtar suas viagens, reduzir sua pegada climática e (é claro) aumentar seus lucros.
    Para fazer isso tudo eles contrataram... você! Eles planejam cada uma de suas viagens saindodo porto 1 e passando por todos os outros portos de 2 a 9 nesta ordem, mas precisam saber o trajeto total para poder ir com o mínimo de combustível possível e ainda voltar para casa depois do último porto. Então sua missão é calcular quanto combustível é necessário, levando em conta os detalhes a seguir: 
    
   - As triremes fenícias se movem apenas nas direções Norte, Sul, Leste e Oeste;
    
   - Cada movimento usa uma unidade de combustível;
    
   - Os portos de 1 a 9 estão marcados... bom... por 1 a 9;
    
   - Nos mapas fenícios o ponto significa um trecho de água navegável e o asterisco significa algo não-navegável (terra, rochedos, deserto, pântano, abismo, rio de lava, etc);
    
   - Às vezes, devido a erros dos cartógrafos locais, um porto foi colocado fora de lugar e não pode ser visitado. Neste caso sua missão é pular este "porto" e ir direto para o seguinte ou para casa se esse for o último porto.
    
Os mapas tem no início a informação de quantas linhas e colunas eles possuem, e seus tamanhos variam bastante pois os fenícios tem grandes planos de expansão para o futuro. Um exemplo de mapa está abaixo:

    15 80
    ..................*.*.*. .... .... .... .**. *... ...* .**. *... .... .... .... .... .... ....
    .................******* .... .... .... .*** *... .*** *... .... *... .*.. .... .... .... ....
    ............************ .... .**. ..*. **** .... .*** **4. .... .**. **.. .... .... .... .***
    .............*..*******. *..* ***. **.* ..*. *..* .*** .*.. .... ***. .... .... ...* .*.. .7**
    .******.........1******* ...* ...* *..* *..* .*** ..** .... ...* **** .... ..*. .... **.. ....
    .****...**.......**.**** .... *..* *..* *..* **** ..*. .... .... .*** *... ...* *.** ***. ....
    ..**...............*.... .... ...* **.. .... **** ***. .... .... .*** .... ..** *.*. **** ....
    ...*.................... .... ..** *... .... **9* ***. .... .... ..5* *... .*** .*** **** ....
    ...*..........****...... .... ..** .... *... ..** *... .**. .... ...* .... .*.* **** ..*. ....
    ....*....**.*..****..... .... ..*. .... ***. ...* *... ***. .... .... .... **.* **.* .... ..*.
    *****....*****..***..... .... ..*. .... **.. .... .*.. ***. .... .... .*.* *... *... .... **..
    *****....*.***..*****... .... ..** ...* ***. .... *... .... .*.. .... .**. *... **.. .... ***8
    ..**......**....*****... .... .... ...* *... ..3* **.. .... *... .... ..** *6*. .*.. .... **..
    ..*.........*...2*.***.. .... .... ..*. *... ..** ***. .... .... .... .*** .*.. .... .... *...
    ...........*.......**... .... .... .... .... ...* *..* .... .... .... ..** *... .... .... ....

   Você deve receber os casos de teste feito pelos cartógrafos e resolver o problema de achar o mpinimo de combustível para fazer as viagens em cada um deles.
