import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

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
        int ring;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            switch (scanner.next()) {
                case "HELP":
                    printHelp();
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
                case "QUIT":
                    return;
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
                case "SETMATRIX":
                    size = Integer.parseInt(scanner.next());
                    String filename = scanner.next();
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
                    current = current.upsideDownFlip(ring);
                    System.out.println("Matrix flipped.\nVisualization:");
                    current.printMatrix();
                    break;
            }
        }
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
