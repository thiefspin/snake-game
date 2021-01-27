package com.thiefspin.snake

final case class Point(x: Int, y: Int) {
  def -(other: Point) = Point(this.x - other.x, this.y - other.y)
  def +(other: Point) = Point(this.x - other.x, this.y - other.y)
}