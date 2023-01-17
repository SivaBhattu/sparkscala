package com.amex.matching
import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.sql.{Row, SaveMode, SparkSession}
//import com.typesafe.config.Config

object Main extends Serializable {
  def main(args: Array[String]): Unit = {
    println("Hello world!")
    val spark  = SparkSession
      .builder().appName("mwh step14a").enableHiveSupport()
      .getOrCreate()
    import spark.sql
    import spark.implicits._

    val database = "mwh_opt_202243"
    val table    = "matching_warehouse_temp_a"

    spark.sql("drop table if exists" + database + "." + table)
    val data = spark.sql(
      "select recid,pdate,listcd,vendorname,seqnum,matchtype,business," +
      "businessid,person,personid,address,phone,email,siccode,stdstat,hashraw" +
      "from matching_warehouse_temp_master group by recid,pdate,listcd,vendorname,"+
      "seqnum,matchtype,business,businessid,person,personid,address,phone,email,siccode," +
      "stdstat,hashraw")

    data.write.save("namesAndFavColorsTable")


    spark.close()
  }
}
