/**
 * Represents a tile on the board.
 */
public class Tile {
    private int rank;
    private int file;

    public Tile(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public String toString() {
        return "" + (char) (file + 65) + (8 - rank);
    }

    /**
     * Determines if the tile exists on the board.
     *
     * @return true if the tile is valid, false otherwise.
     */
    public boolean isValid() {
        return rank >= 0 && rank < 8 && file >= 0 && file < 8;
    }

    /**
     * Determines if this tile is equal to the other object.
     *
     * @param other Another object.
     * @return true if the other object is a tile with the same rank and file, false otherwise.
     */
    public boolean equals(Object other) {
        if (other instanceof Tile) {
            return ((Tile) other).rank == rank && ((Tile) other).file == file;
        }

        return false;
    }
}
