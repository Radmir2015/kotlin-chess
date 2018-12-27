package halma.swing.images

import java.awt.Image
import javax.imageio.ImageIO
import javax.swing.ImageIcon

/**
 * Изображения игры [
 * Халма](https://ru.wikipedia.org/wiki/https://ru.wikipedia.org/wiki/%D0%A5%D0%B0%D0%BB%D0%BC%D0%B0)
 *
 * @author [Romanov V.Y.](mailto:vladimir.romanov@gmail.com)
 */
object HalmaImages {
    private val imageHalma: Image = ImageIO.read(javaClass.getResource("icoHalma.png"))
    val icoHalma = ImageIcon(imageHalma.getScaledInstance(20, 20, Image.SCALE_DEFAULT))
}
