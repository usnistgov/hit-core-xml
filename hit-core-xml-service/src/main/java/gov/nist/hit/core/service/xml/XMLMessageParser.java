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
package gov.nist.hit.core.service.xml;

import gov.nist.hit.core.domain.MessageElement;
import gov.nist.hit.core.domain.MessageModel;
import gov.nist.hit.core.domain.util.XmlUtil;
import gov.nist.hit.core.service.MessageParser;
import gov.nist.hit.core.service.exception.MessageParserException;
import gov.nist.hit.core.service.exception.XmlParserException;
import gov.nist.hit.core.xml.domain.XMLMessageElementData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gov.nist.hit.core.xml.domain.XMLMessageElementData;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

 
public abstract class XMLMessageParser implements MessageParser {

  public MessageModel parse(String xml) throws MessageParserException {
    try {
      Document document = XmlUtil.toDocument(xml);
      Element element = document.getRootElement();
      MessageModel model = new MessageModel();
      XMLMessageElementData data = new XMLMessageElementData(element);
      MessageElement parentNode = getMessageElement(data);
      processChildren(element.getChildren(), parentNode);
      model.getElements().add(parentNode);
      return model;
    } catch (JDOMException | IOException e) {
      throw new XmlParserException(e);
    }
  }

  private void processChildren(List<Element> childElements, MessageElement parent) {
    for (int i = 0; i < childElements.size(); i++) {
      Element element = childElements.get(i);
      MessageElement childNode = getMessageElement(new XMLMessageElementData(element), parent);
      if (!element.getChildren().isEmpty()) {
        processChildren(element.getChildren(), childNode);
      } else if (element.getValue() != null && !"".equals(element.getValue())) {
        getMessageElement(new XMLMessageElementData(element), element.getValue(), childNode);
      }
    }
  }


  private MessageElement getMessageElement(XMLMessageElementData data) {
    return getMessageElement(data, null);
  }

  private MessageElement getMessageElement(XMLMessageElementData data, MessageElement parent) {
    MessageElement element = new MessageElement();
    element.setData(data);
    List<MessageElement> children = new ArrayList<MessageElement>();
    if (parent != null) {
      if (parent.getChildren() == null) {
        parent.setChildren(new ArrayList<MessageElement>());
      }
      parent.getChildren().add(element);
    }
    element.setChildren(children);
    element.setLabel(data.getName());
    return element;
  }


  private MessageElement getMessageElement(XMLMessageElementData data, String label,
      MessageElement parent) {
    MessageElement element = getMessageElement(data, parent);
    element.setLabel(label);
    return element;
  }



}
