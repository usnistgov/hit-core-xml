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

import gov.nist.healthcare.unified.enums.Context;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.healthcare.unified.proxy.ValidationProxy;
import gov.nist.healthcare.unified.proxy.XMLValidationProxy;
import gov.nist.hit.core.domain.MessageValidationCommand;
import gov.nist.hit.core.domain.MessageValidationResult;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.service.exception.MessageException;
import gov.nist.hit.core.service.exception.MessageValidationException;
import gov.nist.hit.core.service.util.FileUtil;
import gov.nist.hit.core.service.util.ResourcebundleHelper;
import gov.nist.hit.core.xml.domain.XMLTestContext;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class XMLMessageValidatorImpl extends XMLMessageValidator {

  static final Logger logger = LoggerFactory.getLogger(XMLMessageValidatorImpl.class);

  public XMLMessageValidatorImpl() {
    super();
  }

  @Override
  public MessageValidationResult validate(TestContext testContext, MessageValidationCommand command)
      throws MessageValidationException {
    logger.info("Validating XML file");
    try {
      if (testContext instanceof XMLTestContext) {
        XMLTestContext context = (XMLTestContext) testContext;
        String title = command.getName();
        String contextType = command.getContextType();
        String message = getMessageContent(command);
        ArrayList<String> schematrons = new ArrayList<>();
        for(String schematronPath : context.getSchematronPathList()){
          Resource resource =
                  ResourcebundleHelper.getResource(schematronPath);
          if (resource != null) {
            String schematronContent = FileUtil.getContent(resource);
            logger.info("schematron loaded " + schematronContent);
            schematrons.add(schematronContent);
          }
        }
        XMLValidationProxy vp = new XMLValidationProxy(title, "NIST", "1.0");
        EnhancedReport report =vp.validate(message, null, schematrons, null, "ALL",Context.valueOf(contextType));
        return new MessageValidationResult(report.to("json").toString(), report.render("iz-report",null));
      } else {
        throw new MessageValidationException(
                "Invalid Context Provided. Expected Context is EDITestContext but found "
                        + testContext.getClass().getSimpleName());
      }
//      Resource resource =
//              ResourcebundleHelper.getResource("Global/Schematrons/schematron.sch");
//      if (resource != null) {
//        String res = FileUtil.getContent(resource);
//        logger.info("resource content" + res);
//      }
    }catch (Exception e) {
      throw new MessageValidationException(e);
    }
    // TODO Auto-generated method stub
//    return new MessageValidationResult(null, null);
  }


  public static String getMessageContent(MessageValidationCommand command) throws MessageException {
    String message = command.getContent();
    if (message == null) {
      throw new MessageException("No message provided");
    }
    return message;
  }
}
