package gov.nist.hit.core.xml.domain;

import gov.nist.hit.core.domain.TestContext;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;


@Entity
public class XMLTestContext extends TestContext {

  private static final long serialVersionUID = 1L;

  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "schemaPathList")
  protected Set<String> schemaPathList = new HashSet<String>();

  @ElementCollection(fetch = FetchType.EAGER)
  @Column(name = "schematronPathList")
  protected Set<String> schematronPathList = new HashSet<String>();

  protected String type;

  public Set<String> getSchemaPathList() {
    return schemaPathList;
  }

  public void setSchemaPathList(Set<String> schemaPathList) {
    this.schemaPathList = schemaPathList;
  }

  public Set<String> getSchematronPathList() {
    return schematronPathList;
  }

  public void setSchematronPathList(Set<String> schematronPathList) {
    this.schematronPathList = schematronPathList;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
