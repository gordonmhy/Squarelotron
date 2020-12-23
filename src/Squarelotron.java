import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Squarelotron {

    private final int id;
    private final int[][] squarelotron;
    private final int size;
    private final int digits;

    private static int count = 0;

    /**
     * Constructor of Squarelotron
     * @param size  Size of Squarelotron to initialize
     * @param slots Array of integers items in Squarelotron
     */
    public Squarelotron(int size, int[] slots) {
        this.size = size;
        count++;
        id = count;
        squarelotron = new int[size][size];
        int longest = 0;
        for (int i = 0, k = 0; i < size; i++) {
            for (int j = 0; j < size; j++, k++) {
                int length;
                if (slots.length == 0) {
                    squarelotron[i][j] = k + 1;
                    length = (int) Math.ceil(Math.log10(k + 1));
                    if (String.valueOf(k + 1).matches("^1(0*)$")) length++;
                } else {
                    squarelotron[i][j] = slots[k];
                    length = (int) Math.ceil(Math.log10(slots[k]));
                    if (String.valueOf(slots[k]).matches("^1(0*)$")) length++;
                }
                if (longest < length)
                    longest = length;
            }
        }
        this.digits = longest;
    }

    /**
     * Obtains the ID of this Squarelotron
     * @return int
     */
    public int getId() {
        return this.id;
    }

    /**
     * Obtains the Squarelotron slot data
     * @return int[][]
     */
    public int[][] getSquarelotron() {
        return this.squarelotron;
    }

    /**
     * Obtains the size of this Squarelotron
     * @return int
     */
    public int getSize() {
        return size;
    }

    /**
     * ZeroPads each slot of the matrix for printing (Internal use)
     * @param n Slot of the matrix in String
     * @return String
     */
    private String zeroPad(String n) {
        if (n.length() < digits)
            return zeroPad("0" + n);
        return n;
    }

    /**
     * Compares the size and the slots of two Squarelotrons
     * @param o Another Squarelotron object to be compared
     * @return Boolean
     */
    public boolean equals(Object o) {
        if (!(o instanceof Squarelotron))
            return false;
        if (this.size != ((Squarelotron) o).getSize())
            return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.squarelotron[i][j] != ((Squarelotron) o).getSquarelotron()[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Exports the Squarelotron to a file and returns true if successful
     * @param filename Name of file to write
     * @return boolean
     */
    public boolean exportToFile(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            PrintWriter printer = new PrintWriter(writer);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int num = squarelotron[i][j];
                    String c = zeroPad(((Integer) num).toString());
                    printer.print(c);
                    printer.print(j + 1 == size ? '\n' : ' ');
                }
            }
            printer.flush();
        } catch (IOException e) {
            System.out.println("Error writing file.");
            return false;
        }
        return true;
    }

    /**
     * Prints the Squarelotron to terminal and outputs to the file output.txt
     */
    public void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int num = squarelotron[i][j];
                String c = zeroPad(((Integer) num).toString());
                System.out.print(c);
                System.out.print(j + 1 == size ? '\n' : ' ');
            }
        }
        System.out.println();
    }

    /**
     * Rotates the squarelotron rightwards for a specified number of turns
     * @param numberOfTurns Number of turns to rotate right, can be negative (leftward)
     */
    public void rotateRight(int numberOfTurns) {
        if (numberOfTurns == 0) return;
        if (Math.abs(numberOfTurns) > 4) numberOfTurns
                = Math.abs(numberOfTurns) % 4 * (numberOfTurns < 0 ? -1 : 1);
        if (numberOfTurns < 0) numberOfTurns = 4 + numberOfTurns;
        for (int count = 0; count < numberOfTurns; count++) {
            for (int i = size - 1, k = 0; i > 0; i -= 2, k++) {
                for (int j = k; j < i + k; j++) {
                    int tmpX = k;
                    int tmpY = j;
                    int tmp = squarelotron[tmpX][tmpY];
                    for (int z = 0; z < 4; z++) {
                        int innerTmp = squarelotron[tmpY][size - 1 - tmpX];
                        squarelotron[tmpY][size - 1 - tmpX] = tmp;
                        tmp = innerTmp;
                        int tmpTmpX = tmpX;
                        tmpX = tmpY;
                        tmpY = size - 1 - tmpTmpX;
                    }
                }
            }
        }
    }

    /**
     * Flips one of the rings of the Squarelotron upside down
     * @param ring The ring to flip
     */
    public void upsideDownFlip(int ring) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int tmp = squarelotron[i][j];
                if (i == ring - 1 && j >= ring - 1 && j <= size - ring) {
                    squarelotron[i][j] = squarelotron[size - ring][j];
                    squarelotron[size - ring][j] = tmp;
                }
                if ((j == ring - 1 || j == size - ring)
                        && i > ring - 1 && i <= (size - ring) / 2) {
                    squarelotron[i][j] = squarelotron[size - 1 - i][j];
                    squarelotron[size - 1 - i][j] = tmp;
                }
            }
        }
    }

    /**
     * Flips one of the rings of the Squarelotron about the main diagonal
     * @param ring The ring to flip
     */
    public void mainDiagonalFlip(int ring) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i == ring - 1 || i == size - ring) && j >= ring - 1 && j < size - ring) {
                    int tmp = squarelotron[i][j];
                    squarelotron[i][j] = squarelotron[j][i];
                    squarelotron[j][i] = tmp;
                }
            }
        }
    }

    /**
     * Transpose the Squarelotron such that all rings are flipped about the main diagonal
     * @param ring The ring to start flipping from the outer to inner rings
     */
    public void transpose(int ring) {
        if (ring > Math.ceil(size / (double) 2))
            return;
        mainDiagonalFlip(ring);
        transpose(ring + 1);
    }

    /**
     * Transpose the Squarelotron from the outermost ring (full transpose)
     */
    public void transpose() {
        transpose(1);
    }
}