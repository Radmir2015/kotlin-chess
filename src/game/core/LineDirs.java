package game.core;

/**
 * 4-направления по вертикали и горизонали.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public enum LineDirs {
    UP(+0, -1),
    LEFT(-1, +0), RIGHT(+1, +0),
    DOWN(+0, +1);

    public static final
    LineDirs[] ALL = {
            UP,
            LEFT, RIGHT,
            DOWN
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
    LineDirs(int dv, int dh) {
        this.dv = dv;
        this.dh = dh;
    }
}
