import figure.*
import pt.isel.canvas.*

data class Game(
    val dim: Dimension, val man: Man, // Fase 1 e 2
    val walls: List<Position>, // Fase 3
    val boxes: List<Position>, // Fase 4
    val targets: List<Position> // Fase 5
)

fun Game.draw(canvas: Canvas) {
    canvas.erase()
    walls.map { Wall(it).draw(canvas) }
    targets.map { Target(it).draw(canvas) }
    boxes.map { Box(it, false, false).draw(canvas) }
    man.draw(canvas)
}

fun Game.move(k: Int): Game {
    val nextMan = man.move(k, walls)
    return copy(man = nextMan)
}