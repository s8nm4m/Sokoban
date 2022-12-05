package figure

import Position
import pt.isel.canvas.Canvas

const val TG_START_W = 0
const val TG_START_H = 218

data class Target(val dim: Dimension, val pos: Position)

/**
 * Drawing the target
 */
fun Target.draw(canvas: Canvas) {
    canvas.drawImage(
        "soko|$TG_START_W,$TG_START_H,${dim.width},${dim.height}",
        pos.col * dim.width,
        pos.line * dim.height,
        dim.width,
        dim.height
    )
}