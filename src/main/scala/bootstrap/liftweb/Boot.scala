package bootstrap.liftweb

import net.liftweb._
import org.squeryl.SessionFactory
import org.squeryl.Session
import util._
import Helpers._
import common._
import http._
import sitemap._
import Loc._
import net.liftmodules.JQueryModule
import net.liftweb.http.js.jquery._
import org.squeryl.adapters.PostgreSqlAdapter
import net.liftweb.util.LoanWrapper
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import com.mchange.v2.c3p0.ComboPooledDataSource
import scala.xml.Text
import net.liftmodules.widgets.tablesorter.TableSorter

/**
 * This class is loaded by Lift, and the boot method is called to configure the app. It is called once and only once.
 */
class Boot extends Loggable {
  def boot {

    // where to search for snippets, comet, etc (lift:xxx classes in HTML)
    LiftRules.addToPackages("com.sas")

    val entries = List(
      Menu.i("Home") / "index",
      Menu.i("Demo 1 (Templates)") / "demo1",
      Menu.i("Demo 2 (AJAX)") / "demo2",
      Menu.i("Demo 3 (Comet)") / "demo3",
      Menu.i("Other") / "other",
      Menu.i("Static") / "static" / ** >> Hidden)

    // set the sitemap.  Note if you don't want access control or a generated menu for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries: _*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    //Init the jQuery module, see http://liftweb.net/jquery for more information.
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery172
    JQueryModule.init
  }
}
