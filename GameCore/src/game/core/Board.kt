package game.core

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

/**
 * Доска для расстановки фигур.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class Board(
        /**
         * Количество вертикалей на доске.
         */
        @JvmField var nV: Int = 0,
        /**
         * Количество горизонталей на доске.
         */
        @JvmField var nH: Int = 0,
)
    : Observable() {
    /**
     * История партии (последовательность ходов игры).
     */
    @JvmField
    val history: History = History(this)

    /**
     * Игроки для этой доски.
     */
    private val players: MutableMap<PieceColor, IPlayer> = mutableMapOf(
            PieceColor.WHITE to IPlayer.HOMO_SAPIENCE,
            PieceColor.BLACK to IPlayer.HOMO_SAPIENCE)

    /**
     * Клетки доски.
     */
    private var squares: Array<Array<Square>>

    /**
     * Цвет фигуры которая должна сделать ход.
     */
    var moveColor = PieceColor.WHITE
        private set

    /**
     * Изменить размеры доски и очистить историю игры.
     *
     * @param nV - количество вертикалей доски.
     * @param nH - количество горизонталей доски.
     */
    fun reset(nV: Int, nH: Int) {
        this.nV = nV
        this.nH = nH

        squares = Array(nV) { v -> Array(nH) { h -> Square(this, v, h) } }
        history.clear()
        moveColor = PieceColor.WHITE
        setBoardChanged()
    }

    /**
     * Уведомить обозревателей доски (классы реализующие интерфейс **Observable**)<br></br>
     * что на доске произошли изменения.
     *
     * @see Observable
     *
     * @see java.util.Observer
     */
    fun setBoardChanged() {
        // Вызвать protected метод базового класса - Observer.
        super.setChanged()
        super.notifyObservers()
    }

    /**
     * Смена цвета (игрока который должен сделать ход).
     */
    fun changeMoveColor() {
        while (true) {
            moveColor = getOpponentColor(moveColor)
            val player = players[moveColor]
            if (player === IPlayer.HOMO_SAPIENCE) break // Ход сделает человек мышкой.
            try {
                player!!.doMove(this, moveColor)
            } catch (e: GameOver) {
                break
            }
        }
    }

    /**
     * Запуск цикла передачи ходов от одного игрока к другому.
     * Выход из цикла при завершении игры или
     * при передаче хода игроку - человеку.
     */
    fun startGame() {
        while (true) {
            val player = players[moveColor]
            if (player === IPlayer.HOMO_SAPIENCE) break // Ход сделает человек.

            try {
                player!!.doMove(this, moveColor)
            } catch (e: GameOver) {
                break
            }
            moveColor = getOpponentColor(moveColor)
        }
    }

    /**
     * Вернуть клетку доски.
     *
     * @param v - вертикаль клетки.
     * @param h - горизонталь клетки.
     * @return клетка с задаными вертикалью и горизонталью.
     */
    fun getSquare(v: Int, h: Int): Square? = squares[v][h]

    /**
     * Проверка выхода координат клетки за границы доски.
     *
     * @param v - вертикаль клетки
     * @param h - горизонталь клетки
     * @return есть ли клетка с такими координатами на доске.
     */
    fun onBoard(v: Int, h: Int): Boolean = when {
        v < 0 -> false
        h < 0 -> false
        else -> h <= nH - 1 && v <= nV - 1
    }

    /**
     * @param v - вертикаль
     * @param h - горизонталь
     * @return Пуста ли клетка с заданными координатами?
     */
    fun isEmpty(v: Int, h: Int): Boolean = getSquare(v, h)!!.isEmpty

    /**
     * игрок белыми фигурами
     */
    var whitePlayer: IPlayer
        get() = players[PieceColor.WHITE]!!
        set(value) {
            players[PieceColor.WHITE] = value
            setBoardChanged()
        }

    /**
     * игрок черными фигурами
     */
    var blackPlayer: IPlayer
        get() = players[PieceColor.BLACK]!!
        set(value) {
            players[PieceColor.BLACK] = value
            setBoardChanged()
        }

    /**
     * Выдать список всех расположенных на доске фигур заданного цвета.
     *
     * @param color - цвет фигуры.
     * @return - список фигур.
     */
    fun getPieces(color: PieceColor): List<Piece> {
        val pieces: MutableList<Piece> = mutableListOf()
        for (v in 0 until nV)
            for (h in 0 until nH) {
                val s = getSquare(v, h)
                val p = s!!.getPiece() ?: continue
                if (p.color != color) continue
                pieces.add(p)
            }
        return pieces
    }

    /**
     * Выдать список всех пустых клеток доски.
     *
     * @return - список всех клеток доски.
     */
    val emptySquares: List<Square>
        get() {
            val emptySquares: MutableList<Square> = mutableListOf()
            for (v in 0 until nV)
                for (h in 0 until nH) {
                    val square = getSquare(v, h)
                    if (square!!.isEmpty) emptySquares.add(square)
                }
            return emptySquares
        }

    /**
     * Для заданной фигуры найти список клеток, на которые ход данной фигурой
     * допустим.
     *
     * @param piece - проверяемая фигура.
     * @return список допустимых для хода клеток.
     */
    fun getPieceTargets(piece: Piece): List<Square> {
        val targets: MutableList<Square> = mutableListOf()
        for (v in 0 until nV)
            for (h in 0 until nH) {
                val target = getSquare(v, h)
                if (piece.isCorrectMove(target!!)) targets.add(target)
            }
        return targets
    }

    /**
     * Выдать список всех клеток доски.
     *
     * @return - список
     */
    fun getSquares(): MutableList<Square> {
        val allSquares: MutableList<Square> = ArrayList()
        for (v in 0 until nV)
            for (h in 0 until nH)
                getSquare(v, h)?.let { allSquares.add(it) }
        return allSquares
    }

    /**
     * Максимальное расстояние между клетками доски.
     *
     * @return расстояние между клетками
     */
    fun maxDistance(): Int = nH + nV

    companion object {
        /**
         * Дать цвет противоположный заданному цвету.
         *
         * @param color - заданный цвет фигуры.
         * @return противоположный цвет фигур.
         */
        @JvmStatic
        fun getOpponentColor(color: PieceColor): PieceColor =
                if (color == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
    }

    init {
        this.squares = Array(nV) { v -> Array(nH) { h -> Square(this, v, h) } }
        whitePlayer = IPlayer.HOMO_SAPIENCE
        blackPlayer = IPlayer.HOMO_SAPIENCE
        reset(0, 0)
    }
}