package figure

import CELL
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
        "soko|$TG_START_W,$TG_START_H,${CELL.width},${CELL.height}",
        (pos.col + dim.width / 2) * CELL.width,
        (pos.line + dim.height / 2) * CELL.height,
        CELL.width,
        CELL.height
    )
}