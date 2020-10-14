package chinachess.players

import chinachess.pieces.King
import game.core.Move
import game.core.moves.ICaptureMove
import game.core.moves.ITransferMove
import java.util.*

/**
 * Конфуций - первый профессиональный педагог Поднебесной.<br></br>
 * [ Пять постоянств праведного человека (Конфуций)](https://ok.ru/tantra.yoga/topic/65490423540658)
 */
class Confucious : ChinaChessPlayer() {
    override val comparator: Comparator<in Move> = Comparator { m1: Move, m2: Move -> getWeight(m2) - getWeight(m1) }
    override val name = "Конфуций"
    override val authorName = "Осинцев Александр"

    override fun toString() = name

    /**
     * Задать вес для хода.
     *
     * @param move - ход
     * @return оценка хода.
     */
    private fun getWeight(move: Move): Int {
        val transfer = move as ITransferMove
        val source = transfer.source
        val target = transfer.target
        val thePiece = source.getPiece() ?: return 0

        if (move is ICaptureMove) {
            // Ход - взятие фигуры врага.
            val capture = move as ICaptureMove
            val capturedSquare = capture.captured[0]
            val capturedPiece = capturedSquare.getPiece()

            // У захвата короля врага наивысший приоритет.
            return if (capturedPiece is King) 1000 else 999

            // Пока берем любую фигуру.
        }

        // Из всех ходов без взятия фигуры врага лучший ход
        // который максимально приближает к королю врага.
        val enemyKing = getEnemyKing(thePiece) ?: return 0
        return MAX_DISTANCE - target.distance(enemyKing.square)
    }
}