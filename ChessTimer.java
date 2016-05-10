/**
 * A chess timer class that acts like a stopwatch.
 */
public class ChessTimer {
    private long start = -1;
    private long elapsed;

    public ChessTimer() {
        this(0);
    }

    public ChessTimer(long elapsed) {
        this.elapsed = elapsed;
    }

    /**
     * Get the total time this timer has been active.
     *
     * @return The total time this timer has been active.
     */
    public long getElapsed() {
        return elapsed;
    }

    /**
     * Marks "now" as the current start time.
     */
    public void resume() {
        start = System.currentTimeMillis();
    }

    /**
     * Pause the timer so that the timer is not currently active.
     * This should make any subsequent calls to update do nothing.
     */
    public void pause() {
        update();
        start = -1;
    }

    /**
     * Update the current elapsed time of the timer, but only if
     * the timer is currently active.
     */
    public void update() {
        if (start != -1) {
            long now = System.currentTimeMillis();
            elapsed += now - start;
            start = now;
        }
    }
}
