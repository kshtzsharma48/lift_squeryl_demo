package com.sas.comet

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
import net.liftweb.actor.LiftActor
import com.sas.snippet.TodoItem
import com.sas.snippet.Database

// The hub where events arrive, and are sent out to the comet actors
object Demo3MessageHub extends LiftActor with ListenerManager {
  var itemThatChanged = TodoItem(0, "", false) // a place to store the incoming message for dist.

  // This method is called in order to retrive the message that should be sent to listeners
  def createUpdate = itemThatChanged

  // This method receives a message, then returns a partial function that can accept messages and handle them
  // update listeners causes the createUpdate message to be sent to all regiestered listeners
  override def lowPriority = {
    case i: TodoItem => itemThatChanged = i; updateListeners()
  }
}

class Demo3 extends CometActor with Loggable with CometListener {
  // On creation, CometListener calls this to register itself with a ListenerManager
  override def registerWith = Demo3MessageHub

  // Note the change from our last version...instead of doing the update locally, we let the push do the work
  def itemChanged(item: TodoItem, b: Boolean): JsCmd = {
    item.complete = b
    // Send the changed item to the listener manager for dist...we'll be included in that update
    Demo3MessageHub ! item
    Noop // No client side code to run...comet will do the update
  }

  def ToggleClass(sel: String, cls: String): JsCmd = JsRaw("$('%s').toggleClass('%s')".format(sel, cls))

  // Receive the message that a todo item has changed
  override def lowPriority = {
    case i: TodoItem => {
      reRender(false) // Complete list redraw 
      // or do a partial update everywhere...this would not be enough
      // partialUpdate(ToggleClass(labelID, "complete"))
    }
  }

  // Transform the segment of the template using CSS selectors
  def render =
    ".item *" #> Database.items.map(item => {
      val labelID = "label_%d".format(item.id)
      ".done" #> SHtml.ajaxCheckbox(item.complete, itemChanged(item, _)) &
        ".label *" #> item.label &
        ".label [class+]" #> (if (item.complete) "complete" else "") &
        ".label [id]" #> labelID
    }) &
      ClearClearable
}