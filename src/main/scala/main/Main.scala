package main

import statisticky.Basic
import risk.Model
import data.Data

object Main extends App {
  var tickers = List("CHOC", "SPY")
  var get = new Data(tickers)

  var stock_data = collection.mutable.Map[String, Any]()

  for ((k,v) <- get.get_data("daily_periodic_return")) {
    var rm = new Model(v)
    var stat = new Basic(v)
    var stockSummaryStats = Map(
      "σ_of_returns" -> stat.standardDeviation(),
      "σ²_of_returns" -> stat.variance(),
      "volatility_eroded_historical_mean" -> rm.volatility_eroded_historical_mean()
    )

    stock_data(k) = stockSummaryStats
  }

  println(stock_data)



//  var stock1 = Array(1.0, 4.0, 7.0, 11.0)
//  var md = Map("Prices" -> Array(1.0, 4.0, 7.0, 11.0))
//  var rm = new Model(md)
//  var stock1stats = new Basic(stock1)
//  var stock1SummaryStats = Map(
//    "μ" -> stock1stats.mean(),
//    "σ" -> stock1stats.standardDeviation(),
//    "cv" -> stock1stats.coefficientVariation(),
//    "variance" -> stock1stats.variance()
//  )
//
//
//  println(get.get_closing_prices())
}
