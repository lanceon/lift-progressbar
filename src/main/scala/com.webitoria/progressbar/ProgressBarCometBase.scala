package com.webitoria
package progressbar

import net.liftweb.http.NamedCometActorTrait
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.common.Logger

trait ProgressBarCometBase[MessageType <: ProgressBarMsgBase] extends NamedCometActorTrait {

  def render = JsCmds.Noop

  override def lowPriority = {

    case msg: MessageType => {
      //info("Comet msg received in ProgressBarCometBase [%s], update cmd: [%s]".format(msg, msg.jsUpdate))
      partialUpdate(msg.jsUpdate)
    }

    case m => /*warn("Unknown message type: [%s]".format(m))*/
  }

}




