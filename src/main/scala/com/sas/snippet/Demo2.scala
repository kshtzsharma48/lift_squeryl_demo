package com.sas.snippet

import net.liftweb._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.http.js._
import net.liftweb.http.js.JsCmds._
import net.liftweb.http._
import net.liftweb.util._
import net.liftweb.util.Helpers._
import scala.xml._
import java.util.Date
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.http.js.JE.JsVar

case class TodoItem(val id: Int, val label: String, var complete: Boolean)

class Demo2 extends Loggable {
  val items = List(
    TodoItem(1, "Fix dinner", false),
    TodoItem(2, "Smash pumpkins", false),
    TodoItem(3, "Grind oats", false),
    TodoItem(4, "Slather things", false))

  def ToggleClass(sel: String, cls: String): JsCmd = JsRaw("$('%s').toggleClass('%s')".format(sel, cls))

  def itemChanged(item: TodoItem, b: Boolean): JsCmd = {
    val labelID = "label_%d".format(item.id)
    item.complete = b
    logger.info("Item %s changed".format(item.label))
    ToggleClass("#" + labelID, "complete")
  }
  
  def clientFunction =  Script(Function("fname", List("arg1", "arg2"),
      JsRaw("var args = arg1 + ':' + arg2;") &
      // ajaxCall returns a (funcid, jscript) tuple...we only need the jscript part
        SHtml.ajaxCall(JsVar("args"), (s: String) => logger.info("Got client callback with " + s))._2))

  // Transform the segment of the template using CSS selectors
  def todoList =
    ".item *" #> items.map(item => {
      val labelID = "label_%d".format(item.id)
      ".done" #> SHtml.ajaxCheckbox(item.complete, itemChanged(item, _)) &
        ".label *" #> item.label &
        ".label [id]" #> labelID
    }) &
      ClearClearable

  // Or transform the node sequence direction (or simple return a new one to replace it, as this does)
  def currentTime(n: NodeSeq): NodeSeq = <span class="time">{ new Date().toString }</span>
}