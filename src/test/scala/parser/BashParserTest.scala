package parser

import java.nio.charset.StandardCharsets

import com.typesafe.config.ConfigFactory
import data.BashPost
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import org.scalatest._


class BashParserTest extends FunSuite {

  private val Content = "Backstory: Zapomniałem odpowiedzi na pytania zabezpieczające potrzebne do zarządzania kontem Apple\n<br>\n<br>\n<kazi> jaka może być moja wymarzona praca?\n<br>\n<ziomek> wpisz że zawsze chciałeś być operatorem maszynki do mięsa"
  private val testWebsiteFile = ConfigFactory.load("test").getString("conf.website.post")

  test("should correctly retrieve data from example post") {
    val parser = new BashParser()
    val doc = new JsoupBrowser().parseFile(testWebsiteFile, StandardCharsets.UTF_8.name())

    val data: BashPost = parser.parse(doc)

    assert(data.id == 4862636)
    assert(data.points == -180)
    assert(data.content == Content)
  }

}
