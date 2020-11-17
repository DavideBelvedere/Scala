package eitherPower

sealed trait Error {val message:String}
case class ListError(override val message:String) extends Error
case class DivisonError(override val message:String) extends Error
