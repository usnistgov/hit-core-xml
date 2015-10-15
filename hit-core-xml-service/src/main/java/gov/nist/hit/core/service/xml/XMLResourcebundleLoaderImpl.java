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
import gov.nist.hit.core.domain.Stage;
import gov.nist.hit.core.domain.TestCaseDocument;
import gov.nist.hit.core.domain.TestContext;
import gov.nist.hit.core.domain.VocabularyLibrary;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.core.service.exception.ProfileParserException;
import gov.nist.hit.core.xml.domain.XMLTestCaseDocument;
import gov.nist.hit.core.xml.domain.XMLTestContext;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLResourcebundleLoaderImpl extends ResourcebundleLoader {

  static final Logger logger = LoggerFactory.getLogger(XMLResourcebundleLoaderImpl.class);
  final static String SCHEMA_PATTERN = "Global/Schemas/";
  final static String SCHEMATRON_PATTERN = "Global/Schematrons/";
  static final String FORMAT = "xml";


  public XMLResourcebundleLoaderImpl() {}


  @Override
  public TestCaseDocument generateTestCaseDocument(TestContext c) throws IOException {
    return new XMLTestCaseDocument();
  }


  @Override
  public TestContext testContext(String path, JsonNode formatObj, Stage stage) throws IOException {
    if (formatObj.findValue(FORMAT) == null){
          return null;
        } else {
          logger.debug("processing xml testContext");
      formatObj = formatObj.findValue(FORMAT);
      XMLTestContext testContext = new XMLTestContext();
      testContext.setFormat(FORMAT);
      testContext.setStage(stage);
      Set<String> schemaPathList = new HashSet<String>();
      JsonNode schemaPathListObj = formatObj.findValue("schemaPathList");
      Iterator<JsonNode> it = schemaPathListObj.iterator();
      while (it.hasNext()) {
        JsonNode node = it.next();
        schemaPathList.add(node.getTextValue());
      }

      Set<String> schematronPathList = new HashSet<String>();
      JsonNode schematronPathListObj = formatObj.findValue("schematronPathList");
      it = schematronPathListObj.iterator();
      while (it.hasNext()) {
        JsonNode node = it.next();
        schematronPathList.add(node.getTextValue());
      }
      testContext.setSchemaPathList(schemaPathList);
      testContext.setSchematronPathList(schematronPathList);
      return testContext;
    }
  }

  @Override
  public ProfileModel parseProfile(String integrationProfileXml, String conformanceProfileId,
      String constraintsXml, String additionalConstraintsXml) throws ProfileParserException {
    throw new UnsupportedOperationException();
  }


  @Override
  protected VocabularyLibrary vocabLibrary(String content) throws JsonGenerationException,
      JsonMappingException, IOException {
    throw new UnsupportedOperationException();
  }



}
