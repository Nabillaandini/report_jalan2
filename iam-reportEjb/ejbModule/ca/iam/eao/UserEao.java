package ca.iam.eao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Named;


import ca.iam.entity.CountList;
import ca.iam.entity.Provision;
import ca.iam.entity.SummaryModel;
import ca.iam.entity.UserModel;
import ca.iam.util.Helper;
import ca.iam.util.SQLConn;
import ca.iam.util.ReportConn;
import ca.iam.util.Settings;

@Stateless
@LocalBean
@Named
public class UserEao {
	String result = null;
	CallableStatement cs = null;
	ResultSet rs = null;
	Connection conn = null;
	String query = null;

	public ArrayList<UserModel> getUserOnboard(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userList = new ArrayList<UserModel>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getUserOnboard(?,?)}");
			System.out.println(stmt);
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					String type = rs.getString(2);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						UserModel user = new UserModel(date_str, type);
						userList.add(user);
					}

				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return userList;
	}
	
	public ArrayList<UserModel> getUserOnboardMonthly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userList = new ArrayList<UserModel>();

		try {

			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin+1);
			System.out.println(month_end+1);
			System.out.println("before exec sql");

			CallableStatement stmt = conn.prepareCall("{call getUserOnboardMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin+1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end+1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					String type = rs.getString(3);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						UserModel user = new UserModel(type,month,year);
						userList.add(user);
					}

				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return userList;
	}
	
	public ArrayList<UserModel> getUserOnboardWeekly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<UserModel> userList = new ArrayList<UserModel>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getUserOnboardWeekly(?,?)}");
			System.out.println(stmt);
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String type = rs.getString(3);

					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						UserModel user = new UserModel(a_str,b_str, type);
						userList.add(user);
					}

				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}

		return userList;
	}


	public ArrayList<SummaryModel> getSummaryReport(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<SummaryModel> sumModel = new ArrayList<SummaryModel>();
		SummaryModel model = new SummaryModel();

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
		

				CallableStatement stmt = conn.prepareCall("{call getSummaryReport(?,?,?,?)}");
				stmt.setInt(1, month_begin+1);
				stmt.setInt(2, year_begin);
				stmt.setInt(3, month_end+1);
				stmt.setInt(4, year_end);
				System.out.println(month_begin + "begin");
				stmt.execute();
				rs = stmt.getResultSet();

				try {
					while (rs.next()) {
						int year = rs.getInt(1);
						int month = rs.getInt(2);
						int countOnboard = rs.getInt(3);
						int countUpdate = rs.getInt(4);
						int sap = rs.getInt(5);
						int dtobm = rs.getInt(6);
						int dtkbm = rs.getInt(7);
						int mms = rs.getInt(8);
						int raos = rs.getInt(9);
						int wbg = rs.getInt(10);
						int countDeprov = rs.getInt(11);
						int resetDomain = rs.getInt(12);
						int resetApps = rs.getInt(13);
						int ebudgeting = rs.getInt(14);
						int fico = rs.getInt(15);
						int mm = rs.getInt(16);
						int srm = rs.getInt(17);
						int skd = rs.getInt(18);
						int deprovSAP = rs.getInt(19);
						int deprovDTKBM = rs.getInt(20);
						int deprovDTOBM = rs.getInt(21);
						int wcp = rs.getInt(22);
						int aap = rs.getInt(13);
						int sumDTOBM = rs.getInt(14);
						int sumDTKBM = rs.getInt(15);
						model = new SummaryModel(countOnboard, countUpdate, sap, dtobm, dtkbm, mms, raos, wbg,
								countDeprov, resetDomain, resetApps,ebudgeting,fico,mm,srm,skd,deprovSAP,deprovDTKBM,deprovDTOBM,wcp,aap,sumDTOBM,sumDTKBM,month,year);
						sumModel.add(model);

						
					}
					System.out.println("selesai rs1");
					stmt.getMoreResults();
					ResultSet rs2 = stmt.getResultSet();
					System.out.println("get rs 2");
					while(rs2.next()) {
						
						int  year = rs2.getInt(1);
						int month = rs2.getInt(2);
						String temp = new  DateFormatSymbols().getMonths()[month-1]+" "+year;
						for(int i=0;i<sumModel.size();i++) {
							if(sumModel.get(i).getDate().equalsIgnoreCase(temp)) {
								System.out.println(temp);
								 int raospw=rs2.getInt(3);
								 int mmspw=rs2.getInt(4);
								 int dtobmpw=rs2.getInt(5);
								 int dtkbmpw = rs2.getInt(6);
								 int sappw = rs2.getInt(7);
								 int webgpw = rs2.getInt(8);
								 int skdpw = rs2.getInt(9);
								 int aappw = rs2.getInt(10);
								 int ebudpw = rs2.getInt(11);
								 int ehcms = rs2.getInt(12);
								 int fico = rs2.getInt(13);
								 int srm = rs2.getInt(14);
								 int mm= rs2.getInt(15);
								 int wcp = rs2.getInt(16);
								sumModel.get(i).setRaospw(raospw);
								sumModel.get(i).setMmspw(mmspw);
								sumModel.get(i).setDtobmpw(dtobmpw);
								sumModel.get(i).setDtkbm(dtkbmpw);
								sumModel.get(i).setSappw(sappw);
								sumModel.get(i).setWebgpw(webgpw);
								sumModel.get(i).setSkdpw(skdpw);
								sumModel.get(i).setAappw(aappw);
								sumModel.get(i).setEbudpw(ebudpw);
								sumModel.get(i).setEhcmspw(ehcms);
								sumModel.get(i).setFicopw(fico);
								sumModel.get(i).setSrmpw(srm);
								sumModel.get(i).setMmpw(mm);
								sumModel.get(i).setWcppw(wcp);
								break;
							}
						}
						
					}

				} finally {
					stmt.close();
				}
				
			

		} finally {
			conn.close();
		}
		return sumModel;
	}

	public ArrayList<CountList> countProvision(Date begin_date, Date end_date, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countProvision(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%" + apps + "%");

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(2);
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return resultCount;
	}

	public ArrayList<Provision> getProvisionData(Date begin_date, Date end_date, String apps)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getProvisionData(?,?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);
			stmt.setString(3, "%" + apps + "%");

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				if (rs != null) {
					while (rs.next()) {
						Date date = rs.getDate(1);
						if (date == null) {
							continue;
						}
						String date_str = Helper.dateToString(date);
						String type = rs.getString(2);
						String[] arr = type.split(",");
						type = arr[1].substring(6);
						if (!(type.contains("failed") || type.contains(":"))) {
							prov = new Provision(date_str, type);
							userList.add(prov);
						}
					}
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return userList;
	}

	public ArrayList<CountList> countDeprovision(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countDeprovision(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int count = rs.getInt(1);
					Date date = rs.getDate(2);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count, "SAP");
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}
	
	

	public ArrayList<CountList> countDeprovMonthly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countDeprovMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int count = rs.getInt(3);
					System.out.println(month);
					System.out.println(year);
					String date_str = new DateFormatSymbols().getMonths()[month - 1] + " " + year;
					CountList temp = new CountList(date_str, count, "SAP");
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}
	public ArrayList<CountList> countProvMonthly(Date begin_date, Date end_date, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println("before exec sql");
			String appsName = "%" + apps +"%";
			CallableStatement stmt = conn.prepareCall("{call countProvMonthly(?,?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.setString(5,appsName );

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int count = rs.getInt(3);
					System.out.println(month);
					System.out.println(year);
					String date_str = new DateFormatSymbols().getMonths()[month - 1] + " " + year;
					CountList temp = new CountList(date_str, count, apps);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}
	public ArrayList<CountList> countProvMonthlyApps(Date begin_date, Date end_date, String apps) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println("before exec sql");
			String appsName = "%" + apps +"%";
			CallableStatement stmt = conn.prepareCall("{call countProvMonthlyApps(?,?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);
			stmt.setString(5,appsName );

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int count = rs.getInt(3);
					System.out.println(month);
					System.out.println(year);
					String date_str = new DateFormatSymbols().getMonths()[month - 1] + " " + year;
					CountList temp = new CountList(date_str, count, apps);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}


	public ArrayList<Provision> getDeprovData(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getDeprovData(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					String type = rs.getString(2);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						if (!((type.charAt(0) == 'K') || (type.charAt(0) == 'O'))) {
							prov = new Provision(date_str, type,"SAP");
							userList.add(prov);
						}
					}
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return userList;
	}
	public ArrayList<Provision> getDeprovWeekly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision prov = new Provision();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call getDeprovWeekly(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					String type = rs.getString(3);
					String[] arr = type.split(",");
					type = arr[1].substring(6);
					if (!(type.contains("failed") || type.contains(":"))) {
						if (!((type.charAt(0) == 'K') || (type.charAt(0) == 'O'))) {
							prov = new Provision(a_str,b_str,type,"SAP");
							userList.add(prov);
						}
					}
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return userList;
	}

	public ArrayList<Provision> getDeprovMonthly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<Provision> userList = new ArrayList<Provision>();
		Provision user = new Provision();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin + 1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call getDeprovMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin + 1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end + 1);
			stmt.setInt(4, year_end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {

					int year = rs.getInt(1);
					int month = rs.getInt(2);
					String desc = rs.getString(3);
					String[] arr = desc.split(",");
					desc = arr[1].substring(6);
					if (!(desc.contains("failed") || desc.contains(":"))) {
						if (!(desc.charAt(0) == 'O') && !(desc.charAt(0) == 'K')) {
							user = new Provision(desc, month, year,"SAP");
							userList.add(user);
						}
					}

				}

			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		return userList;
	}

	

	
	public ArrayList<CountList> countDeprovWeek(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countDeprovWeek(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int countSAP = rs.getInt(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str,b_str, countSAP,"SAP");
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}
	public ArrayList<CountList> countOnboardMonthly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(begin_date);
			int month_begin = begin_date.getMonth();
			int month_end = end_date.getMonth();
			int year_begin = cal.get(Calendar.YEAR);
			cal.setTime(end_date);
			int year_end = cal.get(Calendar.YEAR);
			System.out.println(month_begin+1);
			System.out.println(month_end+1);
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countOnboardMonthly(?,?,?,?)}");
			stmt.setInt(1, month_begin+1);
			stmt.setInt(2, year_begin);
			stmt.setInt(3, month_end+1);
			stmt.setInt(4, year_end);
			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					int year = rs.getInt(1);
					int month = rs.getInt(2);
					int countPass = rs.getInt(3);
					CountList temp = new CountList(countPass, month,year);
					resultCount.add(temp);
					
					
					
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
			return resultCount;
		

	}
	
	public ArrayList<CountList> countOnboardDaily(Date begin_date, Date end_date)
			throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());

			CallableStatement stmt = conn.prepareCall("{call countOnboardDaily(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date date = rs.getDate(1);
					int count = rs.getInt(2);
					if (date == null) {
						continue;
					}
					String date_str = Helper.dateToString(date);
					CountList temp = new CountList(date_str, count);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}

		return resultCount;
	}
	
	public ArrayList<CountList> countOnboardWeekly(Date begin_date, Date end_date) throws SQLException, ParseException {

		ResultSet rs = null;
		Connection conn = SQLConn.getConnectionSql();
		ArrayList<CountList> resultCount = new ArrayList<CountList>();

		try {

			java.sql.Date begin = new java.sql.Date(begin_date.getTime());
			java.sql.Date end = new java.sql.Date(end_date.getTime());
			System.out.println("before exec sql");
			CallableStatement stmt = conn.prepareCall("{call countOnboardWeekly(?,?)}");
			stmt.setDate(1, (java.sql.Date) begin);
			stmt.setDate(2, (java.sql.Date) end);

			stmt.execute();
			rs = stmt.getResultSet();

			try {
				while (rs.next()) {
					Date a = rs.getDate(1);
					Date b = rs.getDate(2);
					int count = rs.getInt(3);
					String a_str = Helper.dateToStringWeek(a);
					String b_str = Helper.dateToStringWeek(b);
					CountList temp = new CountList(a_str,b_str,count);
					resultCount.add(temp);
				}
			} finally {
				stmt.close();
			}

		} finally {
			conn.close();
		}
		System.out.println(resultCount.size());

		return resultCount;
	}
}
