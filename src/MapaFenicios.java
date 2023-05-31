import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

    private static ArrayList<Tuple> getPositions(char[][] matrix) {
        ArrayList<Tuple> numberPositions = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char element = matrix[i][j];
                if (Character.isDigit(element)) {
                    numberPositions.add(new Tuple(i, j));
                }
            }
        }

        // TESTE SE LISTA DAS POSICOES FUNCIONOU
        for (Tuple position : numberPositions) {
            System.out.println("Number at position (" + position.getRow() + ", " + position.getColumn() + ")");
        }
        return numberPositions;
    }

    public static void main(String[] args) {
        // salvar os dados do arquivo em uma matriz de chars
        String filePath = "mapa0.txt";
        char[][] matrix = readMapFile(filePath);

        // salvar todas as posicoes dos portos em uma lista
        ArrayList<Tuple> numberPositions = getPositions(matrix);
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
