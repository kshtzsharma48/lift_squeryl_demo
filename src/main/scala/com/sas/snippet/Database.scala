package com.sas.snippet

case class TodoItem(val id: Int, val label: String, var complete: Boolean)

object Database {
  val items = List(
    TodoItem(1, "Fix dinner", false),
    TodoItem(2, "Smash pumpkins", false),
    TodoItem(3, "Grind oats", false),
    TodoItem(4, "Slather things", false))
}