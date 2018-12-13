package game.swing

import game.core.Game
import java.awt.BorderLayout
import javax.swing.JPanel

/**
 * Составная панель для настольной игры:
 *  *
 * доска с клетками
 *  *
 * панель истории партии (список ходов)
 *  *
 * управляющая панель (выбор игроков, размера доски, счет, ...)
 *
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
open class GamePanel(var game: Game) : JPanel(BorderLayout()) {
    //    protected var control: GameControlPanel
    private var adorned: AdornedBoard = AdornedBoard()
//    protected var jornal: MovesJornal

    init {
//        setLayout(GridLayout(3, 3))

//        var data: GridData
//
//        data = GridData(SWT.LEFT, SWT.FILL, false, true)
//        data.widthHint = 150

//        control = GameControlPanel(this, game)
//        control.setLayoutData(data)

//        data = GridData(SWT.FILL, SWT.FILL, true, true)
//        adorned = AdornedBoard(this)
//        adorned.setLayoutData(data)
    }

    /**
     * Вставить в панель игры доску с клетками.
     *
     * @param gameBoard
     * - вставляемая доска с клетками.
     */
    protected fun insertSquares(gameBoard: GameBoard) {
        adorned.insertSquares(gameBoard)
//
//        jornal = MovesJornal(this, gameBoard.board.history)
//        jornal.setLayoutData(GridData(SWT.RIGHT, SWT.FILL, false, true))
    }

    /**
     * Изменить размеры доски.
     *
     * @param nV
     * - количество вертикалей.
     * @param nH
     * - количество горизонталей.
     */
    public fun resizeBoard(nV: Int, nH: Int) {
        // Новые размеры доски и расстановка фигур.
        game.initBoard(nV, nH)

        adorned.resize(nV, nH)
    }
}