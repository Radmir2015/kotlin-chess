package game.core;

/**
 * 8-направлений по вертикали, горизонали и диагоналям.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public enum Dirs {
    LEFT_UP(-1, -1), UP(+0, -1), RIGHT_UP(+1, -1),
    LEFT(-1, +0), RIGHT(+1, +0),
    LEFT_DOWN(-1, +1), DOWN(+0, +1), RIGHT_DOWN(+1, +1);

    public static final
    Dirs[] ALL = {
            LEFT_UP, UP, RIGHT_UP,
            LEFT, RIGHT,
            LEFT_DOWN, DOWN, RIGHT_DOWN
    };

    public static final
    Dirs[] DIAGONAL = {
            LEFT_UP, RIGHT_UP,
            LEFT_DOWN, RIGHT_DOWN
    };

    public static final
    Dirs[] LINES = {
            UP,
            LEFT, RIGHT,
            DOWN,
    };

    public static final
    Dirs[] VERTICAL = {
            UP,
            DOWN,
    };

    public static final
    Dirs[] HORIZONTAL = {
            LEFT, RIGHT,
    };

    public static final
    Dirs[][] PAIRS = {
            {LEFT, RIGHT},
            {UP, DOWN},
            {LEFT_UP, RIGHT_DOWN},
            {LEFT_DOWN, RIGHT_UP}
    };

    /**
     * Смещение по вертикали.
     */
    public final int dv;

    /**
     * Смещение по горизонтали.
     */
    public final int dh;

    /**
     * @param dv - смещение по вертикали.
     * @param dh - смещение по горизонтали.
     */
    Dirs(int dv, int dh) {
        this.dv = dv;
        this.dh = dh;
    }

    public int getDv() {
        return dv;
    }

    public int getDh() {
        return dh;
    }
}
