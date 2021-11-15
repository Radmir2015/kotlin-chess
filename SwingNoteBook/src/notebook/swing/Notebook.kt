package notebook.swing

import chess.Chess
import game.core.Game
import game.swing.GamePanel
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.Image
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JTabbedPane

fun main() {
    val tabbedPane = JTabbedPane()

    val frame = JFrame("Games Notebook")
    with(frame) {
        iconImage = ImageIcon("images/GamesNotebook.png").image
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        size = Dimension(800, 600)
        layout = BorderLayout()
        setLocationRelativeTo(null)
        add(tabbedPane, BorderLayout.CENTER)
        isVisible = true
    }

    tabbedPane.addGame(Chess())
    // Временно отключим другие игры
//    tabbedPane.addGame(JapanChess())
//    tabbedPane.addGame(NineMensMorris())
//    tabbedPane.addGame(Reversi())
//    tabbedPane.addGame(Checkers())
//    tabbedPane.addGame(TamerlanChess())
//    tabbedPane.addGame(ChinaChess())
//    tabbedPane.addGame(Renju())
//    tabbedPane.addGame(Go())
//    tabbedPane.addGame(Vikings())
//    tabbedPane.addGame(Halma())
}

private fun JTabbedPane.addGame(game: Game) {
    val img = "images/${game.iconImageFile}"
    val url: URL = game.javaClass.getResource(img)
    val iconImage = ImageIO.read(url)
    val icon = ImageIcon(iconImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT))
    addTab(game.name, icon, GamePanel(game))
}