package eitherPower

/**
  * if the list sum is bigger than 100 divide sum by the head, else multiply by the head,
  * it can fail if list is empty or if the head is 0
  */
class EitherPwr {
  def head(list: List[Int]): Either[ListError, Int] = list.headOption.toRight(ListError("list is empty"))

  def division(dividend: Int, divisor: Int): Either[DivisonError, Int] = {
    if (divisor == 0) Left(DivisonError("you are trying to divide by 0"))
    else Right(dividend / divisor)
  }

  def program(list: List[Int]): Either[Error, Int] = {
    def op(firstInt: Int, secondInt: Int): Either[DivisonError, Int] = if (firstInt > 100) {
      division(firstInt, secondInt)
    } else {
      Right(firstInt * secondInt)
    }

    for {
      listHead <- head(list).right
      sum = list.sum
      res <- op(sum, listHead)
    } yield res
  }
}
