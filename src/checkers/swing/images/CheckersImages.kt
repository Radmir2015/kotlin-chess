/**
 *
 */
package checkers.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Изображения для игры в шашки.
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
object CheckersImages {
    private val imageCheckers: Image = ImageIO.read(javaClass.getResource("iconCheсkers.png"))
    val icoCheckers = ImageIcon(imageCheckers.getScaledInstance(20, 20, Image.SCALE_DEFAULT))

    val imageKingWhite: Image = ImageIO.read(javaClass.getResource("wKing.png"))
    val imageKingBlack: Image = ImageIO.read(javaClass.getResource("bKing.png"))

    val imageManBlack: Image = ImageIO.read(javaClass.getResource("bMan.png"))
    val imageManWhite: Image = ImageIO.read(javaClass.getResource("wMan.png"))
}