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

import gov.nist.healthcare.unified.converters.XMLConverter;
import gov.nist.healthcare.unified.model.EnhancedReport;
import gov.nist.hit.core.service.ValidationReportConverter;
import gov.nist.hit.core.service.exception.ValidationReportException;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * @author Harold Affo (NIST)
 */

public abstract class XMLValidationReportConverter implements ValidationReportConverter {

  /**
   * TODO: Implement the method
   */
  @Override
  public String toXML(String json) throws Exception {
    EnhancedReport report = EnhancedReport.from("json", json);
    String xml = new XMLConverter().convert(report);
    return xml;
  }

}
