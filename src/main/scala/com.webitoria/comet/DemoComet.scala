package com.webitoria
package comet

import snippet.DemoBarMessage
import progressbar.ProgressBarCometBase
import net.liftweb.http.js.JE.JsRaw

class DemoComet extends ProgressBarCometBase[DemoBarMessage] {

  override def lowPriority = {

    case msg : DemoBarMessage => // TODO: use type!
      //info("Comet msg received [%s]".format(msg))
      partialUpdate( JsRaw("updateProgressBar("+ msg.percent + ", '"+ msg.message +"')" ).cmd ) // TODO: move func to super

    case m => //warn("Unknown msg: " + m)

  }



}
