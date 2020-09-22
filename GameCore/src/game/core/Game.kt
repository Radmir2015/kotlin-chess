package game.core

import game.core.listeners.MovePieceListener
import game.core.listeners.MovePiecePromptListener
import game.core.listeners.PutPieceListener
import game.core.listeners.PutPiecePromptListener
import java.util.*

/**
 * Общий предок всех игр.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
abstract class Game protected constructor() : IScorable {
    /**
     * Доска на которой происходит игра.
     */
    @JvmField
    val board: Board = Board(0, 0)

    /**
     * Выдать счет для фигур заданного цвета.
     *
     * @param color цвет фигур.
     * @return счет для заданного цвета фигур.
     */
    override fun getScore(color: PieceColor): Int = board.getPieces(color).size

    /**
     * Получить список всех игрокода для заданной игры.
     *
     * @param game заданная игра.
     * @return список игроков для этой игры.
     */
    fun getPlayers(game: Class<out Game?>): List<IPlayer> = allPlayers[game]!!

    /**
     * Задать для игры новые размеры доски.
     *
     * @param nV количество вертикалей.
     * @param nH количество горизонталей.
     */
    open fun initBoardPanel(nV: Int, nH: Int) {
        board.reset(nV, nH)
    }

    /**
     * Задать для панели, представляющей доску игры,
     * слушателей нажатия и перемещения мыши.
     *
     * @param board панель доски для инициализации
     */
    open fun initBoardPanel(board: IBoardPanel) {
        when (moveKind) {
            MoveKind.PIECE_MOVE -> {
                board.setMouseMoveListener(MovePiecePromptListener(board))
                board.setMouseClickListener(MovePieceListener(board))
            }
            MoveKind.PIECE_PUT -> {
                board.setMouseMoveListener(PutPiecePromptListener(board))
                board.setMouseClickListener(PutPieceListener(board))
            }
            else -> {
            }
        }
    }

    /**
     * Сделать доску умалчиваемого размера
     * с умалчиваемой расстановкой фигур на доске.
     */
    abstract fun initBoardDefault()

    /**
     * Выдать карту соответствия фигуры заданного параметром цвета.
     * Файлы с изображениями фигур должны находится в папке images
     * вложенной в папку где содерижится класс-потомок класса Game.
     * <pre>
     * Chess.java
     * images/
     * wRook.gif
     * bRook.gif
     * ...
    </pre> *
     *
     * @param color заданный цвет фигуры.
     * @return карта соответствия: {класс-фигуры, имя-файла-с-изображениием-фигуры}
     */
    abstract fun getPieceImages(color: PieceColor): MutableMap<Class<out Piece>, String>

    /**
     * Выдать вид хода для данной игры.
     *
     * @return вид хода для данной игры.
     */
    abstract val moveKind: MoveKind

    /**
     * Выдать вид доски для данной игры.
     * @return вид доски для данной игры.
     */
    abstract val boardKind: BoardKind

    /**
     * Выдать имя игры для ее пользователя.
     *
     * @return имя игры
     */
    abstract val name: String

    /**
     * Выдать имя файла с изображением пиктограммы для данной игры.
     *
     * @return имя файла
     */
    abstract val iconImageFile: String?

    /**
     * Выдать фигуру заданного цвета и поставить ее заданную клетку доски.
     * Например, для шахмат выдать фигуру для превращения пешки.
     * Для шашек выдать дамку.
     * Для реверси и рендзю выдать камень для постановки его на доску.
     *
     * @param square     клетка для фигуры
     * @param pieceColor цвет фигуры
     * @return новая фигура на доске.
     */
    abstract fun getPiece(square: Square, pieceColor: PieceColor): Piece

    companion object {
        /**
         * Карта для регистрации игроков для каждой из игр.
         */
        private val allPlayers: MutableMap<Class<out Game>, MutableList<IPlayer>> = mutableMapOf()

        /**
         * Добавить игрока для данной игры.
         *
         * @param game   игра для которой добавляют игрока.
         * @param player игрок (программа или человек).
         */
        @JvmStatic
        fun addPlayer(game: Class<out Game>, player: IPlayer) {
            var players = allPlayers[game]
            if (players == null) {
                // Игроков для данной игры еще не добавляли.
                players = ArrayList()
                allPlayers[game] = players
            }
            players.add(player)
        }
    }
}