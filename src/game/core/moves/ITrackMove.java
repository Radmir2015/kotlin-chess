package game.core.moves;

/**
 * Ход, который может быть частью длинного хода (track) состоящего
 * из нескольких ходов одной фигурой.
 *
 * @author <a href="mailto:vladimir.romanov@gmail.com">Romanov V.Y.</a>
 */
public interface ITrackMove extends ITransferMove {
    /**
     * Существует ли у этого хода продолжение
     * - допустимый ход той же фигурой.
     *
     * @return
     */
    boolean hasNext();
}
