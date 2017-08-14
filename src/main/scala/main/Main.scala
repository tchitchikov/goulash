package main

import statisticky.Basic
import risk.Model
import risk.Simulation
import data.Data

object Main extends App {
  var tickers = List("CHOC", "SPY")

  var stock_data = collection.mutable.Map[String, Map[String, Double]]()

  val NORMSINV = data.Data.get_raw_data("NORMSINV")


  for ((k,v) <- data.Data.get_data("daily_periodic_return")) {
    var rm = new Model(v)
    var stat = new Basic(v)
    var stockSummaryStats = Map(
      "μ_of_returns" -> stat.mean(),
      "σ_of_returns" -> stat.standardDeviation(),
      "σ²_of_returns" -> stat.variance(),
      "volatility_eroded_historical_mean" -> rm.volatility_eroded_historical_mean()
    )
    stock_data(k) = stockSummaryStats
  }

  for ((b,c) <- data.Data.get_data("close_prices")) {
    stock_data(b) += ("LastClose" -> c.head)
  }

  for ((a,b) <- stock_data) {
    var sim = new Simulation(stock_data(a), NORMSINV)
    stock_data(a) += ("MeanSimulation" -> sim.monteCarloSimulation())
  }

  stock_data.foreach(println)

}
