package game.core

/**
 * 4-направления по  диагоналям.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
enum class DiagDirs(
        /**
         * Смещение по вертикали.
         */
        private val dv: Int,
        /**
         * Смещение по горизонтали.
         */
        private val dh: Int,
) {
    LEFT_UP(-1, -1), RIGHT_UP(+1, -1), LEFT_DOWN(-1, +1), RIGHT_DOWN(+1, +1);

    companion object {
        val ALL = arrayOf(LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN)
    }
}