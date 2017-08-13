package data

import com.mongodb.casbah.Imports._
import scala.collection.mutable.ListBuffer

class Data (tickers: List[String]){
  //  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]
  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient("financial_data")
  val coll = db("data")
  var coll_names = coll.find()

  def get_closing_prices(): collection.mutable.Map[String, List[Double]] = {
    var m = collection.mutable.Map[String, List[Double]]()
    coll_names.foreach{ d =>
      var ticker = d("ticker").toString()
      var close_price_list = d.as[BasicDBList]("close_prices").toList
      m(ticker) = close_price_list.map(_.toString.toDouble)
    }
    m
  }
}
