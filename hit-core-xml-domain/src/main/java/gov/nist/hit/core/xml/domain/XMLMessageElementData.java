/**
 * This software was developed at the National Institute of Standards and Technology by employees of
 * the Federal Government in the course of their official duties. Pursuant to title 17 Section 105
 * of the United States Code this software is not subject to copyright protection and is in the
 * public domain. This is an experimental system. NIST assumes no responsibility whatsoever for its
 * use by other parties, and makes no guarantees, expressed or implied, about its quality,
 * reliability, or any other characteristic. We would appreciate acknowledgement if the software is
 * used. This software can be redistributed and/or modified freely provided that any derivative
 * works bear some notice that they are derived from it, and any modified versions bear some notice
 * that they have been modified.
 */
package gov.nist.hit.core.xml.domain;


import java.io.Serializable;

import org.jdom2.Element;
import org.jdom2.located.Located;
import org.jdom2.xpath.XPathHelper;

import gov.nist.hit.core.domain.Coordinate;
import gov.nist.hit.core.domain.MessageElementData;
import gov.nist.hit.core.domain.util.XmlUtil;

/**
 * 
 * @author Harold Affo (NIST)
 * 
 */

public class XMLMessageElementData extends MessageElementData implements Serializable {

  private static final long serialVersionUID = 1L;


//  private int column = -1; 
  
  
  public XMLMessageElementData() {
     this.position = -1;
  }
  

  /**
   * 
   * @param element
   */
  public XMLMessageElementData(Element element) {
    this(element, element.getName(), "none", 20000, "20000");
    if (element.getChildren().isEmpty()) {
      this.value = element.getValue();
    } else {
      this.value = null;
    }
  }

  public XMLMessageElementData(Element element, String name, String usage, Integer minOccurs,
      String maxOccurs) {
    Located locatedElement = (Located) element;
    start = XmlCoordinateUtil.getStartCoordinate(element);
    end = XmlCoordinateUtil.getEndCoordinate(element);
//    this.column = locatedElement.getColumn();
    this.position = locatedElement.getColumn();
    this.path = XPathHelper.getAbsolutePath(element);
    this.name = name;
    this.usage = usage;
    this.minOccurs = minOccurs;
    this.maxOccurs = maxOccurs;
    this.value = null;
    this.description = toString();
  }

  @Override
  public String toString() {

    if (this.value != null) {
      return this.value;
    } else {
      StringBuffer buffer1 = new StringBuffer();
      buffer1.append(this.path).append(":".charAt(0)).append(this.name).append(" ".charAt(0))
          .append(this.usage).append("").append("[".charAt(0)).append(minOccurs)
          .append(",".charAt(0)).append(maxOccurs).append("]".charAt(0));
      return buffer1.toString();
    }
  }

  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + (this.maxOccurs == null ? 0 : this.maxOccurs.hashCode());
    result = prime * result + (this.minOccurs == null ? 0 : this.minOccurs.hashCode());
    result = prime * result + (this.name == null ? 0 : this.name.hashCode());
    result = prime * result + (this.path == null ? 0 : this.path.hashCode());
    result = prime * result + (this.usage == null ? 0 : this.usage.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    XMLMessageElementData other = (XMLMessageElementData) obj;
    if (this.maxOccurs == null) {
      if (other.maxOccurs != null) {
        return false;
      }
    } else if (!this.maxOccurs.equals(other.maxOccurs)) {
      return false;
    }
    if (this.minOccurs == null) {
      if (other.minOccurs != null) {
        return false;
      }
    } else if (!this.minOccurs.equals(other.minOccurs)) {
      return false;
    }
    if (this.name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!this.name.equals(other.name)) {
      return false;
    }
    if (this.path == null) {
      if (other.path != null) {
        return false;
      }
    } else if (!this.path.equals(other.path)) {
      return false;
    }
    if (this.usage == null) {
      if (other.usage != null) {
        return false;
      }
    } else if (!this.usage.equals(other.usage)) {
      return false;
    }
    return true;
  }

  @Override
  public int getInstanceNumber() {

    return this.instanceNumber;
  }

//  public int getColumn() {
//    return column;
//  }
//
//  public void setColumn(int column) {
//    this.column = column;
//  }

  public Coordinate getStart() {
    return start;
  }

  public Coordinate getEnd() {
    return end;
  }

}
