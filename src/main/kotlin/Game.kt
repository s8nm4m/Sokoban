import figure.*
import figure.Target
import pt.isel.canvas.Canvas

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
    val newBoxList = boxes.move(nextMan, walls, targets)
    return copy(man = nextMan, boxes = newBoxList)
}

fun List<Position>.move(nextMan: Man, walls: List<Position>, targets: List<Position>): List<Position> {
    if (contains(nextMan.pos)) {
        val newBox = nextMan.pos.newPos(nextMan.dir)
        return map { i -> if (i == nextMan.pos) newBox else i }
    }
    return this
}
