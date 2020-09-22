package game.core.moves

/**
 * Ход, который может быть частью длинного хода (track) состоящего
 * из нескольких ходов одной фигурой.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
interface ITrackMove : ITransferMove {
    /**
     * Существует ли у этого хода продолжение
     * - допустимый ход той же фигурой.
     *
     * @return есть ли у хода продолжение.
     */
    operator fun hasNext(): Boolean
}