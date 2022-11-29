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
    walls.forEach { Wall(it).draw(canvas) }
    targets.forEach { Target(it).draw(canvas) }
    boxes.forEach { Box(it).draw(canvas, targets) }
    man.draw(canvas, boxes)
}

fun Game.move(k: Int): Game {
    val nextMan = man.move(k, walls, boxes)
    val newBoxList = boxes.move(nextMan, walls)
    return copy(man = nextMan, boxes = newBoxList)
}

fun List<Position>.move(nextMan: Man, walls: List<Position>): List<Position> {
    if (contains(nextMan.pos)) {
        val newBox = nextMan.pos.newPos(nextMan.dir)
        val newList = map { i -> if (i == nextMan.pos) newBox else i }
        newList.forEach { if (walls.contains(it)) return this }
        return newList
    }
    return this
}
