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
import net.liftweb.http.js.jquery.JqWiringSupport
import net.liftweb.http.js.JE.JsRaw

class WiringDemo extends Loggable {
  val numberOfItemsChecked = ValueCell(Database.items.count(_.complete))
  val cashOnHand = ValueCell(12)
  val moneyEarnedPerItem = 6
  val moneyEarned = numberOfItemsChecked.lift(_ * moneyEarnedPerItem)
  val projectedMoney = moneyEarned.lift(cashOnHand)(_ + _)

  def ToggleClass(sel: String, cls: String): JsCmd = JsRaw("$('%s').toggleClass('%s')".format(sel, cls))

  def itemChanged(item: TodoItem, b: Boolean): JsCmd = {
    val labelID = "#label_%d".format(item.id)
    item.complete = b
    logger.info("Item %s changed".format(item.label))
    numberOfItemsChecked.set(Database.items.count(_.complete))
    ToggleClass(labelID, "complete")
  }

  def itemsXform =
    ".item" #> Database.items.map(item => {
      val labelID = "label_%d".format(item.id)
      ".done" #> SHtml.ajaxCheckbox(item.complete, itemChanged(item, _)) &
        ".label *" #> item.label &
        ".label [id]" #> labelID &
        ".label [class+]" #> (if (item.complete) "complete" else "")
    })

  def todoList = wiredXform & itemsXform & ClearClearable

  def wiredXform: CssSel =
    "#cash_input" #> SHtml.ajaxText(cashOnHand.get.toString, (str) => { cashOnHand.set(str.toInt); Noop }) &
      ".items_checked" #> WiringUI.asText(numberOfItemsChecked, JqWiringSupport.fade) &
      ".cash_on_hand" #> WiringUI.asText(cashOnHand, JqWiringSupport.fade) &
      ".money" #> WiringUI.asText(moneyEarned, JqWiringSupport.fade) &
      ".projected_money" #> WiringUI.asText(projectedMoney, JqWiringSupport.fade)
}