
package dao;
 
import domain.Sale;
 
public interface SaleDAOInterface {
 
	void save(Sale sale);
        
        void email(Sale sale);
 
}