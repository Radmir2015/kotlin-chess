package game.core;

/**
 * 8-направлений для хода коня.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public enum KnightDirs {
    LEFT_UP1(-1, +2), RIGHT_UP1(+2, +1),
    LEFT_UP2(-2, +1), RIGHT_UP2(+1, +2),
    LEFT_UP3(-2, -1), RIGHT_UP3(-1, +2),
    LEFT_UP4(-1, -2), RIGHT_UP4(-2, +1);

    public static final
    KnightDirs[] ALL = {
            LEFT_UP1, RIGHT_UP1,
            LEFT_UP2, RIGHT_UP2,
            LEFT_UP3, RIGHT_UP3,
            LEFT_UP4, RIGHT_UP4
    };

    /**
     * Смещение по вертикали.
     */
    private final int dv;

    /**
     * Смещение по горизонтали.
     */
    private final int dh;

    /**
     * @param dv - смещение по вертикали.
     * @param dh - смещение по горизонтали.
     */
    KnightDirs(int dv, int dh) {
        this.dv = dv;
        this.dh = dh;
    }
}
