package figure

import Position
import pt.isel.canvas.Canvas

data class Target(val pos: Position)

fun Target.draw(canvas: Canvas) {
    canvas.drawImage("soko|0,218,40,54", pos.col * 40, pos.line * 54, 40, 54)
}