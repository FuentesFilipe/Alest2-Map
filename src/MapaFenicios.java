import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class MapaFenicios {
    /**
     * Lê um arquivo de texto com os dados do mapa salva-os em uma matriz de chars
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
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    System.out.print(matriz[i][j] + " ");
                }
                System.out.println();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return matriz;
    }

    /**
     * Salva todas as posicoes dos portos em um dicionario, mantdestinoo a ordem numerica deles
     * @param matriz matriz de chars com os dados do mapa
     * @return dicionario com as posicoes dos portos
     */
    private static TreeMap<Integer, Tuple> getPositions(char[][] matriz) {
        TreeMap<Integer, Tuple> numberPositions = new TreeMap<>();

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                char element = matriz[i][j];
                if (Character.isDigit(element)) {
                    int digit = Character.getNumericValue(element);
                    numberPositions.put(digit, new Tuple(i, j));
                }
            }
        }
        return numberPositions;
    }

    public static int achaMenorCaminho(char[][] matriz, Tuple origem, Tuple destino) {
        int linhas = matriz.length;
        int colunas = matriz[0].length;

        int[][] distancia = new int[linhas][colunas];
        boolean[][] visitados = new boolean[linhas][colunas];

        // inicializa a matriz de distancias com "infinito"
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                distancia[i][j] = Integer.MAX_VALUE;
            }
        }

        // Definir as direções (cima, baixo, esquerda, direita)
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Criar a fila para o caminhamento BFS
        Queue<Tuple> fila = new LinkedList<>();

        // Marcar a origem como visitada e com distancia 0
        fila.add(origem);
        visitados[origem.getRow()][origem.getColumn()] = true;
        distancia[origem.getRow()][origem.getColumn()] = 0;

        // Realiza o caminhamento BFS
        while (!fila.isEmpty()) {
            Tuple atual = fila.poll();

            // Checar se a posição atual é o destino
            if (atual.getRow() == destino.getRow() && atual.getColumn() == destino.getColumn()) {
                return distancia[atual.getRow()][atual.getColumn()];
            }

            // Explorar as posições vizinhas
            for (int k = 0; k < 4; k++) {
                int novaLinha = atual.getRow() + dr[k];
                int novaLColuna = atual.getColumn() + dc[k];

                // Checar se a posição vizinha é válida e não foi visitada
                if (novaLinha >= 0 && novaLinha < linhas && novaLColuna >= 0 && novaLColuna < colunas
                        && !visitados[novaLinha][novaLColuna] && matriz[novaLinha][novaLColuna] != '*') {

                    // Atualiza a distancia e adiciona a posição vizinha na fila
                    distancia[novaLinha][novaLColuna] = distancia[atual.getRow()][atual.getColumn()] + 1;
                    visitados[novaLinha][novaLColuna] = true;
                    fila.add(new Tuple(novaLinha, novaLColuna));
                }
            }
        }

        // Se o destino não for alcançável, retorne 0 para não ser considerado na soma total
        return 0;
    }


    public static void main(String[] args) {
        // salvar os dados do arquivo em uma matriz de chars
        String caminhoArq = "mapa0.txt";
        char[][] matriz = lerArquivoMapa(caminhoArq);

        // salvar todas as posicoes dos portos em uma lista
        TreeMap<Integer, Tuple> posicaoPortos = getPositions(matriz);

        int caminhoTotal = 0;
        int i = 1;
        List<Integer> posicoes = new ArrayList<>(posicaoPortos.keySet());
        Integer portoAtual = posicoes.get(0);

        while (i < posicoes.size()) {
            Integer proxPorto = posicoes.get(i);
            Tuple atual = posicaoPortos.get(portoAtual);
            Tuple proximo = posicaoPortos.get(proxPorto);

            int menorCaminho = achaMenorCaminho(matriz, atual, proximo);

            // se o menor caminho for 0, significa que não há caminho entre os portos
            // então remove o porto atual da lista de portos e a proxima coordenada do dicionario
            if (menorCaminho == 0) {
                posicaoPortos.remove(proxPorto);
                posicoes.remove(i);
                System.out.printf("Rota não encontrada de (%d, %d) até (%d, %d)\n", atual.getRow(), atual.getColumn(), proximo.getRow(), proximo.getColumn());
            } else {
                caminhoTotal += menorCaminho;
                portoAtual = proxPorto;
                i++;
                System.out.printf("Rota encontrada de (%d, %d) até (%d, %d)\n", atual.getRow(), atual.getColumn(), proximo.getRow(), proximo.getColumn());
            }
        }
        System.out.println(caminhoTotal);

    }
}

/**
 * Classe para representar as coordenadas dos mapas em tuplas (row, column)
 * Usada para facilitar a implementação do BFS
 */
class Tuple {
    private int row;
    private int column;

    public Tuple(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
