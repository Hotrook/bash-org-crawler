package parser

import data.BashPost
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL._

class BashParser {

  val IdCss = "#container #content div div a"
  val PointsCss = "#container #content div div span"
  val ContentsCss = "#container #content div div:nth-child(2)"

  def parse(doc: JsoupBrowser.JsoupDocument): BashPost = {
    new BashPost(parseId(doc), parsePoints(doc), parseContents(doc))
  }

  private def parseContents(doc: JsoupBrowser.JsoupDocument): String = {
    doc.underlying.outputSettings.prettyPrint(false).outline()
    val result = doc.body.select(ContentsCss).head.innerHtml

    formatText(result)
  }

  private def formatText(result: String) = {
    result.replaceFirst("^\\s+", "") // remove redundant spaces at the beginning of post
      .replaceAll("\n[ ]+(\\S)", "\n$1") // remove redundant spaces at the beginning of every line
      .replaceAll("\n[ ]*\n", "\n") // remove empty lines
      .replaceFirst("\\s+$", "") // remove redundant spaces in the end of post
      .replace("&lt;", "<")
      .replace("&gt;", ">")
  }

  private def parseId(doc: JsoupBrowser.JsoupDocument) = {
    (doc >> text(IdCss)).substring(1).toInt
  }

  private def parsePoints(doc: JsoupBrowser.JsoupDocument): Int = {
    (doc >> text(PointsCss)).toInt
  }
}
