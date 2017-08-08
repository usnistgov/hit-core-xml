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



import gov.nist.hit.core.domain.MessageModel;
import gov.nist.hit.core.domain.MessageParserCommand;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.service.exception.MessageParserException;
import gov.nist.hit.core.xml.domain.XMLTestContext;


public class XMLMessageParserImpl extends XMLMessageParser {

  public XMLMessageParserImpl() {
    super();
  } 



  @Override
  public MessageModel parse(TestContext testContext, MessageParserCommand command)
      throws MessageParserException {
    if (testContext instanceof XMLTestContext) {
      return parse(command.getContent()); // FIXME. add schema and schematron info if necessary
    } else {
      throw new MessageParserException(
          "Invalid Context Provided. Expected Context is XMLTestContext but found "
              + testContext.getClass().getSimpleName());
    }
  }



}
