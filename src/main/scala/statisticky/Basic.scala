package statisticky

class Basic(data: Array[Double]) {

  /* coefficientVariation is the ratio of the standard deviation to the mean
     *   Args: None
     *   Returns:
     *      CV (Double) =  (σ / μ) * 100.0
    */
  def coefficientVariation(): Double = {
    (standardDeviation() / mean()) * 100.0
  }

  /* mean reads from the data array passed into Basic and returns the total
    *   of the array divided by the number of elements in the array
    *   Args: None
    *   Returns:
    *      μ (Double) =  (Σ x : x ∈ data) / len(data)
   */
  def mean(): Double = {
    data.reduceLeft((x,y) => x + y) / data.length
  }

  /* standardDeviation takes the sample standard deviation from data
   *   Args: None
   *   Returns:
   *      σ (Double) = sqrt((Σ(x - mean)^2) / len(data) - 1)
  */
  def standardDeviation(): Double = {
    scala.math.sqrt(data.foldLeft (0.0) { (total, x) =>
        scala.math.pow(x - mean(), 2) + total
      }   /   (data.length - 1.0)
    )
  }
}
