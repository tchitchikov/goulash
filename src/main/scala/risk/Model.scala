package risk

import statisticky.Basic

class Model(data: List[Double]) {
  var b = new Basic(data)

  def expected_return_historical(): Double = {
    b.mean()
  }
  def capitalAssetPricing(): Double = {
    1
  }
}
