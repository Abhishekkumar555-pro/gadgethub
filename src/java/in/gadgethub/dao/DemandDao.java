
package in.gadgethub.dao;

import in.gadgethub.pojo.DemandPojo;
import java.util.List;


public interface DemandDao {
    
    boolean addProduct(DemandPojo demandPojo);
    boolean removeProduct(String userid,String Prodid);
    List<DemandPojo> haveDemanded(String Prodid);
}
