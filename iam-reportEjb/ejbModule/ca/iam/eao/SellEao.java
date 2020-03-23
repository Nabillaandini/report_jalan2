package ca.iam.eao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;

import ca.iam.util.CapFirst;
import ca.iam.util.Settings;

@Stateless
@LocalBean
@Named
public class SellEao{
	
	String result = null;
	CallableStatement cs = null;
	ResultSet rs = null;
	Connection conn = null;
	String query = null;

	String ket = ""; 
	public List<String> getCurrency(String str) throws SQLException{
		List<String> result = new ArrayList<String>();
		int i = 0;
		try{		
			conn = Settings.getConnection();
			query = "{call getCurrency(?)}";
			cs = conn.prepareCall(query);
			cs.setString(1,str);
			cs.execute();
			rs = (ResultSet)cs.getResultSet();
			
			try {
				while(rs.next()){
					String item = new String();
					ket = CapFirst.str(rs.getString("keterangan"));
					item = rs.getString("kode").toUpperCase();
					result.add(item);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				cs.close();
			}
		}
		finally{
			conn.close();
		}
		return result;
	}
	
	public  List<String> getListOfCustomerName(String str) throws SQLException{
		List<String> result = new ArrayList<String>();
		try{		
			conn = Settings.getConnection();
			query = "{call getCustomerName(?)}";
			cs = conn.prepareCall(query);
			cs.setString(1,str);
			cs.execute();
			rs = (ResultSet)cs.getResultSet();
			try {
				while(rs.next()){
					String item = new String();
					item = CapFirst.str(rs.getString("name"));
					result.add(item);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				cs.close();
			}
		}
		finally{
			conn.close();
		}
		return result;
	}
	
	public int getSellQue() throws SQLException{
		int result = 0;
		try{		
			conn = Settings.getConnection();
			query = "{call getSellQue}";
			cs = conn.prepareCall(query);
			cs.execute();
			rs = (ResultSet)cs.getResultSet();
			try {
				if (rs.next()){;
					result = rs.getInt(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				cs.close();
			}
		}
		finally{
			conn.close();
		}
		return result;
	}
	
	public BigDecimal getSumOfCurrency(String kode) throws SQLException{
		BigDecimal result = new BigDecimal(0);
		try{		
			conn = Settings.getConnection();
			query = "{call getSumOfCurrencyByCode(?)}";
			cs = conn.prepareCall(query);
			cs.setString(1,kode);
			cs.execute();
			rs = (ResultSet)cs.getResultSet();
			try {
				if(rs.next()){
					result = rs.getBigDecimal(1);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally {
				cs.close();
			}
		}
		finally{
			conn.close();
		}
		return result;
	}
}
