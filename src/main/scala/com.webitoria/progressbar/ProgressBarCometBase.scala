package com.webitoria
package progressbar

import net.liftweb.http.NamedCometActorTrait
import net.liftweb.http.js.JsCmds
import net.liftweb.http.js.JE.JsRaw

trait ProgressBarCometBase[MessageType <: ProgressBarMsgBase] extends NamedCometActorTrait {

  def render = JsCmds.Noop

}




