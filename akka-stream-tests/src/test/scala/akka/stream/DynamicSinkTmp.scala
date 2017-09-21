package akka.stream

import akka.Done
import akka.actor.ActorSystem
import akka.stream.scaladsl._

object DynamicSinkTmp extends App {
  implicit val system = ActorSystem("tmp")
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  private def makeSink[T](name: T) = {
    Sink.foreach[T](v ⇒ println(s"name:$name, value:$v"))
  }

  try {
    Source(1 to 10).to(Sink.dynamic(makeSink, () ⇒ Done)).run()
  } finally {
    Thread.sleep(500)
    system.terminate()
  }
}

