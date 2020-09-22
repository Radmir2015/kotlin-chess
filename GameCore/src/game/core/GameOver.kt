package game.core;

/**
 * Ситуация окончания игры. Параметр ситуации - результат игры
 * (переменная типа GameResult)
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class GameOver extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final GameResult result;

    /**
     * Ситуация с результатом игры.
     *
     * @param result результатом игры.
     * @see GameResult
     */
    public GameOver(GameResult result) {
        this.result = result;
    }
}
