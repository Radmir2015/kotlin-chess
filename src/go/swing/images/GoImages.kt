package go.swing.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Изображения для игры
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
object GoImages {
    private val imageGo = ImageIO.read(javaClass.getResource("icoGo.png"))
    val icoGo = ImageIcon(imageGo.getScaledInstance(20, 20, Image.SCALE_DEFAULT))
}
