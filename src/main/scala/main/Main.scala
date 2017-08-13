package main

import statisticky.Basic
import risk.Model
import data.Data

object Main extends App {

  var get = new Data(List("CHOC", "SPY"))
  var stock1 = Array(1.0, 4.0, 7.0, 11.0)
  var md = Map("Prices" -> Array(1.0, 4.0, 7.0, 11.0))
  var rm = new Model(md)
  var stock1stats = new Basic(stock1)
  var stock1SummaryStats = Map(
    "μ" -> stock1stats.mean(),
    "σ" -> stock1stats.standardDeviation(),
    "cv" -> stock1stats.coefficientVariation(),
    "variance" -> stock1stats.variance()
  )


  println(get.get_closing_prices())
}
