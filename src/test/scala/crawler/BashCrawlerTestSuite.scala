package crawler

import java.nio.charset.StandardCharsets

import com.typesafe.config.{Config, ConfigFactory}
import net.jadler.Jadler
import net.jadler.stubbing.server.jdk.JdkStubHttpServer
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class BashCrawlerTestSuite extends FunSuite with BeforeAndAfterAll {

  private val config: Config = ConfigFactory.load("test")
  private val latestWebsiteWith5Posts = readFromFile(config.getString("conf.website.latest"))
  private val bashPostWebsite = readFromFile(config.getString("conf.website.post"))
  private val actualPostNumberOnWebsite = 5
  private val host = "http://localhost:[PORT]"
  private val bashPagePath = "/page=[NUMBER]"
  private val bashPostPath = "/[NUMBER]"
  private val placeholder = "[NUMBER]"
  private val postId = 4862636

  override protected def beforeAll(): Unit = {
    initJadlerBehaviour()
  }

  private def initJadlerBehaviour() = {
    Jadler.initJadlerUsing(new JdkStubHttpServer())

    val validPath = createUrlToPage(1)
    val invalidPath = createUrlToPage(2)

    Jadler.onRequest()
      .havingPathEqualTo(validPath)
      .respond()
      .withStatus(202)
      .withBody(latestWebsiteWith5Posts)

    Jadler.onRequest()
      .havingPathEqualTo(invalidPath)
      .respond().withStatus(404)

    Jadler.onRequest()
      .havingPathEqualTo(bashPostPath.replace(placeholder, postId.toString))
      .respond()
      .withStatus(202)
      .withBody(bashPostWebsite)

  }

  private def createUrlToPage(page: Int) = {
    bashPagePath
      .replace(placeholder, page.toString)
  }

  test("should parse given number of posts") {
    val bashUrlCreator = new BashUrlCreator(bashHost, bashPagePath, bashPostPath, placeholder)
    val n = 5
    val crawler = new BashCrawler(bashUrlCreator, n)

    val result = crawler.crawl()

    assert(result.size == n)
    assert(result.head.id == postId)
    assert(result(4).id == postId)
  }

  test("should parse all available posts when n is bigger then number of posts on website") {
    val bashUrlCreator = new BashUrlCreator(bashHost, bashPagePath, bashPostPath, placeholder)
    val n = 25
    val crawler = new BashCrawler(bashUrlCreator, n)

    val result = crawler.crawl()

    assert(result.size == actualPostNumberOnWebsite)
    assert(result.head.id == postId)
    assert(result(4).id == postId)
  }

  override protected def afterAll(): Unit = {
    Jadler.closeJadler()
  }

  private def bashHost: String = host.replace("[PORT]", Jadler.port.toString)

  private def readFromFile(fileName: String) = {
    io.Source.fromFile(fileName, StandardCharsets.UTF_8.name()).mkString
  }
}
