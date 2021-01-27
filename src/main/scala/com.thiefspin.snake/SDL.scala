package com.thiefspin.snake

import scala.scalanative.native._
import scala.scalanative._

@extern
@link("SDL2")
object SDL {
  type Window   = CStruct0
  type Renderer = CStruct0

  def SDL_Init(flags: UInt): Unit = extern
  def SDL_CreateWindow(title: CString,
    x: CInt,
    y: CInt,
    w: Int,
    h: Int,
    flags: UInt): Ptr[Window] = extern
  def SDL_Delay(ms: UInt): Unit                  = extern
  def SDL_CreateRenderer(win: Ptr[Window],
    index: CInt,
    flags: UInt): Ptr[Renderer] = extern

  type _56   = Nat.Digit[Nat._5, Nat._6]
  type Event = CStruct2[UInt, CArray[Byte, _56]]

  def SDL_PollEvent(event: Ptr[Event]): CInt = extern

  type Rect = CStruct4[CInt, CInt, CInt, CInt]

  def SDL_RenderClear(renderer: Ptr[Renderer]): Unit = extern
  def SDL_SetRenderDrawColor(renderer: Ptr[Renderer],
    r: UByte,
    g: UByte,
    b: UByte,
    a: UByte): Unit = extern
  def SDL_RenderFillRect(renderer: Ptr[Renderer], rect: Ptr[Rect]): Unit =
    extern
  def SDL_RenderPresent(renderer: Ptr[Renderer]): Unit = extern

  type KeyboardEvent =
    CStruct8[UInt, UInt, UInt, UByte, UByte, UByte, UByte, Keysym]
  type Keysym   = CStruct4[Scancode, Keycode, UShort, UInt]
  type Scancode = Int
  type Keycode  = Int
}