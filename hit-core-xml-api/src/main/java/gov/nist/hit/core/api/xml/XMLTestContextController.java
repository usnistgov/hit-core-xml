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

import gov.nist.hit.core.domain.MessageValidationResult;
import gov.nist.hit.core.domain.MessageParserCommand;
import gov.nist.hit.core.domain.MessageValidationCommand;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.domain.TestDomain;
import gov.nist.hit.core.domain.XMLTestContext;
import gov.nist.hit.core.repo.TestCaseRepository;
import gov.nist.hit.core.repo.TestContextRepository;
import gov.nist.hit.core.repo.TestStepRepository;
import gov.nist.hit.core.repo.XMLTestContextRepository;
import gov.nist.hit.core.service.MessageParser;
import gov.nist.hit.core.service.MessageValidator;
import gov.nist.hit.core.service.ProfileParser;
import gov.nist.hit.core.service.ReportService;
import gov.nist.hit.core.service.exception.MessageException;
import gov.nist.hit.core.service.exception.MessageParserException;
import gov.nist.hit.core.service.exception.MessageValidationException;
import gov.nist.hit.core.service.exception.TestCaseException;
import gov.nist.hit.core.service.xml.XMLMessageParser;
import gov.nist.hit.core.service.xml.XMLMessageValidator;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Harold Affo (NIST)
 * 
 */ 

@RequestMapping("/xml/testcontext")
@RestController
public class XMLTestContextController {

  Logger logger = LoggerFactory.getLogger(XMLTestContextController.class);
  
  @Autowired 
  private XMLTestContextRepository testContextRepository;

  @Autowired
   private XMLMessageValidator messageValidator;

  @Autowired
  private XMLMessageParser messageParser;

  @Autowired
  private ReportService reportService;

  @Autowired
  protected TestCaseRepository testCaseRepository;

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
  public List<gov.nist.hit.core.domain.MessageElement> parse(
      @PathVariable final Long testContextId, @RequestBody final MessageParserCommand command)
      throws MessageParserException {
    logger.info("Parsing xml message");
    XMLTestContext testContext = testContext(testContextId);
    return messageParser.parse(testContext, command).getElements();
  }

  @RequestMapping(value = "/{testContextId}/validateMessage", method = RequestMethod.POST)
  public MessageValidationResult validate(@PathVariable final Long testContextId,
      @RequestBody final MessageValidationCommand command) throws MessageValidationException {
    try {
      XMLTestContext testContext = testContext(testContextId);
      return messageValidator.validate(testContext, command);
     } catch (MessageValidationException e) {
      throw new MessageValidationException(e.getMessage());
    } catch (Exception e) {
      throw new MessageValidationException(e.getMessage());
    }
  }

  public static String getMessageContent(MessageValidationCommand command) throws MessageException {
    String message = command.getContent();
    if (message == null) {
      throw new MessageException("No message provided");
    }
    return message;
  }



}
