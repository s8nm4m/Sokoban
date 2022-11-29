package figure

import Position
import pt.isel.canvas.Canvas

const val WALLSTARTW = 41
const val WALLSTARTH = 218
const val WALLWIDTH = 40
const val WALLHEIGHT = 54


data class Wall(val pos: Position)

fun Wall.draw(canvas: Canvas) {
    canvas.drawImage(
        "soko|$WALLSTARTW,$WALLSTARTH,$WALLWIDTH,$WALLHEIGHT",
        pos.col * WALLWIDTH, pos.line * WALLHEIGHT+1, WALLWIDTH, WALLHEIGHT
    )
}