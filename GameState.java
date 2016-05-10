/**
 * Represents the state of a game. This class is used solely for serialization purposes,
 * as it's very difficult in Java to return multiple parameters from the load method of
 * the GameSerializer class.
 */
public class GameState {
    public Board board;
    public Player turn;
    public long whiteTime;
    public long blackTime;

    public GameState(Board board, Player turn, long whiteTime, long blackTime) {
        this.board = board;
        this.turn = turn;
        this.whiteTime = whiteTime;
        this.blackTime = blackTime;
    }
}
