package eitherPower

sealed trait Error {val message:String}
case class GenericError(override val message:String) extends Error
case class DivisonError(override val message:String) extends Error
