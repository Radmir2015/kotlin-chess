package halma.moves;

import game.core.Square;

/**
 * Ход для игры <a href=
 * "https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0">
 * Халма</a>
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public class Jump extends HalmaMove {
    public Jump(Square... squares) {
        super(squares);
    }

    @Override
    public String toString() {
        return "" + source + "x" + target;
    }
}
