package com.webitoria
package progressbar

import xml.NodeSeq
import net.liftweb.http.{NamedCometActorSnippet, NamedCometListener}
import net.liftweb.common.{Logger, Full}
import net.liftweb.util.Helpers
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds.{Run, Script}


/**
 * Message from worker to comet actor
 */
abstract class ProgressBarMsgBase(val percent: Int) {
  /*
     Example:
        JsCrVar("width", Num(percent)) &
        JsCrVar("text", Str(text)) &
        JsRaw("$('.progressbar_log').val( $('.progressbar_log').val() + " + "\n" +  " + text);").cmd &
        JsRaw("$('.progressbar_log').scrollTop($('.progressbar_log')[0].scrollHeight);").cmd
  */
  def jsUpdate: JsCmd
}


trait ProgressBar[MessageType <: ProgressBarMsgBase] extends NamedCometActorSnippet with Logger {

  protected def progressbarTpl = {
    <div class="progress progress-striped active">
      <div class="bar" style="width: 0%;"></div>
    </div>
  }

  protected def progressbarInfoPane : NodeSeq = NodeSeq.Empty

  /**
   * Render progress bar and optionally log pane
   */
  def bar(in: NodeSeq) = {
    import Helpers._
    (
      ".progress_bar_container" #> progressbarTpl &
      ".progress_info_container *" #> progressbarInfoPane
    ).apply(in) ++ render(NodeSeq.Empty)
  }


  /**
   * Update progressbar on client-side
   */
  def updateProgress(msg: MessageType) {
    notifyAllActors(msg)
  }

  /**
   * Send messages to each active actor
   */
  protected def notifyAllActors(msg: MessageType) {
    NamedCometListener.getDispatchersFor(Full(name)).foreach{actorBox => {
      actorBox.map(actor => {
        info("notifying actor [%s] with msg [%s]".format(actor, msg))
        actor ! msg
      })
    }}
  }

}

