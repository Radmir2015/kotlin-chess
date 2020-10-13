package reversi.players

import game.core.IPieceProvider
import game.core.Move
import game.core.Square
import game.core.moves.ICaptureMove
import game.core.moves.IPutMove
import java.util.*

/**
 * Тигра - игрок в реверси:<br></br>
 *
 *  *  Знает что фигуры в углах доски окружить невозможно.
 *  *  Знает что фигуры на краях окружить сложнее чем в центре доски.
 *  *  Выбирает ход с захватом максимального количества фигур врага.
 *
 *
 * @author Екатерина Козак
 */
class Tiger(pieceProvider: IPieceProvider) : ReversiPlayer(pieceProvider) {
    override val comparator: Comparator<in Move> = Comparator { m1: Move, m2: Move -> getMoveWeight(m2) - getMoveWeight(m1) }

    override val name: String = "Тигра"
    override val authorName: String = "Екатерина Козак"

    /**
     * Определить стоимость хода.
     *
     * @param move - ход который оцениваем.
     * @return стоимость хода.
     */
    private fun getMoveWeight(move: Move): Int {
        val putMove = move as IPutMove
        val target = putMove.target
        if (isCorner(target)) return 1000 // Встали в угол.
        if (isBorder(target)) return 900 // Встали на край доски.
        if (isBigCross(target)) return 800
        if (isSquareX(target)) return -900
        if (isSquareC(target)) return -800
        if (move is ICaptureMove) {
            // Ход - взятие фигур врага.
            val capture = move as ICaptureMove

            // Правило реверси - брать меньше фигур!
            // В результате - больший выбор ходов потом.
            // Фигуры противника заберем в конце игры.
            return 64 - capture.captured.size
        }
        return 0
    }

    /**
     * @param target - проверяемая клетка.
     * @return Если вы сыграете на это поле, ваш противник легко займет угол.
     */
    private fun isSquareX(target: Square): Boolean {
        // TODO Козак
        // http://othello.gomel.by/stepanov/x-pole/
        return false
    }

    /**
     * Вторым наиболее плохим полем является C-поле. Оно само уже находится на
     * стороне и для того чтобы через него попасть в угол, надо постараться
     * немного больше.
     *
     * @param target - проверяемая клетка.
     * @return это плохое C-поле.
     */
    private fun isSquareC(target: Square): Boolean {
        // TODO Козак
        // http://othello.gomel.by/stepanov/c-pole/
        return false
    }

    /**
     * Эта клетка на большом кресте?
     * Ход на крест даёт возможность делать минимальные ходы и в будущем,
     * а не только сейчас.
     * Ваши фишки будут все время внутри соперника, а его снаружи,
     * что очень приветствуется в Отелло.
     *
     * @param target - проверяемая клетка.
     * @return Эта клетка на большом кресте?
     */
    private fun isBigCross(target: Square): Boolean {
        // TODO Козак
        // http://othello.gomel.by/stepanov/bolwoy-krest/
        return false
    }

    /**
     * Тигра - игрок в реверси.
     */
    init {
        this.pieceProvider = pieceProvider!!
    }
}