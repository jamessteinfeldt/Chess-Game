import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameSerializer {
    /**
     * Reads a game state from the given file.
     *
     * @param loadFile The input file.
     * @return A game state.
     * @throws RuntimeException If the file cannot be read.
     * @throws RuntimeException If a game state cannot be parsed from the file contents.
     */
    public GameState load(File loadFile) {
        try (Scanner scanner = new Scanner(loadFile)) {
            try {
                Board board = new Board();

                Player turn = Player.valueOf(scanner.next());
                long whiteTime = scanner.nextLong();
                long blackTime = scanner.nextLong();
                scanner.nextLine();

                while (scanner.hasNextLine()) {
                    int rank = scanner.nextInt();
                    int file = scanner.nextInt();
                    String p = scanner.next();
                    String n = scanner.next();
                    scanner.nextLine();

                    Tile tile = new Tile(rank, file);
                    Player pl = Player.valueOf(p);

                    Piece piece = loadPiece(board, pl, n);

                    if (board.isOccupied(tile)) {
                        throw new RuntimeException("Duplicate positions in save file.");
                    }

                    board.setPieceAt(tile, piece);
                }

                return new GameState(board, turn, whiteTime, blackTime);
            } catch (NoSuchElementException | IllegalArgumentException ex) {
                throw new RuntimeException("Malformed line.");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Problem opening file.");
        }
    }

    /**
     * Saves a game state to the given file.
     *
     * @param saveFile The output file.
     * @param state    The game state.
     * @throws RuntimeException When the file cannot be written.
     */
    public void save(File saveFile, GameState state) {
        try (PrintWriter writer = new PrintWriter(saveFile)) {
            writer.print(state.turn);
            writer.print(" ");
            writer.print(state.whiteTime);
            writer.print(" ");
            writer.print(state.blackTime);
            writer.print("\n");

            for (int rank = 0; rank < 8; rank++) {
                for (int file = 0; file < 8; file++) {
                    Piece piece = state.board.getPieceAt(new Tile(rank, file));

                    if (piece != null) {
                        writer.println(savePiece(rank, file, piece));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Problem writing file.");
        }
    }

    /**
     * Creates a piece instance with the given board and player.
     *
     * @param board  The board.
     * @param player The player.
     * @param name   The name (type) of the piece.
     * @return A piece instance.
     * @throws RuntimeException When the name of the piece is unknown.
     */
    private Piece loadPiece(Board board, Player player, String name) {
        switch (name) {
            case "Pawn":
                return new Pawn(board, player);
            case "Rook":
                return new Rook(board, player);
            case "Knight":
                return new Knight(board, player);
            case "Bishop":
                return new Bishop(board, player);
            case "Queen":
                return new Queen(board, player);
            case "King":
                return new King(board, player);
        }

        throw new RuntimeException("Unknown piece.");
    }

    /**
     * Serializes a piece's location, player, and type data.
     *
     * @param rank  The piece's current rank.
     * @param file  The piece's current file.
     * @param piece The piece.
     * @return A string representation.
     */
    private String savePiece(int rank, int file, Piece piece) {
        return String.format("%d %d %s %s", rank, file, piece.getPlayer().toString(), piece.getClass().getName());
    }
}
