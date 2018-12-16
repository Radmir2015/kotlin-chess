package game.swing

import game.core.Game
import java.awt.BorderLayout
import java.awt.Color
import javax.swing.JPanel

/**
 * Управляющая панель для настойки параметров игр
 * и показа текущего состояния игры.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class GameControlPanel(game: Game) : JPanel(BorderLayout()) {

    init {
        background = CONTROL_COLOR

        val players = PlayersPanel(game)
        add(players, BorderLayout.CENTER)
    }

    companion object {
        private val CONTROL_COLOR = Color(0, 192, 80, 255)
    }
}