package game.core

/**
 * 4-направления по вертикали и горизонали.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
enum class LineDirs(
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
    UP(+0, -1), LEFT(-1, +0), RIGHT(+1, +0), DOWN(+0, +1);

    companion object {
        @JvmField
        val ALL = arrayOf(UP, LEFT, RIGHT, DOWN)
    }
}