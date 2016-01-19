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

package gov.nist.hit.core.api.xml;

import gov.nist.hit.core.api.TestContextController;
import gov.nist.hit.core.domain.*;
import gov.nist.hit.core.repo.TestCaseRepository;
import gov.nist.hit.core.service.*;
import gov.nist.hit.core.service.exception.MessageException;
import gov.nist.hit.core.service.exception.MessageParserException;
import gov.nist.hit.core.service.exception.MessageValidationException;
import gov.nist.hit.core.service.exception.TestCaseException;
import gov.nist.hit.core.service.xml.XMLMessageParser;
import gov.nist.hit.core.service.xml.XMLMessageValidator;
import gov.nist.hit.core.xml.domain.XMLTestContext;
import gov.nist.hit.core.xml.repo.XMLTestContextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Harold Affo (NIST)
 * 
 */

@RequestMapping("/xml/testcontext")
@RestController
public class XMLTestContextController extends TestContextController {

  Logger logger = LoggerFactory.getLogger(XMLTestContextController.class);

  @Autowired
  private XMLTestContextRepository testContextRepository;

  @Autowired
  private XMLMessageValidator messageValidator;

  @Autowired
  private XMLMessageParser messageParser;

  @Autowired
  protected TestCaseRepository testCaseRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private TestStepService testStepService;

  @Override
  public MessageValidator getMessageValidator() {
    return null;
  }

  @Override
  public MessageParser getMessageParser() {
    return null;
  }

  @Override
  public TestContext getTestContext(Long aLong) {
    return null;
  }

  @Override
  public ValidationReportConverter getValidatioReportConverter() {
    return null;
  }

  @RequestMapping(value = "/{domain}/{testContextId}")
  public XMLTestContext testContext(@PathVariable final Long testContextId) {
    logger.info("Fetching testContext with id=" + testContextId);
    XMLTestContext testContext = testContextRepository.findOne(testContextId);
    if (testContext == null) {
      throw new TestCaseException("No test context available with id=" + testContextId);
    }
    return testContext;
  }

  @RequestMapping(value = "/{testContextId}/parseMessage", method = RequestMethod.POST)
  public MessageModel parse(@PathVariable final Long testContextId,
      @RequestBody final MessageParserCommand command) throws MessageParserException {
    logger.info("Parsing xml message");
    XMLTestContext testContext = testContext(testContextId);
    return messageParser.parse(testContext, command);
  }

  @RequestMapping(value = "/{testContextId}/validateMessage", method = RequestMethod.POST)
  public MessageValidationResult validate(@PathVariable final Long testContextId,
                                          @RequestBody final MessageValidationCommand command) throws MessageValidationException {
    return messageValidator.validate(testContext(testContextId), command);
  }

  public static String getMessageContent(MessageValidationCommand command) throws MessageException {
    String message = command.getContent();
    if (message == null) {
      throw new MessageException("No message provided");
    }
    return message;
  }



}
