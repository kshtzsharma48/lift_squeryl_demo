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

class Demo1 {
  val items = List("Fix dinner", "Smash pumpkins", "Grind oats", "Slather things")
  
  // Transform the segment of the template using CSS selectors
  def todoList = ".item *" #> items & ClearClearable

  // Or transform the node sequence direction (or simple return a new one to replace it, as this does)
  def currentTime(n : NodeSeq) : NodeSeq = <span class="time">{ new Date().toString }</span>
}