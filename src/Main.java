import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Stores Squarelotron objects
    static ArrayList<Squarelotron> matrices = new ArrayList<>();

    // Default input for the creation of Squarelotrons with
    // terms T(n)=n (n being natural numbers and <= size^2) integer items
    private static final int[] EMPTY = new int[0];

    public static void main(String[] args) {
        int id;
        int ring;
        int size;
        System.out.println("==== SQUARELOTRON ====");
        System.out.println("Start by creating a matrix (SETMATRIX)");
        printHelp();
        String filename;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            switch (scanner.next()) {
                case "ADDMATRIX":
                    size = Integer.parseInt(scanner.next());
                    filename = scanner.next();
                    Squarelotron matrix = new Squarelotron(size, filename.equals("default") ? EMPTY : importFromFile(filename, size));
                    matrices.add(matrix);
                    System.out.println("Squarelotron (ID: " + matrix.getId() + ") added.\nVisualization:");
                    matrix.printMatrix();
                    break;
                case "HELP":
                    printHelp();
                    break;
                case "LIST":
                    System.out.println("List of Squarelotrons [ID_(SIZExSIZE)]:");
                    int i = 0;
                    for (Squarelotron s : matrices) {
                        System.out.print(s.getId() + "_(" + s.getSize() + "x" + s.getSize() + ") ");
                        i++;
                        if (i % 5 == 0)
                            System.out.println();
                    }
                    if (i % 5 != 0)
                        System.out.println();
                    System.out.println("Count: " + ((Integer) i).toString());
                    break;
                case "MAINDIAGONALFLIP":
                    id = Integer.parseInt(scanner.next());
                    if (!matrixExists(id)) {
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    ring = Integer.parseInt(scanner.next());
                    if (ring < 1 || ring > Math.ceil(getMatrix(id).getSize() / (double) 2)){
                        System.out.println("Invalid ring.");
                        continue;
                    }
                    getMatrix(id).mainDiagonalFlip(ring);
                    System.out.println("Squarelotron (ID: " + id + ") flipped.\nVisualization:");
                    getMatrix(id).printMatrix();
                    break;
                case "PRINT":
                    id = Integer.parseInt(scanner.next());
                    if (!matrixExists(id)) {
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    System.out.println("Squarelotron (ID: " + id + ")\nVisualization:");
                    getMatrix(id).printMatrix();
                    break;
                case "QUIT":
                    return;
                case "REMOVEMATRIX":
                    id = Integer.parseInt(scanner.next());
                    if (!matrixExists(id)) {
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    matrices.remove(getMatrix(id));
                    System.out.println("Squarelotron (ID: " + id + ") removed.");
                    break;
                case "ROTATE":
                    id = Integer.parseInt(scanner.next());
                    if (!matrixExists(id)) {
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    int n = Integer.parseInt(scanner.next());
                    getMatrix(id).rotateRight(n);
                    System.out.println("Squarelotron rotated.\nVisualization:");
                    getMatrix(id).printMatrix();
                    break;
                case "TRANSPOSE":
                    id = Integer.parseInt(scanner.next());
                    if (!matrixExists(id)) {
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    getMatrix(id).transpose();
                    System.out.println("Squarelotron (ID: " + id + ") transposed.\nVisualization:");
                    getMatrix(id).printMatrix();
                    break;
                case "UPSIDEDOWNFLIP":
                    id = Integer.parseInt(scanner.next());
                    if (!matrixExists(id)) {
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    ring = Integer.parseInt(scanner.next());
                    if (ring < 1 || ring > Math.ceil(getMatrix(id).getSize() / (double) 2)){
                        System.out.println("Invalid ring.");
                        continue;
                    }
                    getMatrix(id).upsideDownFlip(ring);
                    System.out.println("Squarelotron (ID: " + id + ") flipped.\nVisualization:");
                    getMatrix(id).printMatrix();
                    break;
            }
        }
    }

    /**
     * Returns if the Squarelotron exist by specifying its ID
     * @param id ID of Squarelotron
     * @return Squarelotron
     */
    public static boolean matrixExists(int id) {
        for (Squarelotron s : matrices) {
            if (s.getId() == id)
                return true;
        }
        return false;
    }

    /**
     * Obtains a Squarelotron object by ID
     * The last Squarelotron in the list will be returned if ID does not exist
     * @param id ID of Squarelotron
     * @return Squarelotron
     */
    public static Squarelotron getMatrix(int id) {
        for (Squarelotron s : matrices) {
            if (s.getId() == id)
                return s;
        }
        return matrices.get(matrices.size() - 1);
    }

    /**
     * Imports one Squarelotron from file
     * @param filename Name of file that contains a single squarelotron
     * @return int[] Contents of the Squarelotron
     */
    private static int[] importFromFile(String filename, int size) {
        int[] result = new int[size * size];
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNext()) {
                result[i] = Integer.parseInt(scanner.next());
                i++;
            }
            if (i != size * size) {
                System.out.println("Invalid matrix. Squarelotron replaced by default input.");
                return EMPTY;
            }
        } catch (IOException e) {
            System.out.println("File input error. Check for file validity.");
            System.out.println("Squarelotron replaced by default input.");
            return EMPTY;
        }
        return result;
    }

    private static void printHelp() {
        System.out.println("--- List of Commands ---");
        System.out.println("- ADDMATRIX <size> <filename|'default'>");
        System.out.println("- HELP");
        System.out.println("- LIST");
        System.out.println("- MAINDIAGONALFLIP <ID> <ring(Between 1 and CEIL(size/2))>");
        System.out.println("- PRINT <ID>");
        System.out.println("- QUIT");
        System.out.println("- REMOVEMATRIX <ID>");
        System.out.println("- ROTATE <ID> <turns(Any Real Number)>");
        System.out.println("- TRANSPOSE <ID>");
        System.out.println("- UPSIDEDOWNFLIP <ID> <ring(Between 1 and CEIL(size/2))>");
        System.out.println();
    }

}
