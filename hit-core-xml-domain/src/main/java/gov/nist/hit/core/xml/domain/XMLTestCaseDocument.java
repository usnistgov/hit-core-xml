package gov.nist.hit.core.xml.domain;

import gov.nist.hit.core.domain.TestCaseDocument;

import java.io.Serializable;

public class XMLTestCaseDocument extends TestCaseDocument implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  protected boolean exMsgPresent;
  protected boolean xmlConfProfilePresent;
  protected boolean xmlValueSetLibraryPresent;
  protected boolean xmlConstraintPresent;


  public XMLTestCaseDocument() {
    super();
    this.format = "xml";
  }

  public boolean isExMsgPresent() {
    return exMsgPresent;
  }

  public void setExMsgPresent(boolean exMsgPresent) {
    this.exMsgPresent = exMsgPresent;
  }

  public boolean isXmlConfProfilePresent() {
    return xmlConfProfilePresent;
  }

  public void setXmlConfProfilePresent(boolean xmlConfProfilePresent) {
    this.xmlConfProfilePresent = xmlConfProfilePresent;
  }

  public boolean isXmlValueSetLibraryPresent() {
    return xmlValueSetLibraryPresent;
  }

  public void setXmlValueSetLibraryPresent(boolean xmlValueSetLibraryPresent) {
    this.xmlValueSetLibraryPresent = xmlValueSetLibraryPresent;
  }

  public boolean isXmlConstraintPresent() {
    return xmlConstraintPresent;
  }

  public void setXmlConstraintPresent(boolean xmlConstraintsPresent) {
    this.xmlConstraintPresent = xmlConstraintsPresent;
  }

}
