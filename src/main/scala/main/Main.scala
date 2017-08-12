package main

import statisticky.Basic

object Main extends App {
  var d = Array(1.0, 4.0, 7.0, 11.0)
  var b = new Basic(d)
  println(b.mean())
  println(b.standardDeviation())
  println(b.coefficientVariation())
  println(b.variance())
}
