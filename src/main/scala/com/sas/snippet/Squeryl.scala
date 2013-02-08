package com.sas.snippet

import com.sas.model.MySchema._
import org.squeryl.PrimitiveTypeMode._
import net.liftweb.util.Helpers._

object Squeryl {
  def orderQuery = {
    from(people, gadgets, purchases)((p, g, o) =>
      where(p.id === o.buyer_id and o.gadget_id === g.id)
        groupBy (p.name, g.description)
        compute (sum(g.price) * sum(o.quantity)))
  }

  def oqJoin = {
    join(people, purchases.leftOuter, gadgets.leftOuter)((p, o, g) =>
      groupBy(p.name, g.map(_.description))
        compute (sum(g.map(_.price)) * sum(o.map(_.quantity)))
        on (p.id === o.map(_.buyer_id), o.map(_.gadget_id) === g.map(_.id)))
  }

  def render = ".row" #> orderQuery.map(o =>
    ".name *" #> o.key._1 &
      ".description *" #> o.key._2 &
      ".total *" #> "%.2f".format(o.measures.get))

  def showJoin = ".row" #> oqJoin.map(o =>
    ".name *" #> o.key._1 &
      ".description *" #> o.key._2 &
      ".total *" #> o.measures.map(amt => "%.2f".format(amt)))
}