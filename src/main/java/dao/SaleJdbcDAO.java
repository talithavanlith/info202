package dao;
 
import domain.Customer;
import domain.Product;
import domain.Sale;
import domain.SaleItem;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
 
public class SaleJdbcDAO implements SaleDAOInterface {
 
	private static final String url = "jdbc:h2:tcp://localhost:9041/project;IFEXISTS=TRUE";
 
	@Override
	public void save(Sale sale) {
 
		Connection con = JdbcConnection.getConnection(url);
		try {
			try (
					PreparedStatement insertSaleStmt = con.prepareStatement(
							"insert into Sale (Person_ID, Date) values (?,?)",
							Statement.RETURN_GENERATED_KEYS);
 
					PreparedStatement insertSaleItemStmt = con.prepareStatement(
							"insert into SaleItem (Sale_ID, Product_ID, QuantityPurchased, SalePrice) values (?,?,?,?)");
 
					PreparedStatement updateProductStmt = con.prepareStatement(
							"UPDATE Product SET quantityInStock = ? WHERE Product_ID = ?;");
 
					) {
 
				// Since saving and sale involves multiple statements across
				// multiple tables we need to control the transaction ourselves
				// to ensure our DB remains consistent.
				//
				// Turn off auto-commit which effectively starts a new transaction.
				con.setAutoCommit(false);
 
				Customer customer = sale.getCustomer();
 
				// #### save the sale ### //
 
				// add a date to the sale if one doesn't already exist
				if(sale.getDate() == null) {
					sale.setDate(new Date());
				}
 
				// convert sale date into to java.sql.Timestamp
				Date date = sale.getDate();
				Timestamp timestamp = new Timestamp(date.getTime());
 
 
				// save the timestamp and username in the
				// sale table using the insertSaleStmt statement.
				insertSaleStmt.setInt(1, customer.getPersonID());
				insertSaleStmt.setTimestamp(2, timestamp);
                                insertSaleStmt.executeUpdate(); 
 
				// get the auto-generated sale ID from the database
				ResultSet rs = insertSaleStmt.getGeneratedKeys();
 
				Integer saleId = null;
 
				if (rs.next()) {
					saleId = rs.getInt(1);
				} else {
					throw new DAOException("Problem getting generated Sale ID");
				}
 
                                sale.setSaleID(saleId);
 
				Collection<SaleItem> items = sale.getItems();
 
				for (SaleItem item : items) {
 
					Product product = item.getProduct();
                                        BigDecimal QuantityPurchased = item.getQuantityPurchased();
 
					// save the sale item using the insertSaleItemStmt statement.
                                        insertSaleItemStmt.setInt(1, saleId);
                                        insertSaleItemStmt.setInt(2, product.getProductID());
                                        insertSaleItemStmt.setBigDecimal(3, QuantityPurchased);
                                        insertSaleItemStmt.setBigDecimal(4, item.getSalePrice());
                                        insertSaleItemStmt.executeUpdate();
                                        
					// update the product quantity using the updateProductStmt statement.
                                        BigDecimal newQuantity = product.getQuantityInStock().subtract(QuantityPurchased);
                                        updateProductStmt.setBigDecimal(1, newQuantity);
                                        updateProductStmt.setInt(2, product.getProductID());
                                        updateProductStmt.executeUpdate();

 
				}
 
				// commit the transaction
				con.setAutoCommit(true);
			}
		} catch (SQLException ex) {
 
			Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
 
			try {
				// something went wrong so rollback
				con.rollback();
 
				// turn auto-commit back on
				con.setAutoCommit(true);
 
				// and throw an exception to tell the user something bad happened
				throw new DAOException(ex.getMessage(), ex);
			} catch (SQLException ex1) {
				throw new DAOException(ex1.getMessage(), ex1);
			}
 
		} finally {
			try {
				con.close();
			} catch (SQLException ex) {
				Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
        
        @Override
	public void email(Sale sale) {
            CompletableFuture.runAsync(() -> {
                String itemsSold = "";
                for(int i =1; i<=sale.getSaleItemList().size(); i++){
                    itemsSold += "\nItem " + i + ": " + sale.getSaleItemList().get(i-1).getProduct().getName() +
                            " | Price: $" + sale.getSaleItemList().get(i-1).getSalePrice();
                }

                String userEmail = sale.getCustomer().getEmail();

                Email email = new SimpleEmail();
                email.setHostName("localhost");
                email.setSmtpPort(2525);

                try {
                    email.setFrom("user@gmail.com");
                    email.setSubject("New Sale #" + sale.getSaleID());
                    email.setMsg("New purchase made by customer: " + sale.getCustomer().getFirstname() + 
                            " " + sale.getCustomer().getSurname() + "\nPurchased items include:" + 
                            itemsSold);

                    email.addTo(userEmail);
                    email.send();
                } catch (EmailException ex) {
                    Logger.getLogger(SaleJdbcDAO.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
	}
 
}