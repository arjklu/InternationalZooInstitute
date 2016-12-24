package com.src.izi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.src.izi.constants.IZIConstants;

/**
 * The purpose of this abstract class that implements the Servlet interface and
 * is specially designed to handle HTTP requests.
 *
 * @author Nagarjun Singharavelu
 * @version 2.0
 * @since 2016-12-23
 */
public class IZIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * This is the constructor which invokes Object class by default.
	 * 
	 * @return Nothing.
	 * @see HttpServlet#HttpServlet()
	 */
	public IZIServlet() {
		super();
	}

	/**
	 * This is the GET method which reads values from JSF page.
	 * 
	 * @param request,
	 *            response
	 * @return Nothing.
	 * @exception IOException,
	 *                ServletException On input error.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		viewInventory(request, response);
	}

	/**
	 * This is the POST method which posts values from JSF page.
	 * 
	 * @param request,
	 *            response
	 * @return Nothing.
	 * @exception IOException,
	 *                ServletException On input error.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String htmlFormName = request.getParameter(IZIConstants.FORM_NAME);
		if (IZIConstants.NEW_ITEM_FORMNAME.equals(htmlFormName)) {
			newItems(request, response);
		} else if (IZIConstants.UPDATE_ITEM_FORMNAME.equals(htmlFormName)) {
			updateInventory(request, response);
		}
	}

	/**
	 * This method creates an alert box and notifies the user when a particular
	 * user action is successful or failed.
	 * 
	 * @param resp,
	 *            msg
	 * @return Nothing.
	 * @exception IOException
	 *                On input error.
	 */
	private void response(HttpServletResponse resp, String msg) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('" + msg + "');");
		out.println("location='iziHome.jsp';");
		out.println("</script>");
	}

	/**
	 * This method is being invoked from doGet method. The purpose of this
	 * method is store all user inputs from newItems.jsp file into the database
	 * 
	 * @param req,
	 *            res
	 * @return Nothing.
	 * @exception IOException
	 *                On input error.
	 */
	private void newItems(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String inventoryQuantity = req.getParameter(IZIConstants.INV_QUANTITY);
		String itemDate = req.getParameter(IZIConstants.ITEM_DATE);
		String zooName = req.getParameter(IZIConstants.ZOO_NAME);

		if (inventoryQuantity.equals("")) {
			response(res, IZIConstants.CHECK_INPUT_MSG);
		} else {
			try {
				// JSON file created here so that any platform application can
				// communicate with our existing services to access data.
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(IZIConstants.INV_QUANTITY, inventoryQuantity);
				jsonObj.put(IZIConstants.ITEM_DATE, itemDate);
				jsonObj.put(IZIConstants.ZOO_NAME, zooName);

				Connection con = getConnection();
				String sql = "INSERT INTO dbo.Inventory VALUES ('" + inventoryQuantity + "','" + itemDate + "','"
						+ zooName + "')";
				PreparedStatement pStatement = con.prepareStatement(sql);
				int rs = pStatement.executeUpdate();
				if (rs > 0) {
					response(res, IZIConstants.UPDATE_SUCCESS);
				} else {
					response(res, IZIConstants.ERROR_MSG);
				}
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is being invoked from doGet method. The purpose of this
	 * method is store all user inputs from updateInventory.jsp file into the
	 * database
	 * 
	 * @param req,
	 *            res
	 * @return Nothing.
	 * @exception IOException
	 *                On input error.
	 */
	private void updateInventory(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String animal = req.getParameter(IZIConstants.ANIMAL_NAME);
		String feedDate = req.getParameter(IZIConstants.FEED_DATE);
		String feedTime = req.getParameter(IZIConstants.FEED_TIME);
		String quantityGiven = req.getParameter(IZIConstants.FEED_QUANTITY);
		String zooName = req.getParameter(IZIConstants.ZOO_NAME);

		if (animal.equals("") || feedDate.equals("") || feedTime.equals("") || quantityGiven.equals("")) {
			response(res, IZIConstants.CHECK_INPUT_MSG);
		} else {
			response(res, IZIConstants.UPDATE_SUCCESS);
			try {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put(IZIConstants.ANIMAL_NAME, animal);
				jsonObj.put(IZIConstants.FEED_DATE, feedDate);
				jsonObj.put(IZIConstants.FEED_TIME, feedTime);
				jsonObj.put(IZIConstants.FEED_QUANTITY, quantityGiven);
				jsonObj.put(IZIConstants.ZOO_NAME, zooName);

				Connection con = getConnection();
				String sql = "INSERT INTO dbo.Consumption VALUES ('" + animal + "','" + feedDate + "','" + feedTime
						+ "','" + quantityGiven + "','" + zooName + "')";
				PreparedStatement pStatement = con.prepareStatement(sql);
				int rs = pStatement.executeUpdate();
				if (rs > 0) {
					response(res, IZIConstants.UPDATE_SUCCESS);
				} else {
					response(res, IZIConstants.ERROR_MSG);
				}
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method is being invoked from doPost method. The purpose of this
	 * method is retrieve all inventory activities from database to the
	 * viewInventory1.jsp file.
	 * 
	 * @param req,
	 *            res
	 * @return Nothing.
	 * @exception IOException,
	 *                ServletException On input error.
	 */
	private void viewInventory(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String animal = req.getParameter(IZIConstants.ANIMAL_NAME);
		String zName = req.getParameter("zooName");

		if (animal.equals("") || zName.equals("")) {
			response(res, IZIConstants.CHECK_INPUT_MSG);
		} else {
			try {
				Connection con = getConnection();
				String sql = "SELECT Animal, Count(*) as Cnt, AVG(CONVERT(INTEGER,Quantity)) as AvgQuantity FROM dbo.Consumption WHERE Animal='"
						+ animal + "' group by Animal, FeedDate";

				PreparedStatement pStatement;
				pStatement = con.prepareStatement(sql);
				ResultSet rs = pStatement.executeQuery();
				while (rs.next()) {
					req.setAttribute(IZIConstants.ANIMAL_NAME, rs.getString("Animal"));
					req.setAttribute(IZIConstants.FEED_TIMES, rs.getString("Cnt"));
					req.setAttribute(IZIConstants.QUANTITY_FED, rs.getString("AvgQuantity"));
				}
				con.close();

				Connection conn = getConnection();
				String sql1 = "SELECT ISNULL((SELECT SUM(CONVERT(INTEGER,Quantity)) FROM dbo.Inventory WHERE ZooName = '"
						+ zName + "') - (SELECT SUM(CONVERT(INTEGER,Quantity)) FROM dbo.Consumption WHERE ZooName = '"
						+ zName + "'),0) AS Diff";

				PreparedStatement pStatement1;
				pStatement1 = conn.prepareStatement(sql1);
				ResultSet rs1 = pStatement1.executeQuery();
				while (rs1.next()) {
					req.setAttribute(IZIConstants.ZOO_NAME, zName);
					req.setAttribute(IZIConstants.FOOD_WASTE, rs1.getString("Diff"));
				}
				conn.close();

				Connection con2 = getConnection();
				String sql2 = "declare @avgQuantity float; "
						+ "SET @avgQuantity = (Select AVG(CONVERT(INTEGER,Quantity)) from dbo.Consumption "
						+ "where Animal='"+ animal +"' and ZooName='"+ zName +"'); "
						+ "Select (Select count(*) from dbo.Consumption "
						+ "where (Convert(Integer,Quantity) > @avgQuantity and Animal='"+ animal +"' and ZooName='"+ zName +"')) as fCount,"
						+ "(Select count(*) from dbo.Consumption where Animal='"+ animal +"' and ZooName='"+ zName +"') as orgCount";

				PreparedStatement pStatement2;
				pStatement2 = con2.prepareStatement(sql2);
				ResultSet rs2 = pStatement2.executeQuery();
				while (rs2.next()) {
					float orgCount = Float.parseFloat(rs2.getString("orgCount"));
					float fCount = Float.parseFloat(rs2.getString("fCount"));
					float percentDiff = (fCount/orgCount)*100;
					String pDiff = Float.toString(percentDiff);
					if(fCount > orgCount){
						req.setAttribute("percentDiff", "more (" + pDiff + "%)");
					} else {
						req.setAttribute("percentDiff", "less (" + pDiff + "%)");
					}
				}
				con2.close();

				RequestDispatcher rd = req.getRequestDispatcher("/viewInventory1.jsp");
				rd.forward(req, res);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method creates database connection and is being used by other
	 * methods whenever there is a database operation involved.
	 * 
	 * @param NULL
	 * @return conn
	 * @exception ClassNotFoundException,
	 *                SQLException On SQL connection error.
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			String url = "jdbc:sqlserver://localhost;databaseName=InternationalZooInstitute;integratedSecurity=true;";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url);
			if (conn != null) {
				DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
				System.out.println(dm.getDriverName());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
