package io.johnmurray.watson

abstract class Service[A, B](method: Http.Method, queryString: QueryString) {

  /*
   * Since parameters are contra-variant, I'm not sure this can even exist in the
   * abstract, since it can only get _more_ generic as we sub-class it. I think that
   * means we'll need to get rid of it here.
   */
  // def handle(f: RequestContext[A] => B): Handler[A, B] = ???

}

object Service {

  /*
   * TODO: Generate a custom [[RequestContext]] that contains all of the required
   *       information. Also generate a custom [[Service]] that has a [[Service.handle]]
   *       method that uses the custom [[RequestContext]]. 
   *       
   *       Also... we need to generate a custom [[Handler]] object since it will be responsible
   *       for converting an actual incoming [[Http.Request]] object into a [[RequestContext]]
   *       and returning a [[Http.Response]]. 
   */
  def apply[A, B](method: Http.Method, queryString: QueryString): Service[A, B] = {
    Service(method, queryString)
  }

}

trait Handler[A, B] {
  // TODO: Make this specific to Play
  def apply(r: Http.Request): Http.Response
}
trait RequestContext[A] {
  def body: A
}



final case object QueryString(values: Seq[QueryStringValue])

abstract class QueryStringValue(val name: String, val type: Type) {
  def required: Boolean
}
final case class OptionalQueryStringValue(name: String, type: Type) extends QueryStringValue(name, type) {
  override def required = false
}
final case class RequiredQueryStringValue(name: String, type: Type) extends QueryStringValue(name, type) {
  override def required = true
}



object Http {

  /*
   * Supported HTTP Method(s)
   */
  trait Method
  case object Get extends Method
  case object Post extends Method
  case object Put extends Method
  case object Delete extends Method

  /* TODO: remove once fixed in [[Handler]] */
  trait Request
  trait Response

}


trait Type
object Types {
  case object Int extends Type
  case object Float extends Type
  case object String extends Type
  case object Boolean extends Type
}
