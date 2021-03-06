package game.core

enum class MoveKind {
    /**
     * Не определен.
     */
    NONE,

    /**
     * Фигура ставится на доску.
     * Пример - реверси.
     */
    PIECE_PUT,

    /**
     * Фигура перемещается с одной клетски на другую.
     * Пример - шахматыю
     */
    PIECE_MOVE,

    /**
     * Фигура перемещается через последовательность клеток.
     * Пример - шашки.
     */
    PIECE_TRACK
}