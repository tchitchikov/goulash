package statisticky

class Basic(data: Array[Double]) {

  def mean(): Double = {
    /* mean reads from the data array passed into Basic and returns the total
     *   of the array divided by the number of elements in the array
     *   Args: None
     *   Returns:
     *      μ (Double) =  (Σ x : x ∈ data) / len(data)
    */
    data.reduceLeft((x,y) => x + y) / data.length
  }

  def standardDeviation(): Double = {
    /* standardDeviation takes the sample standard deviation from data
     *   Args: None
     *   Returns:
     *      σ (Double) = sqrt((Σ(x - mean)^2) / len(data) - 1)
    */
    scala.math.sqrt(data.foldLeft (0.0) { (total, x) =>
        scala.math.pow(x - mean(), 2) + total
      }   /   (data.length - 1.0)
    )
  }
}
