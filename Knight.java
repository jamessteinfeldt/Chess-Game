public class Knight extends Piece {
    public Knight(Board board, Player player) {
        super(board, player);
    }

    public String toString() {
        return getPlayer() == Player.WHITE ? "\u2658" : "\u265E";
    }

    // ------------------------------------------------------------------------
    // Movement
    // ------------------------------------------------------------------------

    /**
     * A knight can move in an `L` shape - two squares horizontally and one square
     * vertically, or one square horizontally and two squares vertically. A knight
     * may not be obstructed by another piece.
     *
     * @param from The source (current) tile.
     * @param to   The destination tile.
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean canMove(Tile from, Tile to) {
        int d1 = Math.abs(from.getRank() - to.getRank());
        int d2 = Math.abs(from.getFile() - to.getFile());

        return canMoveOrCapture(to) && ((d1 == 1 && d2 == 2) || (d1 == 2 && d2 == 1));
    }
}
