object rendering {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  import scala.xml._
  import net.liftweb.util.Helpers._
  
  val a = ".time" #> "3pm"                        //> a  : net.liftweb.util.CssSel = CssBind(Full(.time), Full(ClassSelector(time,
                                                  //| Empty)))
  
  def b(n : NodeSeq) : NodeSeq = {
    val c = n \\ "span"
    <p>New parent paragraph{ c }</p>
  }                                               //> b: (n: scala.xml.NodeSeq)scala.xml.NodeSeq
  
  val input = <p>This is the input <span class="time">4pm</span></p>
                                                  //> input  : scala.xml.Elem = <p>This is the input <span class="time">4pm</span>
                                                  //| </p>
  
  a(input)                                        //> res0: scala.xml.NodeSeq = NodeSeq(<p>This is the input 3pm</p>)
  
  b(input)                                        //> res1: scala.xml.NodeSeq = <p>New parent paragraph<span class="time">4pm</spa
                                                  //| n></p>
}