import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // Stores Squarelotron objects
    static ArrayList<Squarelotron> matrices = new ArrayList<>();

    // Complemented by an ArrayList when multiple Squarelotrons exist
    static Squarelotron current;
    // Will become deprecated when multiple Squarelotrons exist
    static int size;

    // Default input for the creation of Squarelotrons with
    // terms T(n)=n (n being natural numbers and <= size^2) integer items
    private static final int[] EMPTY = new int[0];

    public static void main(String[] args) {
        System.out.println("==== SQUARELOTRON ====");
        System.out.println("Start by creating a matrix (SETMATRIX)");
        printHelp();
        int id;
        int ring;
        String filename;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            switch (scanner.next()) {
                case "ADDMATRIX":
                    size = Integer.parseInt(scanner.next());
                    filename = scanner.next();
                    Squarelotron matrix = new Squarelotron(size, filename.equals("default") ? EMPTY : importFromFile(filename));
                    matrices.add(matrix);
                    System.out.println("Matrix added.\nVisualization:");
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
                    break;
                case "MAINDIAGONALFLIP":
                    if (current == null){
                        System.out.println("You have not set a matrix.");
                        continue;
                    }
                    ring = Integer.parseInt(scanner.next());
                    if (ring < 1 || ring > Math.ceil(size / (double) 2)){
                        System.out.println("Invalid ring.");
                        continue;
                    }
                    current.mainDiagonalFlip(ring);
                    System.out.println("Matrix flipped.\nVisualization:");
                    current.printMatrix();
                    break;
                case "PRINT":
                    id = Integer.parseInt(scanner.next());
                    if (!matrixExists(id)) {
                        System.out.println("Invalid ID.");
                        continue;
                    }
                    System.out.println("Matrix (ID: " + id + ")\nVisualization:");
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
                    System.out.println("Matrix (ID: " + id + ") removed.");
                    break;
                case "ROTATE":
                    if (current == null){
                        System.out.println("You have not set a matrix.");
                        continue;
                    }
                    int n = Integer.parseInt(scanner.next());
                    current.rotateRight(n);
                    System.out.println("Matrix rotated.\nVisualization:");
                    current.printMatrix();
                    break;
                // Will be replaced by ADDMATRIX when multimatrix support is implemented
                case "SETMATRIX":
                    size = Integer.parseInt(scanner.next());
                    filename = scanner.next();
                    current = new Squarelotron(size, filename.equals("default") ? EMPTY : importFromFile(filename));
                    System.out.println("Matrix set.\nVisualization:");
                    current.printMatrix();
                    break;
                case "TRANSPOSE":
                    if (current == null){
                        System.out.println("You have not set a matrix.");
                        continue;
                    }
                    current.transpose();
                    System.out.println("Matrix transposed.\nVisualization:");
                    current.printMatrix();
                    break;
                case "UPSIDEDOWNFLIP":
                    if (current == null){
                        System.out.println("You have not set a matrix.");
                        continue;
                    }
                    ring = Integer.parseInt(scanner.next());
                    if (ring < 1 || ring > Math.ceil(size / (double) 2)){
                        System.out.println("Invalid ring.");
                        continue;
                    }
                    current.upsideDownFlip(ring);
                    System.out.println("Matrix flipped.\nVisualization:");
                    current.printMatrix();
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
    private static int[] importFromFile(String filename) {
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
        System.out.println("- HELP");
        System.out.println("- MAINDIAGONALFLIP <ring(Between 1 and CEIL(size/2))>");
        System.out.println("- QUIT");
        System.out.println("- ROTATE <turns(Any Real Number)>");
        System.out.println("- SETMATRIX <size(Any Positive Number)>");
        System.out.println("- TRANSPOSE");
        System.out.println("- UPSIDEDOWNFLIP <ring(Between 1 and CEIL(size/2))>");
        System.out.println();
    }

}
