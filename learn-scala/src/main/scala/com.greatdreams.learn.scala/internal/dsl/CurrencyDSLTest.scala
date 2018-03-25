package com.greatdreams.learn.scala.internal.dsl

import com.greatdreams.learn.scala.`implicit`.ImplicitClassTest
import com.greatdreams.learn.scala.internal.dsl.Currency.{Conversion, EUR, GBP, USD}
import org.slf4j.{Logger, LoggerFactory}

trait Currency {
  def getCode: String
}
object Currency {
  object USD extends Currency {
    val getCode: String = "USD"
  }
  object EUR extends Currency {
    val getCode: String = "EUR"
  }

  object GBP extends Currency {
    val getCode: String = "GBP"
  }
  def apply(s: String): Currency = s.toUpperCase match {
    case "USD" => USD
    case "EUR" => EUR
    case "GBP" => GBP
  }

  type Conversion = Map[(Currency, Currency), BigDecimal]

  case class Converter(conversion: Conversion) extends {
    def convert(from: Currency, to: Currency): BigDecimal = {
      if (from == to) 1
      else conversion.getOrElse((from, to), 1 / conversion((to, from)))
    }
  }

  case class Money(amount: BigDecimal, currency: Currency, converter: Converter) {
    def +(thatMoney: Money): Money =  performOperation(thatMoney, _ + _)

    def performOperation(thatMoney: Money, operation: (BigDecimal, BigDecimal) => BigDecimal): Money = {
      thatMoney match {
        case Money(v, c, con) if c == currency => Money(operation(amount, v), currency, con)
        case Money(v, c, con) => performOperation(thatMoney.to(currency), operation)
      }
    }

    def to(thatCurrency: Currency): Money = {
      val rate = converter.convert(currency, thatCurrency)
      Money(amount * rate, thatCurrency, converter)
    }
  }
}

object Currency1 {
  object USD1 extends Currency {
    val getCode: String = "USD"
  }
  object EUR1 extends Currency {
    val getCode: String = "EUR"
  }

  object GBP1 extends Currency {
    val getCode: String = "GBP"
  }
  def apply(s: String): Currency = s.toUpperCase match {
    case "USD" => USD1
    case "EUR" => EUR1
    case "GBP" => GBP1
  }

  type Conversion1 = Map[(Currency, Currency), BigDecimal]

  case class Converter1(conversion: Conversion1) extends {
    def convert(from: Currency, to: Currency): BigDecimal = {
      if (from == to) 1
      else conversion.getOrElse((from, to), 1 / conversion((to, from)))
    }
  }

  case class Money(amount: BigDecimal, currency: Currency)(implicit converter: Converter1) {
    def +(thatMoney: Money): Money =  performOperation(thatMoney, _ + _)

    def performOperation(thatMoney: Money, operation: (BigDecimal, BigDecimal) => BigDecimal): Money = {
      thatMoney match {
        case Money(v, c) if c == currency => Money(operation(amount, v), currency)
        case Money(v, c) => performOperation(thatMoney.to(currency), operation)
      }
    }

    def to(thatCurrency: Currency): Money = {
      val rate = converter.convert(currency, thatCurrency)
      Money(amount * rate, thatCurrency)
    }
  }

  implicit class BigDecimalOps(value: BigDecimal) {
    def apply(currency: Currency)(implicit converter: Converter1): Money = Money(value, currency)
  }

  implicit class IntOps(value: Int) {
    def apply(currency: Currency)(implicit converter: Converter1): Money = (value: BigDecimal).apply(currency)
  }

  implicit class DoubleOps(value: Double) {
    def apply(currency: Currency)(implicit converter: Converter1): Money = (value: BigDecimal).apply(currency)
  }

}

object CurrencyDSLTest {
  def main(args: Array[String]): Unit = {
    val logger: Logger = LoggerFactory.getLogger(ImplicitClassTest.getClass)

    import Currency._

    val conversion: Conversion = Map(
      (GBP, EUR) -> 1.39,
      (EUR, USD) -> 1.08,
      (GBP, USD) -> 1.5
    )

    val converter = Converter(conversion)

    // result is 79.8 USD
    val result = Money(42, USD, converter) + Money(35, EUR, converter)

    println(result)

    // resultToPound is 53.2 GBP
    val resultToPound = result.to(GBP)
    println(resultToPound)

    dslTest()
  }

  def dslTest(): Unit = {
    {
      import Currency1._

      val conversion: Conversion = Map(
        (GBP1, EUR1) -> 1.39,
        (EUR1, USD1) -> 1.08,
        (GBP1, USD1) -> 1.50
      )

      implicit val converter = Converter1(conversion)

      val result = 42(USD1) + 35(EUR1)
      val resultToPound = result to GBP1
      println(result)
      println(resultToPound)
    }
  }
}
