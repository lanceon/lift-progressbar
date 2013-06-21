package com.webitoria
package snippet

import net.liftweb.common.Logger
import scala.xml.NodeSeq
import net.liftweb.util.Helpers
import Helpers._
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds

class DemoBarInit extends Logger {

  def renderActivateButton(in: NodeSeq) = {
    (
      "button" #> SHtml.ajaxButton("Start background worker", ()=>{

        info("Calling DemoWorker.start")
        DemoWorker.start

        JsCmds.Noop
      })).apply(in)
 }

}



