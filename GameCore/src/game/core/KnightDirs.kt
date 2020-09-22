package game.core

/**
 * 8-направлений для хода коня.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
enum class KnightDirs(
        /**
         * Смещение по вертикали.
         */
        private val dv: Int,
        /**
         * Смещение по горизонтали.
         */
        private val dh: Int,
) {
    LEFT_UP1(-1, +2),
    RIGHT_UP1(+2, +1),
    LEFT_UP2(-2, +1),
    RIGHT_UP2(+1, +2),
    LEFT_UP3(-2, -1),
    RIGHT_UP3(-1, +2),
    LEFT_UP4(-1, -2),
    RIGHT_UP4(-2, +1);

    companion object {
        val ALL = arrayOf(LEFT_UP1, RIGHT_UP1, LEFT_UP2, RIGHT_UP2, LEFT_UP3, RIGHT_UP3, LEFT_UP4, RIGHT_UP4)
    }
}