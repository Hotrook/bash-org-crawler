package crawler

import data.BashPost
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import parser.BashParser

import scala.collection.immutable

class BashCrawler(bashUrlCreator: BashUrlCreator, n: Int) {

  private val browser = new JsoupBrowser()
  private val parser = new BashParser()
  private val postsCss = "#container #content .post .bar .qid"
  private val pagesCss = "#container #content .pagination .page"

  def crawl(): immutable.Seq[BashPost] = {
    val links = findBashPostsUrls()

    links
      .map(browser.get)
      .map(parser.parse)
  }

  private def findBashPostsUrls(): immutable.Seq[String] = {
    val firstPage = browser.get(bashUrlCreator.getPageUrl(1))
    val maxPageNumber = findMaxPageNumber(firstPage)
    val postNumberPerSite = findPostNumberPerSite(firstPage)
    val pagesToParse = Math.min(Math.ceil(n.toDouble / postNumberPerSite).toInt, maxPageNumber)

    (1 to pagesToParse)
      .map(bashUrlCreator.getPageUrl)
      .map(browser.get)
      .flatMap(retrievePostIdsFromPage)
      .take(n)
      .map(bashUrlCreator.getPostUrl)
  }

  private def retrievePostIdsFromPage(doc: JsoupBrowser.JsoupDocument): List[Int] = {
    doc.body.select(postsCss)
      .toList
      .map(_.innerHtml.substring(1))
      .map(_.toInt)
  }

  private def findPostNumberPerSite(firstPage: JsoupBrowser.JsoupDocument): Int = {
    firstPage.body.select(postsCss).size
  }

  private def findMaxPageNumber(doc: JsoupBrowser.JsoupDocument) = {
    doc.body.select(pagesCss).toList.last.innerHtml.toInt
  }
}
