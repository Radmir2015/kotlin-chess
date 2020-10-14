package game.swing

import game.core.Game
import java.awt.Color
import java.awt.Insets
import javax.swing.BoxLayout
import javax.swing.JPanel

/**
 * Управляющая панель для настойки параметров игр
 * и показа текущего состояния игры.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
class GameControlPanel(game: Game) : JPanel(true) {
    init {
        background = CONTROL_COLOR
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

        val players = PlayersPanel(game)
        players.layout = BoxLayout(players, BoxLayout.Y_AXIS)
        add(players)
    }

    override fun getInsets(): Insets = Insets(5, 5, 5, 5)

    companion object {
        private val CONTROL_COLOR = Color(0, 192, 80, 255)
    }
}