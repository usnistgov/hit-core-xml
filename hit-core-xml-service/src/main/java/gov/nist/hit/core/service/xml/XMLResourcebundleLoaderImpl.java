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
import gov.nist.hit.core.domain.TestDomain;
import gov.nist.hit.core.domain.XMLTestContext;
import gov.nist.hit.core.service.ResourcebundleLoader;
import gov.nist.hit.core.service.exception.ProfileParserException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLResourcebundleLoaderImpl extends ResourcebundleLoader {

  static final Logger logger = LoggerFactory.getLogger(XMLResourcebundleLoaderImpl.class);
  final static String SCHEMA_PATTERN = "Global/Schemas/";
  final static String SCHEMATRON_PATTERN = "Global/Schematrons/";


  public XMLResourcebundleLoaderImpl() {}


  @Override
  public TestCaseDocument setTestContextDocument(TestContext c, TestCaseDocument doc)
      throws IOException {
    return doc;
  }


  @Override
  public TestContext testContext(String path, JsonNode domainObj) throws IOException {
    XMLTestContext testContext = new XMLTestContext();
    testContext.setDomain(TestDomain.XML);
    Set<String> schemaPathList = new HashSet<String>();
    JsonNode schemaPathListObj = domainObj.findValue("schemaPathList");
    Iterator<JsonNode> it = schemaPathListObj.iterator();
    while (it.hasNext()) {
      JsonNode node = it.next();
      schemaPathList.add(node.getTextValue());
    }

    Set<String> schematronPathList = new HashSet<String>();
    JsonNode schematronPathListObj = domainObj.findValue("schematronPathList");
    it = schematronPathListObj.iterator();
    while (it.hasNext()) {
      JsonNode node = it.next();
      schematronPathList.add(node.getTextValue());
    }
    testContext.setSchemaPathList(schemaPathList);
    testContext.setSchematronPathList(schematronPathList);
    return testContext;
  }


  @Override
  public ProfileModel parseProfile(String integrationProfileXml, String conformanceProfileId,
      String constraintsXml, String additionalConstraintsXml) throws ProfileParserException {
    throw new UnsupportedOperationException();
  }



}
