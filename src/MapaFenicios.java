import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class MapaFenicios {
    /**
     * Lê um arquivo de texto com os dados do mapa e salva-os em uma matriz de chars
     * @param caminhoArq caminho do arquivo de texto
     * @return matriz de chars com os dados do arquivo
     */
    private static char[][] lerArquivoMapa(String caminhoArq) {
        char[][] matriz = null;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArq))) {
            String linha;
            String[] dimensoes = br.readLine().split(" ");
            int nroLinhas = Integer.parseInt(dimensoes[0]);
            int nroColunas = Integer.parseInt(dimensoes[1]);

            matriz = new char[nroLinhas][nroColunas];
            int row = 0;

            while ((linha = br.readLine()) != null) {
                for (int col = 0; col < nroColunas; col++) {
                    matriz[row][col] = linha.charAt(col);
                }
                row++;
            }

            // TESTE SE MATRIZ FICOU CERTA
            for (char[] chars : matriz) {
                for (int j = 0; j < matriz[0].length; j++) {
                    System.out.print(chars[j] + " ");
                }
                System.out.println();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return matriz;
    }

    /**
     * Salva todas as posicoes dos portos em um dicionario, mantendo a ordem numerica deles
     * @param matriz matriz de chars com os dados do mapa
     * @return dicionario com as posicoes dos portos
     */
    private static TreeMap<Integer, Tuple> getPosicoes(char[][] matriz) {
        TreeMap<Integer, Tuple> posNumeros = new TreeMap<>();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                char elemento = matriz[i][j];
                if (Character.isDigit(elemento)) {
                    int digito = Character.getNumericValue(elemento);
                    posNumeros.put(digito, new Tuple(i, j));
                }
            }
        }
        return posNumeros;
    }

    /**
     * Acha o menor caminho entre dois pontos na matriz de entrada
     * @param matriz matriz de chars com os dados do mapa
     * @param origem posicao de origem
     * @param destino posicao de destino
     * @return distancia entre os dois pontos
     */
    private static int achaMenorCaminho(char[][] matriz, Tuple origem, Tuple destino) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;

        int[][] distancia = new int[linhas][colunas];
        boolean[][] visitados = new boolean[linhas][colunas];

        // inicializa a matriz de distancias com "infinito"
        for (int i = 0; i < linhas; i++) {
            Arrays.fill(distancia[i], Integer.MAX_VALUE);
        }

        // Vetores para explorar as posições vizinhas
        // (cima, baixo, esquerda, direita)
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Criar a fila para o caminhamento BFS
        Queue<Tuple> fila = new LinkedList<>();

        // Marcar a origem como visitada e com distancia 0
        fila.add(origem);
        visitados[origem.row()][origem.column()] = true;
        distancia[origem.row()][origem.column()] = 0;

        // Realiza o caminhamento BFS
        while (!fila.isEmpty()) {
            Tuple atual = fila.poll();
            int atualLinha = atual.row();
            int atualColuna = atual.column();
            
            // Checar se a posição atual é o destino
            if (atualLinha == destino.row() && atualColuna == destino.column()) {
                return distancia[atualLinha][atualColuna];
            }

            // Explorar as posições vizinhas
            for (int k = 0; k < 4; k++) {
                int novaLinha = atualLinha + dr[k];
                int novaLColuna = atualColuna + dc[k];

                // Checar se a posição vizinha é válida e não foi visitada
                if (novaLinha >= 0 && novaLinha < linhas && novaLColuna >= 0 && novaLColuna < colunas
                        && !visitados[novaLinha][novaLColuna] && matriz[novaLinha][novaLColuna] != '*') {

                    // Atualiza a distancia e adiciona a posição vizinha na fila
                    distancia[novaLinha][novaLColuna] = distancia[atualLinha][atualColuna] + 1;
                    visitados[novaLinha][novaLColuna] = true;
                    fila.add(new Tuple(novaLinha, novaLColuna));
                }
            }
        }

        // Se o destino não for alcançável, retorna 0 para não ser considerado na soma total
        return 0;
    }


    /**
     * Calcula o caminho total percorrido pelos portos do mapa de entrada
     * @param matriz matriz de chars com os dados do mapa
     * @return caminho total percorrido pelos fenicios
     */
    private static int calculaCaminhoTotal(char[][] matriz) {
        // salvar todas as posicoes dos portos em um dicionario para manter a ordem numerica deles
        TreeMap<Integer, Tuple> posicaoPortos = getPosicoes(matriz);

        int caminhoTotal = 0;
        int i = 1;
        List<Integer> posicoes = new ArrayList<>(posicaoPortos.keySet());
        Integer portoAtual = posicoes.get(0);

        while (i < posicoes.size()) {
            Integer proxPorto = posicoes.get(i);
            Tuple atual = posicaoPortos.get(portoAtual);
            Tuple proximo = posicaoPortos.get(proxPorto);

            int menorCaminho = achaMenorCaminho(matriz, atual, proximo);

            // se o menor caminho for 0, significa que não há caminho entre os portos,
            // então remove o porto atual da lista de portos e a proxima coordenada do dicionario
            if (menorCaminho == 0) {
                System.out.printf("Rota não encontrada do Porto %s%s até Porto %s%s\n", portoAtual, atual, proxPorto, proximo);
                posicaoPortos.remove(proxPorto);
                posicoes.remove(i);
            } else {
                System.out.printf("Rota encontrada do Porto %s%s até Porto %s%s\n", portoAtual, atual, proxPorto, proximo);
                caminhoTotal += menorCaminho;
                portoAtual = proxPorto;
                i++;
            }
        }
        // apos terminar de percorrer todos os portos, soma o caminho do ultimo porto até o primeiro
        caminhoTotal += achaMenorCaminho(matriz, posicaoPortos.get(portoAtual), posicaoPortos.get(1));
        return caminhoTotal;
    }

    /**
     * Mostra o menu de opções
     */
    private static void mostraMenu() {
        System.out.println("Escolha uma opção:");
        System.out.println("0 - Sair");
        System.out.println("1 - Mapa 1");
        System.out.println("2 - Mapa 2");
        System.out.println("3 - Mapa 3");
        System.out.println("4 - Mapa 4");
        System.out.println("5 - Mapa 500x1000");
        System.out.println();
    }

    public static void main(String[] args) {
        final int SAIR = 0;
        final String[] CAMINHOS = {"mapa1.txt", "mapa2.txt", "mapa3.txt", "mapa4.txt", "mapa_500_1000.txt"};

        int opcao;
        Scanner sc = new Scanner(System.in);
        do {
            mostraMenu();
            opcao = sc.nextInt();

            if (opcao < SAIR || opcao > CAMINHOS.length) {
                System.out.println("Opção inválida!!");
            } else if (opcao == SAIR) {
                System.out.println("Saindo...");
            } else {
                String caminhoArq = CAMINHOS[opcao - 1];
                char[][] matriz = lerArquivoMapa(caminhoArq);
                int caminhoTotal = calculaCaminhoTotal(matriz);
                System.out.printf("Total de combustivel necessario para viagem: %d\n", caminhoTotal);
            }
        } while (opcao != SAIR);
    }
}

/**
 * Classe para representar as coordenadas dos mapas em tuplas (row, column)
 */
record Tuple(int row, int column) {
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
