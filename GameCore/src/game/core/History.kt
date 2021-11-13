package game.core

import com.google.firebase.database.FirebaseDatabase

/**
 * История игры (список ходов сделанных фигурами на доске).
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class History internal constructor(
    /**
     * Доска на которой идет игра.
     */
    val board: Board,
) {
    /**
     * Номер текущего хода на доске.
     */
    var curMoveNumber = -1
        private set

    /**
     * Список ходов сделанных на доске.
     */
    val moves: MutableList<Move> = mutableListOf()

    /**
     * Результат игры.
     */
    var result = GameResult.UNKNOWN

    fun clear() {
        curMoveNumber = -1
        moves.clear()
        result = GameResult.UNKNOWN
    }

    /**
     * Добавить ход в историю игры.
     *
     * @param move - ход игры
     */
    fun addMove(move: Move) {
        moves.add(move)
        curMoveNumber++

        val newMove = mutableMapOf("history" to moves.toString()).toMap()
        val ref = FirebaseDatabase.getInstance().getReference("game")
        ref.updateChildren(newMove) { _, _ ->
            println("New move added!")
        }
    }

    fun addMoveSilently(move: Move) {
        moves.add(move)
        curMoveNumber++
    }

    /**
     * @return Вернуть текущий ход.
     */
    val curMove: Move?
        get() = if (curMoveNumber == -1) null else moves[curMoveNumber]

    /**
     * Сместиться на первый ход.
     */
    fun toFirstMove() {
        while (curMoveNumber >= 1) {
            moves[curMoveNumber].undoMove()
            curMoveNumber--
        }
    }

    /**
     * Сместиться на последний ход.
     */
    fun toLastMove() {
        while (curMoveNumber < moves.size - 1) {
            try {
                moves[curMoveNumber].doMove()
            } catch (e: GameOver) {
                result = e.result
            }
            curMoveNumber++
        }
    }

    /**
     * Сместиться на следующий ход.
     */
    fun toNextMove() {
        if (curMoveNumber < moves.size - 1) try {
            moves[++curMoveNumber].doMove()
        } catch (e: GameOver) {
            result = e.result
        }
    }

    /**
     * Сместиться на предыдущий ход.
     */
    fun toPrevMove() {
        if (curMoveNumber >= 0) moves[curMoveNumber--].undoMove()
    }

    /**
     * @return Вернуть последний ход.
     */
    val lastMove: Move?
        get() = if (moves.isEmpty()) null else moves[moves.size - 1]

    /**
     * Выдать номер хода.
     *
     * @param move - для какого хода выдается номер.
     */
    fun getMoveNumber(move: Move): Int = moves.indexOf(move)

    /**
     * Сместиться на ход с номером **n**.
     *
     * @param n - номер хода
     */
    fun toMove(n: Int) {
        if (n < curMoveNumber) while (n < curMoveNumber) toPrevMove()
        if (curMoveNumber < n) while (curMoveNumber < n) toNextMove()
    }

    override fun toString(): String {
        val s = StringBuilder()
        for ((k, m) in moves.withIndex()) {
            val odd = k % 2 == 0
            val number = if (!odd) "" else "${1 + k / 2}. "
            val nl = if (!odd) "\n" else ""
            s.append("$number$m $nl")
        }
        return s.toString()
    }

    companion object {
        fun clearHistoryString(moves: String): String {
            return moves
                .replace("[", "")
                .replace("]", "")
                .replace("x", "-")
        }
    }
}