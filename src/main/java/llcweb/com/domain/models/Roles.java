package llcweb.com.domain.models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 角色表
 */
@Entity
@Table(name = "roles")
public class Roles implements Serializable {
   /** @pdOid f16a930e-44f7-408b-a69e-7ff0a4f31fe4 */
   @Id
   public int rId;
   public String rName;
   public String rFlag;

   public int getrId() {
      return rId;
   }

   public void setrId(int rId) {
      this.rId = rId;
   }

   public String getrName() {
      return rName;
   }

   public void setrName(String rName) {
      this.rName = rName;
   }

   public String getrFlag() {
      return rFlag;
   }

   public void setrFlag(String rFlag) {
      this.rFlag = rFlag;
   }
}