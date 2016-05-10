public class Pawn extends Piece {
    public Pawn(Board board, Player player) {
        super(board, player);
    }

    public String toString() {
        return getPlayer() == Player.WHITE ? "\u2659" : "\u265F";
    }

    // ------------------------------------------------------------------------
    // Movement
    // ------------------------------------------------------------------------

    /**
     * A pawn can move forward by one tile if the destination is unoccupied, or
     * diagonally by one tile by one tile for capture. Pawns may only move away
     * from the player's back rank.
     *
     * @param from The source (current) tile.
     * @param to   The destination tile.
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean canMove(Tile from, Tile to) {
        int d1 = from.getRank() - to.getRank();
        int d2 = Math.abs(from.getFile() - to.getFile());
        int dr = getPlayer() == Player.WHITE ? +1 : -1;

        if (d1 == dr && d2 == 0) {
            return canMove(to);
        }

        if (d1 == dr && d2 == 1) {
            return canCapture(to);
        }

        return false;
    }
}
