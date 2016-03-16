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

import gov.nist.hit.core.domain.ProfileModel;
import gov.nist.hit.core.domain.TestCaseDocument;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.domain.TestingStage;
import gov.nist.hit.core.domain.VocabularyLibrary;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.core.service.exception.ProfileParserException;
import gov.nist.hit.core.service.util.FileUtil;
import gov.nist.hit.core.xml.domain.XMLTestCaseDocument;
import gov.nist.hit.core.xml.domain.XMLTestContext;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gov.nist.hit.core.xml.repo.XMLTestContextRepository;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class XMLResourcebundleLoaderImpl extends ResourcebundleLoader {

  static final Logger logger = LoggerFactory.getLogger(XMLResourcebundleLoaderImpl.class);
  final static String SCHEMA_PATTERN = "Global/Schemas/";
  final static String SCHEMATRON_PATTERN = "Global/Schematrons/";
  static final String FORMAT = "xml";


  @Autowired
  XMLTestContextRepository testContextRepository;


  public XMLResourcebundleLoaderImpl() {}


  @Override
  public TestCaseDocument generateTestCaseDocument(TestContext c) throws IOException {
    XMLTestCaseDocument doc = new XMLTestCaseDocument();
    if (c != null) {
      XMLTestContext context = testContextRepository.findOne(c.getId());
      doc.setExMsgPresent(context.getMessage() != null && context.getMessage().getContent() != null);
      /*doc.setXmlConfProfilePresent(context.get != null
              && context.getConformanceProfile().getJson() != null);
      doc.setXmlValueSetLibraryPresent(context.getVocabularyLibrary() != null
              && context.getVocabularyLibrary().getJson() != null);
      doc.setXmlConstraintPresent(context.getAddditionalConstraints() != null && context.getAddditionalConstraints().getXml() != null);
      ObjectMapper mapper = new ObjectMapper();
      logger.info("TestCaseDocument : "+ mapper.writeValueAsString(doc));*/
    }
    return new XMLTestCaseDocument();
  }


  @Override
  public TestContext testContext(String path, JsonNode formatObj, TestingStage stage)
      throws IOException {
    if (formatObj.findValue(FORMAT) == null) {
      return null;
    } else {
      logger.debug("processing xml testContext");
      formatObj = formatObj.findValue(FORMAT);
      XMLTestContext testContext = new XMLTestContext();
      testContext.setFormat(FORMAT);
      testContext.setStage(stage);

      testContext.setMessage(message(FileUtil.getContent(getResource(path + "Message.xml"))));

      testContext.setSchemaPathList(getSchemas(path, formatObj.findValue("schemaPathList")));
      testContext.setSchematronPathList(getSchematrons(path,
          formatObj.findValue("schematronPathList")));
      return testContext;
    }
  }

  private Set<String> getSchemas(String path, JsonNode schemaPathListObj) throws IOException {
    Set<String> schemaPathList = new HashSet<String>();
    if (schemaPathListObj != null && schemaPathListObj.isArray()) {
      Iterator<JsonNode> it = schemaPathListObj.iterator();
      while (it.hasNext()) {
        JsonNode node = it.next();
        if (node.textValue() != null && !"".equals(node.textValue())) {
          schemaPathList.add(SCHEMA_PATTERN + node.textValue());
        }
      }
    }
    List<Resource> specificSchemas = getResources(path + "*.xsd");
    if (specificSchemas != null && !specificSchemas.isEmpty()) {
      for (Resource schema : specificSchemas) {
        schemaPathList.add(path + schema.getFilename());
      }
    }
    return schemaPathList;
  }

  private Set<String> getSchematrons(String path, JsonNode schematronPathListObj)
      throws IOException {
    Set<String> schematronPathList = new HashSet<String>();
    if (schematronPathListObj != null && schematronPathListObj.isArray()) {
      Iterator<JsonNode> it = schematronPathListObj.iterator();
      while (it.hasNext()) {
        JsonNode node = it.next();
        if (node.textValue() != null && !"".equals(node.textValue())) {
          schematronPathList.add(SCHEMATRON_PATTERN + node.textValue());
        }
      }
    }
    List<Resource> specificSchematrons = getResources(path + "*.sch");
    if (specificSchematrons != null && !specificSchematrons.isEmpty()) {
      for (Resource schematron : specificSchematrons) {
        schematronPathList.add(path + schematron.getFilename());
      }
    }
    return schematronPathList;
  }


  @Override
  public ProfileModel parseProfile(String integrationProfileXml, String conformanceProfileId,
      String constraintsXml, String additionalConstraintsXml) throws ProfileParserException {
    throw new UnsupportedOperationException();
  }


  @Override
  public VocabularyLibrary vocabLibrary(String content) throws JsonGenerationException,
      JsonMappingException, IOException {
    throw new UnsupportedOperationException();
  }



}
