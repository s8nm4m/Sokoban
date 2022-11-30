package figure

import Position
import pt.isel.canvas.*

const val TGSTARTW = 0
const val TGSTARTH = 218

data class Target(val dim: Dimension, val pos: Position)

/**
 * Drawing the target
 */
fun Target.draw(canvas: Canvas) {
    canvas.drawImage(
        "soko|$TGSTARTW,$TGSTARTH,${dim.width},${dim.height}",
        pos.col * dim.width,
        pos.line * dim.height,
        dim.width,
        dim.height
    )
}