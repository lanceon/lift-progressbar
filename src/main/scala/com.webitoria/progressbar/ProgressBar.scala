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
class ProgressBarMsgBase(val percent: Int)


trait ProgressBar[MessageType <: ProgressBarMsgBase] extends NamedCometActorSnippet with Logger
{

  protected def updateUiScript : JsCmd = Run{"""
      function updateProgressBar(w) {
         $('.bar').css('width', w + '%');
      } """
  }

  protected def progressbarTpl = {
    <div class="progress progress-striped active">
      <div class="bar" style="width: 0%;"></div>
    </div>
  }

  protected def progressbarInfoPane : NodeSeq = NodeSeq.Empty

  protected def addon : NodeSeq = {
    <head_merge>
      { Script(updateUiScript) }
    </head_merge>
    <div>
      { render(NodeSeq.Empty) }
    </div>
  }

  /**
   * Render progress bar and optionally log pane
   */
  def bar(in: NodeSeq) = {
    import Helpers._
    (
      ".progress_bar_container" #> progressbarTpl &
      ".progress_info_container *" #> progressbarInfoPane
    ).apply(in) ++ addon
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
        debug("notifying actor [%s] with msg [%s]".format(actor, msg)) // TODO Logger
        actor ! msg
      })
    }}
  }

}

