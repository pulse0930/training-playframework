package controllers

import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import javax.inject._
import scala.concurrent.Future

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())

  }

  def ping() = Action { implicit request: Request[AnyContent] =>
    System.out.print("Enter the second number: ")
    Ok("This Works")
  }

  def anotherOne = Action{ _=>
    Ok(Json.obj("yes" -> true))
  }

  def nameParam(name: String) = Action{ implicit request: Request[AnyContent] =>
    Ok(Json.obj("name" -> name))
  }

  def posted = Action.async(parse.json) { implicit request =>
    request.body.asOpt[JsValue].map { json =>
      Future.successful(Ok(Json.obj("received_input" -> json)))
    }.getOrElse( Future.successful(BadRequest(Json.obj("err" -> "Json not found"))))
  }
}
