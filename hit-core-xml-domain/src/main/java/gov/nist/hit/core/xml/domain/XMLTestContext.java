package gov.nist.hit.core.xml.domain;
import gov.nist.hit.core.domain.TestContext;

import java.util.HashSet;
import java.util.Set;

 import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
 

@Entity
public class XMLTestContext extends TestContext { 

  private static final long serialVersionUID = 1L;
 
  @ElementCollection
  @Column(name="schemaPathList")
  protected Set<String> schemaPathList = new HashSet<String>();
  
  @ElementCollection
  @Column(name="schematronPathList")
  protected Set<String> schematronPathList = new HashSet<String>();

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
  

}
