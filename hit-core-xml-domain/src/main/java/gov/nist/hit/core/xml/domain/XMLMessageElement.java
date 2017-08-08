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
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Harold Affo (NIST)
 * 
 */
public class XMLMessageElement implements Serializable {

  private static final long serialVersionUID = 1L;

  public static String DEFAULT_TYPE = "default";

  private XMLMessageElementData data;

  private String label;

  private List<XMLMessageElement> children;

  public XMLMessageElement() {
    this.data = null;
    this.children = null;
    this.label = "No defined";
  }

  public XMLMessageElement(XMLMessageElementData data) {
    this(data, null);
  }

  public XMLMessageElement(XMLMessageElementData data, XMLMessageElement parent) {
    this.data = data;
    this.children = new ArrayList<XMLMessageElement>();
    if (parent != null) {
      if (parent.getChildren() == null) {
        parent.setChildren(new ArrayList<XMLMessageElement>());
      }
      parent.getChildren().add(this);
    }
    label = data.getName();
  }

  public XMLMessageElement(XMLMessageElementData data, String label, XMLMessageElement parent) {
    this.data = data;
    this.children = new ArrayList<XMLMessageElement>();
    if (parent != null) {
      if (parent.getChildren() == null) {
        parent.setChildren(new ArrayList<XMLMessageElement>());
      }
      parent.getChildren().add(this);
    }
    this.label = label;
  }

  public XMLMessageElement(String type, XMLMessageElementData data, XMLMessageElement parent) {
    this(data, parent);
  }

  public XMLMessageElementData getData() {
    return this.data;
  }

  public void setData(XMLMessageElementData data) {
    this.data = data;
  }

  public List<XMLMessageElement> getChildren() {
    return this.children;
  }

  public void setChildren(List<XMLMessageElement> children) {
    this.children = children;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public int getChildCount() {
    return this.children != null ? this.children.size() : 0;
  }

  public boolean isLeaf() {
    if (this.children == null) {
      return true;
    }
    return this.children.size() == 0;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (this.data == null ? 0 : this.data.hashCode());
    return result;
  }

  @Override
  public String toString() {
    if (this.data != null) {
      return this.data.toString();
    }
    return "";
  }

  public XMLMessageElement getChild(int line) {
    List<XMLMessageElement> children = getChildren();
    for (XMLMessageElement node : children) {
      XMLMessageElementData data = node.getData();
      if (data.getStart().getLine() == line) {
        return node;
      }
    }
    return null;
  }
}
