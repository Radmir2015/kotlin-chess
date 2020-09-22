package game.core

/**
 * 8-направлений по вертикали, горизонали и диагоналям.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
enum class Dirs(
        /**
         * Смещение по вертикали.
         */
        @JvmField
        val dv: Int,
        /**
         * Смещение по горизонтали.
         */
        @JvmField
        val dh: Int,
) {
    LEFT_UP(-1, -1), UP(+0, -1), RIGHT_UP(+1, -1),
    LEFT(-1, +0), RIGHT(+1, +0),
    LEFT_DOWN(-1, +1), DOWN(+0, +1), RIGHT_DOWN(+1, +1);

    companion object {
        @JvmField
        val ALL = arrayOf(LEFT_UP, UP, RIGHT_UP, LEFT, RIGHT, LEFT_DOWN, DOWN, RIGHT_DOWN)

        @JvmField
        val DIAGONAL = arrayOf(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN)
        val LINES = arrayOf(UP, LEFT, RIGHT, DOWN)
        val VERTICAL = arrayOf(UP, DOWN)
        val HORIZONTAL = arrayOf(LEFT, RIGHT)

        @JvmField
        val PAIRS = arrayOf(arrayOf(LEFT, RIGHT), arrayOf(UP, DOWN), arrayOf(LEFT_UP, RIGHT_DOWN), arrayOf(LEFT_DOWN, RIGHT_UP))
    }
}