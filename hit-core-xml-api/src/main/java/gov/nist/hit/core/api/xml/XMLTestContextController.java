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

 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
 
 import gov.nist.hit.core.api.TestContextController;
 import gov.nist.hit.core.domain.TestContext;
 import gov.nist.hit.core.service.MessageParser;
 import gov.nist.hit.core.service.MessageValidator;
 import gov.nist.hit.core.service.ValidationReportConverter;
 import gov.nist.hit.core.service.xml.XMLMessageParser;
 import gov.nist.hit.core.service.xml.XMLMessageValidator;
 import gov.nist.hit.core.service.xml.XMLValidationReportConverter;
 import gov.nist.hit.core.xml.repo.XMLTestContextRepository;
 
 /**
  * @author Harold Affo (NIST)
  * 
  */
 
 @RequestMapping("/xml/testcontext")
 @RestController
 public class XMLTestContextController extends TestContextController {
 
   Logger logger = LoggerFactory.getLogger(XMLTestContextController.class);
 
   @Autowired
   public XMLValidationReportConverter xmlValidationReportConverter;
   
   @Autowired
   public XMLMessageValidator xmlMessageValidator;
 
   @Autowired
   public XMLMessageParser xmlMessageParser;
 
   @Autowired
   public XMLTestContextRepository xmlTestContextRepository;
 
   @Override
   public MessageValidator getMessageValidator() {
     return xmlMessageValidator;
   }
 
   @Override
   public MessageParser getMessageParser() {
     return xmlMessageParser;
   }
 
   @Override
   public TestContext getTestContext(Long aLong) {
     return xmlTestContextRepository.findOne(aLong);
   }
 
   @Override
   public ValidationReportConverter getValidatioReportConverter() {
     return xmlValidationReportConverter;
   }
 
 }
 