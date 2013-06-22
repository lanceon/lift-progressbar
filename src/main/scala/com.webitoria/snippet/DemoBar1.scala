package com.webitoria
package snippet

import net.liftweb.http.js.JsCmds.{JsCrVar, Run}
import progressbar.{ProgressBarWorkerBase, ProgressBar, ProgressBarMsgBase}
import net.liftweb.http.js.JE.{JsRaw, Num, Str}

case class DemoBarMessage1(override val percent: Int, message: String) extends ProgressBarMsgBase(percent) {

  def jsUpdate = {
    JsCrVar("text", Str(message)) &
    JsCrVar("percent", Num(percent)) &
    JsRaw("$('#demo_log').append(text + '<br>')").cmd &
    JsRaw("$('#demo_log').scrollTop($('#demo_log')[0].scrollHeight)").cmd &
    JsRaw("$('#demo_progressbar').css('width', percent + '%')").cmd
  }

}



object DemoBar1 extends ProgressBar[DemoBarMessage1] {

  override def name = "global"
  override def cometClass = "DemoComet1"

  override protected def progressbarTpl = {
      <div class="progress progress-success progress-striped">
        <div id="demo_progressbar" class="bar"></div>
      </div>
  }

  override protected def progressbarInfoPane = {
    <div id="demo_log" style="margin-top: 20px; height: 100px; padding: 5px; background-color: #eeeeee; overflow: auto;">
      Log will be appended below
      <br/>
    </div>
  }

}


object DemoWorker1 extends ProgressBarWorkerBase[DemoBarMessage1] {

  def progressBar = DemoBar1

  override def run {
    (0 to 100 by 5).foreach(i => {
      progressBar.updateProgress( DemoBarMessage1(i, "Completed: %s%%".format(i)) )
      Thread.sleep(1000)
    })
  }
}

