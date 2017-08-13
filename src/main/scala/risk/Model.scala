package risk

import statisticky.Basic
import scala.collection.mutable.ListBuffer

class Model(data: List[Double]) {
  var b = new Basic(data)

  def expected_return_historical(): Double = {
    b.mean()
  }

  def capitalAssetPricing(): Double = {
    1
  }


  /*
   * One way to calculate the drift in a monte carlo simulation
   * Other way is risk free rate - (variance / 2)
   * or 0 for the random lock theory
   * this is NOT the price it will change each day
   * we use this value to help calculate the random drift
   * central limit theorem tells us that we graph
   *  enough periodic daily returns of an asset, the graph will approach a normal distribution
   *  todays_price = yesterdays_price * (e ^ periodic daily return)
   *  future prices will be normally distributed MOST of the time.
   *
   *  we use the volatility eroded historical mean as the mean and then use
   *  the historical standard deviation as our standard deviation
   */
  def volatility_eroded_historical_mean(): Double = {
    b.mean() - (b.variance() / 2)
  }

}

class Simulation(data: Map[String, Double]) {
  def monteCarloSimulation(): Double = {
    var outcomes = new ListBuffer[Double]()
    for (i <- 1 until 100000) {
      var exponent = (data("μ_of_returns") - (data("σ²_of_returns") / 2)) + (data("σ_of_returns") * scala.util.Random.nextDouble())
      var result = data("LastClose") * math.pow(math.E, exponent)
      outcomes += result
    }
    var b = new Basic(outcomes.toList)
    b.mean()
  }
}