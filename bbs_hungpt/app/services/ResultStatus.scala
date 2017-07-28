package services

object ResultStatus extends Enumeration {
  val Succeed = Value(1)
  val Failed = Value(0)
  val Excepted = Value(-1)
}
