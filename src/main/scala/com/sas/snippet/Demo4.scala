package com.sas.snippet

import scala.xml.NodeSeq
import scala.xml.NodeSeq.seqToNodeSeq
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds.Replace
import net.liftweb.util.Helpers.strToCssBindPromoter

class Demo4 {
  def search = {
    var query = ""

    def currentResults: NodeSeq = <div id="results">
                                    { Database.items.filter(_.label.contains(query)).map(i => <p>{ i.label }</p>) }
                                  </div>
    def updateResults(q: String): JsCmd = {
      query = q
      Replace("results", currentResults)
    }

    ".query_field" #> SHtml.ajaxText(query, updateResults _) &
      "#results" #> currentResults
  }
}