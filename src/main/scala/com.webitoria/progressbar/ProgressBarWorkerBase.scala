package com.webitoria
package progressbar

import net.liftweb.actor.LiftActor
import net.liftweb.common.{Empty, Box}


trait ProgressBarWorkerBase[MessageType <: ProgressBarMsgBase] extends LiftActor {

  /**
   * Reference to progress bar implementation
   */
  def progressBar : ProgressBar[MessageType]

  /**
   * Incapsulates worker activity logic
   */
  def run : Unit


  def start = this ! StartWorker

  private case object StartWorker

  protected def messageHandler = {
    case StartWorker => run
    case _ =>
  }

}
