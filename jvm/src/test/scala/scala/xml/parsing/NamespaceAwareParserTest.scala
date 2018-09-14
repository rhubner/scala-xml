package scala.xml.parsing

import javax.xml.parsers.SAXParserFactory
import org.junit.Test
import org.junit.Assert._

import scala.xml.XML

class NamespaceAwareParserTest {

  val saxParserFactory = SAXParserFactory.newInstance()
  saxParserFactory.setNamespaceAware(true)


  val testXMl =
    """
      |<rootElement xmlns="uri:namespace1" xmlns:ble="uri:ble">
      |  <a xmlns="uri:namespace2">test</a>
      |  <b xmlns="uri:namespace2">
      |    <c>hello</c>
      |  </b>
      |  <ble:d>hello</ble:d>
      |</rootElement>
      |
    """.stripMargin

  @Test
  def testNamespace(): Unit = {
    val doc = XML.withSAXParser(saxParserFactory.newSAXParser()).loadString(testXMl)

    val c = doc \\ "c"
    val d = doc \\ "d"

    assertEquals("uri:ble", d.head.scope.uri)
    assertEquals("ble", d.head.prefix)
    assertEquals("uri:namespace2", c.head.scope.uri)

  }


}
