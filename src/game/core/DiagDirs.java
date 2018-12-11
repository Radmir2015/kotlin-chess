package game.core;

/**
 * 4-направления по  диагоналям.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public enum DiagDirs {
    LEFT_UP(-1, -1), RIGHT_UP(+1, -1),

    LEFT_DOWN(-1, +1), RIGHT_DOWN(+1, +1);

    public static final
    DiagDirs[] ALL = {
            LEFT_UP, RIGHT_UP,

            LEFT_DOWN, RIGHT_DOWN
    };

    /**
     * Смещение по вертикали.
     */
    public int dv;

    /**
     * Смещение по горизонтали.
     */
    public int dh;

    /**
     * @param dv - смещение по вертикали.
     * @param dh - смещение по горизонтали.
     */
    DiagDirs(int dv, int dh) {
        this.dv = dv;
        this.dh = dh;
    }
}
