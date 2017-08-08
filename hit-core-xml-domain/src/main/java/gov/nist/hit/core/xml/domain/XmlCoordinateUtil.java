package gov.nist.hit.core.xml.domain;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.located.Located;
import org.jdom2.located.LocatedJDOMFactory;
import org.jdom2.output.XMLOutputter;

import gov.nist.hit.core.domain.Coordinate;

public class XmlCoordinateUtil {



  public static String toString(Element element) {
    return new XMLOutputter().outputString(element);
  }



  public static String toString(Document document) {
    return new XMLOutputter().outputString(document.getRootElement());
  }

  public static Coordinate getStartCoordinate(Element element) {
    Located locatedElement = (Located) element;
    return new Coordinate(locatedElement.getLine(), 0);
  }

  public static Coordinate getEndCoordinate(Element element) {
    return new Coordinate(getEndLine(element), 1000);
  }

  private static int getNumberOfLine(Element element) {
    String content = XmlCoordinateUtil.toString(element);
    String[] lines = content.split(System.getProperty("line.separator"));
    return lines.length;
  }

  public static int getEndLine(Element element) {
    return getStartLine(element) + getNumberOfLine(element) - 1;
  }

  public static int getStartLine(Element element) {
    return ((Located) element).getLine();
  }
}
