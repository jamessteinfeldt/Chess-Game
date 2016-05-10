public enum Player {
    WHITE,
    BLACK;

    /**
     * Get the opposite player.
     *
     * @return The opposite player.
     */
    public Player opposite() {
        switch (this) {
            case WHITE: return BLACK;
            case BLACK: return WHITE;
        }

        return null;
    }
}
