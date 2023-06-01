import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class MapaFenicios {
    private static char[][] readMapFile(String filePath) {
        char[][] matrix = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] dimensions = br.readLine().split(" ");
            int numRows = Integer.parseInt(dimensions[0]);
            int numCols = Integer.parseInt(dimensions[1]);

            matrix = new char[numRows][numCols];
            int row = 0;

            while ((line = br.readLine()) != null) {
                for (int col = 0; col < numCols; col++) {
                    matrix[row][col] = line.charAt(col);
                }
                row++;
            }

            // TESTE SE MATRIZ FICOU CERTA
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return matrix;
    }

    private static TreeMap<Integer, Tuple> getPositions(char[][] matrix) {
        TreeMap<Integer, Tuple> numberPositions = new TreeMap<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char element = matrix[i][j];
                if (Character.isDigit(element)) {
                    int digit = Character.getNumericValue(element);
                    numberPositions.put(digit, new Tuple(i, j));
                }
            }
        }

//        // TESTE SE A MAPA DAS POSICOES FUNCIONOU
//        for (Integer digit : numberPositions.keySet()) {
//            Tuple position = numberPositions.get(digit);
//            System.out.println("Number " + digit + " at position (" + position.getRow() + ", " + position.getColumn() + ")");
//        }

        return numberPositions;
    }

//    private static boolean isValid(int x, int y, char[][] matrix) {
//        int numRows = matrix.length;
//        int numCols = matrix[0].length;
//
//        // Check if the position is within the matrix boundaries
//        return x >= 0 && x < numRows && y >= 0 && y < numCols;
//    }

    public static int findShortestPath(char[][] matrix, Tuple start, Tuple end) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        int[][] distance = new int[rows][columns];
        boolean[][] visited = new boolean[rows][columns];

        // Initialize distance matrix with infinity
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }

        // Define the directions (up, down, left, right)
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        // Create a queue for BFS
        Queue<Tuple> queue = new LinkedList<>();

        // Enqueue the start position
        queue.add(start);
        visited[start.getRow()][start.getColumn()] = true;
        distance[start.getRow()][start.getColumn()] = 0;

        // Perform BFS
        while (!queue.isEmpty()) {
            Tuple current = queue.poll();

            // Check if the current position is the destination
            if (current.getRow() == end.getRow() && current.getColumn() == end.getColumn()) {
                return distance[current.getRow()][current.getColumn()];
            }

            // Explore the neighboring positions
            for (int k = 0; k < 4; k++) {
                int newRow = current.getRow() + dr[k];
                int newColumn = current.getColumn() + dc[k];

                // Check if the neighboring position is valid and not visited
                if (newRow >= 0 && newRow < rows && newColumn >= 0 && newColumn < columns
                        && !visited[newRow][newColumn] && matrix[newRow][newColumn] != '*') {

                    // Update the distance and enqueue the neighboring position
                    distance[newRow][newColumn] = distance[current.getRow()][current.getColumn()] + 1;
                    visited[newRow][newColumn] = true;
                    queue.add(new Tuple(newRow, newColumn));
                }
            }
        }

        // If the destination is not reachable, return -1 or some sentinel value
        return 0;
    }


    public static void main(String[] args) {
        // salvar os dados do arquivo em uma matriz de chars
        String filePath = "mapa1.txt";
        char[][] matrix = readMapFile(filePath);

        // salvar todas as posicoes dos portos em uma lista
        TreeMap<Integer, Tuple> numberPositions = getPositions(matrix);

        int totalDistance = 0;
        // Run BFS for each number in numberPositions
        int i = 1;
        List<Integer> positions = new ArrayList<>(numberPositions.keySet());
        Integer currentKey = positions.get(i - 1);

        while (i < positions.size()) {
            Integer nextKey = positions.get(i);
            Tuple current = numberPositions.get(currentKey);
            Tuple next = numberPositions.get(nextKey);

            int shortestDistance = findShortestPath(matrix, current, next);

            if (shortestDistance == 0) {
                numberPositions.remove(nextKey);
                positions.remove(i);
                System.out.printf("Rota não encontrada de (%d, %d) até (%d, %d)\n", current.getRow(), current.getColumn(), next.getRow(), next.getColumn());
            } else {
                totalDistance += shortestDistance;
                currentKey = nextKey;
                i++;
                System.out.printf("Rota encontrada de (%d, %d) até (%d, %d)\n", current.getRow(), current.getColumn(), next.getRow(), next.getColumn());
            }
        }



        System.out.println(totalDistance);

    }
}

// classe para representar uma tupla em Java
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
