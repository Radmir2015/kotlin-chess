package reversi.players

import game.core.IPieceProvider
import game.core.Move
import game.core.moves.ICaptureMove
import game.core.moves.IPutMove
import java.util.*

/**
 * Сова - игрок в реверси.
 * Знает что фигуры в углах доски окружить невозможно.
 * Знает что фигуры на краях окружить сложнее чем в центре доски.
 * Выбирает ход с захватом максимального количества фигур врага.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Owl(pieceProvider: IPieceProvider) : ReversiPlayer(pieceProvider) {
    override val comparator: Comparator<in Move> = Comparator { m1: Move, m2: Move -> getMoveWeight(m2) - getMoveWeight(m1) }

    override val name: String = "Сова"
    override val authorName: String = "Романов В.Ю."

    private fun getMoveWeight(move: Move): Int {
        val putMove = move as IPutMove
        val target = putMove.target
        if (isCorner(target)) return 1000 // Встали в угол.
        if (isBorder(target)) return 900  // Встали на край доски.
        if (move is ICaptureMove) {
            // Ход - взятие фигур врага.
            val capture = move as ICaptureMove

            // Цена хода - сколько взяли фигур.
            return capture.captured.size
        }
        return 0
    }

    /**
     * Сова - игрок в реверси. Знает что фигуры в углах доски окружить
     * невозможно. Знает что фигуры на краях окружить сложнее чем в центре
     * доски. Выбирает ход с захватом максимального количества фигур врага.
     */
    init {
        this.pieceProvider = pieceProvider
    }
}