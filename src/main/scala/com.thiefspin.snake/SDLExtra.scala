package com.thiefspin.snake

import scalanative.native._
import SDL._

import scala.scalanative.native.Ptr

object SDLExtra {
  val INIT_VIDEO   = 0x00000020.toUInt
  val WINDOW_SHOWN = 0x00000004.toUInt
  val VSYNC        = 0x00000004.toUInt

  implicit class EventOps(val self: Ptr[Event]) extends AnyVal {
    def type_ = !(self._1)
  }

  val QUIT_EVENT = 0x100.toUInt

  implicit class RectOps(val self: Ptr[Rect]) extends AnyVal {
    def init(x: Int, y: Int, w: Int, h: Int): Ptr[Rect] = {
      !(self._1) = x
      !(self._2) = y
      !(self._3) = w
      !(self._4) = h
      self
    }
  }

  val KEY_DOWN  = 0x300.toUInt
  val KEY_UP    = (0x300 + 1).toUInt
  val RIGHT_KEY = 1073741903
  val LEFT_KEY  = 1073741904
  val DOWN_KEY  = 1073741905
  val UP_KEY    = 1073741906

  implicit class KeyboardEventOps(val self: Ptr[KeyboardEvent])
    extends AnyVal {
    def keycode: Keycode = !(self._8._2)
  }
}