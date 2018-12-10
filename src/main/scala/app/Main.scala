package app

import java.io.{File, PrintWriter}

import com.google.gson.GsonBuilder
import com.typesafe.config.ConfigFactory
import crawler.{BashCrawler, BashUrlCreator}
import data.BashPost

import scala.collection.JavaConverters._
import scala.collection.immutable

object Main {

  private val config = ConfigFactory.load()
  private val resultFilePath = config.getString("conf.result.file.path")
  private val bashHost = config.getString("conf.bash.host")
  private val bashPagePath = config.getString("conf.bash.page")
  private val bashPostPath = config.getString("conf.bash.post")
  private val placeholder = config.getString("conf.bash.placeholder")

  def main(args: Array[String]): Unit = {
    val n = args(0).toInt
    val crawler = new BashCrawler(new BashUrlCreator(bashHost, bashPagePath, bashPostPath, placeholder), n)

    val result = crawler.crawl()

    writeResultToFile(result)
  }

  private def writeResultToFile(result: immutable.Seq[BashPost]): Unit = {
    val fileWriter = new PrintWriter(new File(resultFilePath))
    val gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    fileWriter.write(gson.toJson(result.asJava))
    fileWriter.close()
  }
}
