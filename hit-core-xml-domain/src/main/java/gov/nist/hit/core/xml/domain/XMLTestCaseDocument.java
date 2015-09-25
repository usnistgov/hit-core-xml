package gov.nist.hit.core.xml.domain;

import gov.nist.hit.core.domain.TestCaseDocument;

import java.io.Serializable;

public class XMLTestCaseDocument extends TestCaseDocument implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  public XMLTestCaseDocument() {
    super();
    this.format = "xml";
  }


}
