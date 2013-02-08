package com.sas.model

import org.squeryl.Schema
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._

case class person(val id: Int, val name: String) extends KeyedEntity[Int]
case class gadget(val id: Int, val description: String, price: Double) extends KeyedEntity[Int]
case class purchase(val id: Int, val dt_purchase: java.util.Date, val buyer_id: Int, gadget_id: Int, quantity : Int) extends KeyedEntity[Int]

object MySchema extends Schema {
  val people = table[person]
  val gadgets = table[gadget]
  val purchases = table[purchase]
}
