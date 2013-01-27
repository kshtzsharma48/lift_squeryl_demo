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
import java.text.SimpleDateFormat
import java.text.DateFormat

case class Tick()

class Clock extends CometActor with Loggable {
  ActorPing.schedule(this, Tick, 1 second)
  override def lowPriority = {
    case Tick =>
      reRender(false)
      ActorPing.schedule(this, Tick, 1 second)
  }

  def currentTime = DateFormat.getTimeInstance().format(new Date());

  // Transform the segment of the template using CSS selectors
  def render = ".time" #> currentTime
}