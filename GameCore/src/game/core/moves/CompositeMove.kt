package game.core.moves

import game.core.GameOver
import game.core.Piece
import game.core.Square
import java.util.*

/**
 * Составной ход - последовательность простых ходов фигурой одного цвета.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
open class CompositeMove<T : ITransferMove> : ITransferMove {
    /**
     * Последовательность простых ходов.
     */
    val moves: ArrayList<T> = ArrayList()

    override val source: Square
        get() = moves[0].source

    override val target: Square
        get() = moves[moves.size - 1].target

    /**
     * фигура которая делает ход.
     */
    final override var piece: Piece? = null

    constructor(move: CompositeMove<T>) {
        piece = move.piece
        moves.addAll(move.moves)
    }

    constructor(move: T) {
        piece = move.piece
        addMove(move)
    }

    protected constructor(p: Piece) {
        piece = p
    }

    /**
     * Добавить простой ход фигурой фигуры к последовательности ходов.
     *
     * @param move - простой ход фигурой
     */
    fun addMove(move: T) {
        moves.add(move)
    }

    @Throws(GameOver::class)
    override fun doMove() {
        for (move in moves) try {
            move.doMove()
        } catch (e: GameOver) {
            throw GameOver(e.result)
        }
    }

    override fun undoMove() {
        for (k in moves.indices.reversed()) moves[k].undoMove()
    }

    val clone: CompositeMove<T>
        get() = CompositeMove(this)

    val isEmpty: Boolean
        get() = moves.isEmpty()

    /**
     * @param square - проверяемая клетка.
     * @return Допустим ли ход на клетку square.
     */
    fun isAcceptable(square: Square): Boolean {
        // Если фигура уже была на этой клетке, то ход недопустим.
        return moves
                .stream()
                .noneMatch { move: T -> move.source == square }
    }

    override fun toString(): String {
        if (moves.isEmpty()) return "????"

        val s = StringBuilder("$source")
        for (m in moves) s.append("x").append(m.target)
        return s.toString()
    }
}