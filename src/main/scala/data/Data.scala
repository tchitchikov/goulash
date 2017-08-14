package data

import com.mongodb.casbah.Imports._
import scala.collection.mutable.ListBuffer

object Data {
  //  def manOf[T: Manifest](t: T): Manifest[T] = manifest[T]
  val mongoClient = MongoClient("localhost", 27017)
  val db = mongoClient("financial_data")
  val coll = db("data")
  var raw_data = db("raw_data")

  def get_data(dataSetDimension: String): collection.mutable.Map[String, List[Double]] = {
    var m = collection.mutable.Map[String, List[Double]]()
    coll.find().foreach{ d =>
      m(d("ticker").toString()) = d.as[BasicDBList](dataSetDimension).toList.map(_.toString.toDouble)
    }
    m
  }

  def get_raw_data(dataDim: String): List[Double] = {
    var m = collection.mutable.Map[String, List[Double]]()
    val rd = raw_data.findOne( MongoDBObject("type" -> dataDim) )
    rd.foreach{ obj =>
      m(obj("type").toString()) = obj.as[BasicDBList]("data").toList.map(_.toString.toDouble)
    }
    m(dataDim)
  }
}
