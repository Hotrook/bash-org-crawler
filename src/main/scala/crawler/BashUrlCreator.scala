package crawler

class BashUrlCreator(host: String, bashPagePath: String, bashPostPath: String, placeholder: String) {
  def getPageUrl(page: Int): String = host + bashPagePath.replace(placeholder, page.toString)

  def getPostUrl(postId: Int): String = host + bashPostPath.replace(placeholder, postId.toString)
}
