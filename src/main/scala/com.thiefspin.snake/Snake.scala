package com.thiefspin.snake

import scalanative.native._
import SDL._
import SDLExtra._

import scala.scalanative.native.{Ptr, UByte, UInt, stackalloc}

object Snake extends App {
  var window: Ptr[Window]     = _
  var renderer: Ptr[Renderer] = _
  var snake: List[Point]      = _
  var pressed                 = collection.mutable.Set.empty[Keycode]
  var over                    = false
  var apple                   = Point(0, 0)
  var rand                    = new java.util.Random

  val title  = c"Snake"
  val width  = 800
  val height = 800
  val Up     = Point(0, 1)
  val Down   = Point(0, -1)
  val Left   = Point(1, 0)
  val Right  = Point(-1, 0)

  def newApple(): Point = {
    var pos = Point(0, 0)
    do {
      pos = Point(rand.nextInt(40), rand.nextInt(40))
    } while (snake.exists(_ == pos))
    pos
  }

  def drawColor(r: UByte, g: UByte, b: UByte): Unit =
    SDL_SetRenderDrawColor(renderer, r, g, b, 0.toUByte)
  def drawClear(): Unit =
    SDL_RenderClear(renderer)
  def drawPresent(): Unit =
    SDL_RenderPresent(renderer)
  def drawSquare(point: Point) = {
    val rect = stackalloc[Rect].init(point.x * 20, point.y * 20, 20, 20)
    SDL_RenderFillRect(renderer, rect)
  }
  def drawSnake(): Unit = {
    val head :: tail = snake
    drawColor(100.toUByte, 200.toUByte, 100.toUByte)
    drawSquare(head)
    drawColor(0.toUByte, 150.toUByte, 0.toUByte)
    tail.foreach(drawSquare)
  }
  def drawApple(): Unit = {
    drawColor(150.toUByte, 0.toUByte, 0.toUByte)
    drawSquare(apple)
  }

  def onDraw(): Unit = {
    drawColor(0.toUByte, 0.toUByte, 0.toUByte)
    drawClear()
    drawSnake()
    drawApple()
    drawPresent()
  }

  def gameOver(): Unit = {
    over = true
    println(s"Game is over, your score is: " + snake.length)
  }

  def move(newPos: Point) =
    if (!over) {
      if (newPos.x < 0 || newPos.y < 0 || newPos.x > 39 || newPos.y > 39) {
        println("out of bounds")
        gameOver()
      } else if (snake.exists(_ == newPos)) {
        println("hit itself")
        gameOver()
      } else if (apple == newPos) {
        snake = newPos :: snake
        apple = newApple()
      } else {
        snake = newPos :: snake.init
      }
    }

  def onIdle(): Unit = {
    val head :: second :: rest = snake
    val direction              = second - head
    val userDirection =
      if (pressed.contains(UP_KEY)) Up
      else if (pressed.contains(DOWN_KEY)) Down
      else if (pressed.contains(LEFT_KEY)) Left
      else if (pressed.contains(RIGHT_KEY)) Right
      else direction
    move(head + userDirection)
  }

  def init(): Unit = {
    rand.setSeed(java.lang.System.nanoTime)
    SDL_Init(INIT_VIDEO)
    window = SDL_CreateWindow(title, 0, 0, width, height, WINDOW_SHOWN)
    renderer = SDL_CreateRenderer(window, -1, VSYNC)
    snake = Point(10, 10) :: Point(9, 10) :: Point(8, 10) :: Point(7, 10) :: Nil
    apple = newApple()
  }

  def delay(ms: UInt): Unit =
    SDL_Delay(ms)

  def loop(): Unit = {
    val event = stackalloc[Event]
    while (true) {
      while (SDL_PollEvent(event) != 0) {
        event.type_ match {
          case QUIT_EVENT =>
            return
          case KEY_DOWN =>
            pressed += event.cast[Ptr[KeyboardEvent]].keycode
          case KEY_UP =>
            pressed -= event.cast[Ptr[KeyboardEvent]].keycode
          case _ =>
            ()
        }
      }
      onDraw()
      onIdle()
      delay((1000 / 12).toUInt)
    }
  }

  init()
  loop()
}