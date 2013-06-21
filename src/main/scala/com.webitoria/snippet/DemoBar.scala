package com.webitoria
package snippet

import net.liftweb.http.js.JsCmds.Run
import progressbar.{ProgressBarWorkerBase, ProgressBar, ProgressBarMsgBase}

case class DemoBarMessage(override val percent: Int, message: String) extends ProgressBarMsgBase(percent)


object DemoBar extends ProgressBar[DemoBarMessage] {

  override def name = "global"
  override def cometClass = "DemoComet"

  override protected def progressbarInfoPane = {
    <div class="progressbar_log">
      Worker not started
    </div>
  }

  override protected def updateUiScript = {
      Run("""
        function updateProgressBar(w, text) {
          $('.bar').css('width', w + '%');
          $('.progressbar_log').html(text);
         }
      """)
  }
}


object DemoWorker extends ProgressBarWorkerBase[DemoBarMessage] {
  def progressBar = DemoBar
  override def run {
    (0 to 100 by 5).foreach(i => {
      progressBar.updateProgress( DemoBarMessage(i, "Completed: %s%%".format(i)) )
      Thread.sleep(2000)
    })
  }
}

