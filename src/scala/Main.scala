
import java.sql.Timestamp

import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{Days, LocalDate, LocalDateTime, YearMonth}

import scala.collection.immutable
import scala.util.Try

object Main {
  def main(args: Array[String]): Unit = {
    val ciao = null.asInstanceOf[String]
    val bho = "ok"
    val tru= Try(Integer.valueOf("vis")).getOrElse(4)
    val la= None.exists(_ == "ok")

    val prova = new Prova()
    prova.setCiao("hello")


    val ld= List("ok" ->List("ok2"->5).toMap, "lc"->List("lc3"->8).toMap).toMap
    println(ld.get("ok").map(_.get("csd")))
    println(ld.get("oki").map(_.get("csd")))
    println(ld.get("oki").flatMap(_.get("csd")))
    println(ld.get("ok").flatMap(_.get("csd")))
    Option(prova).foreach(_.setCiao("vbava"))
    println(s"$tru $la, ${prova.getCiao}")

    val yyyyMMddHHmmss: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    val macActivationLocal = new LocalDateTime()
    val otherTs =LocalDateTime.parse("2020-11-02 01:00:00",yyyyMMddHHmmss)

    val tYm = new YearMonth(otherTs)
    val _ :: td :: tHMS :: _ = new LocalDateTime(otherTs).toString("yyyyMM-dd-HHmmss").split("-").toList
    val mYM = new YearMonth(macActivationLocal.getYear,macActivationLocal.getMonthOfYear)

    println(mYM == tYm || (mYM == tYm.minusMonths(1) && td == "01" && tHMS == "000000"))

    val ciaone = List(("c",1),("b",2),("c",3),("a",4)).toMap

    println(ciaone    )

    val ym = YearMonth.parse("202012",DateTimeFormat.forPattern("yyyyMM")).toInterval
    def getDateInterval(startP: Timestamp, endP: Timestamp): immutable.IndexedSeq[LocalDate] = {

      val startDate = new LocalDate(startP.getTime)
      val endDate = new LocalDate(endP.getTime)

      val daysCount = Days.daysBetween(startDate, endDate).getDays
      (0 until daysCount + 1).map(startDate.plusDays) //.foreach(println)
    }
    println(ym.getStart)
    println(ym.getEnd)
    //getDateInterval(new Timestamp(ym.getStartMillis),new Timestamp(ym.getEndMillis)).foreach(println)
  }
}
