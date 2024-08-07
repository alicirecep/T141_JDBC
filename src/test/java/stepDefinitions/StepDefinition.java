package stepDefinitions;

import io.cucumber.java.en.Given;
import manage.QueryManage;
import utilities.ConfigReader;
import utilities.JDBCReusableMethods;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StepDefinition {

	Connection connection;
	Statement statement;
	ResultSet resultSet;
	String Query;

	QueryManage queryManage = new QueryManage();

	@Given("Database baglantisini kurar.")
	public void database_baglantisini_kurar() throws SQLException {
		connection = DriverManager.getConnection(ConfigReader.getProperty("URL"),
				ConfigReader.getProperty("USERNAME"),ConfigReader.getProperty("PASSWORD"));
	}
	@Given("Query01 hazirlar")
	public void query01_hazirlar() throws SQLException {

		// Database içindeki "deposits" toblosunda
		// "amount" değeri 100$ ile 500$ arasında olan user_id’leri doğrulayınız

		String Query = "SELECT user_id FROM u168183796_qaloantec.deposits WHERE amount BETWEEN 100 AND 500";

		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet =  statement.executeQuery(Query);

	}
	@Given("Query01 sonuclarini isler")
	public void query01_sonuclarini_isler() throws SQLException {

		resultSet.next();

	int expectedID = 1;
	int actualID= resultSet.getInt("user_id");

		assertEquals(expectedID,actualID);

		System.out.println("ResultSet'ten gelen user_id:    "+resultSet.getInt("user_id"));

	}
	@Given("Database baglantisini kapatir.")
	public void database_baglantisini_kapatir() throws SQLException {

		resultSet.close();
		statement.close();
		connection.close();
	}

// --- Query02 ---

	@Given("Query02 hazirlar.")
	public void query02_hazirlar() {

	 Query = queryManage.getQuery02();

	}
	@Given("Query02 sonuclarini isler.")
	public void query02_sonuclarini_isler() throws SQLException {

	/*
	5 Minutes
    10 Minutes
	 */

		statement =connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		resultSet = statement.executeQuery(Query);

		List<String> expectedResultList = new ArrayList<>();
		expectedResultList.add("5 Minutes");
		expectedResultList.add("10 Minutes");

		List<String> actualResultList = new ArrayList<>();

		while(resultSet.next()){
			String name = resultSet.getString("name");
			actualResultList.add(name);

		}

		for (int i = 0; i < actualResultList.size(); i++) {
			assertEquals(expectedResultList.get(i),actualResultList.get(i));
			System.out.println(i+" . index dogrulandi");
		}
	}

		// **** QUERY03 ****

	@Given("Database connection kurar.")
	public void database_connection_kurar() {
		JDBCReusableMethods.getConnection();

	}


	@Given("Query03 hazirlar.")
	public void query03_hazirlar() {

		Query = queryManage.getQuery03();

	}
	@Given("Query03 sonuclarini isler.")
	public void query03_sonuclarini_isler() throws SQLException {

		String expectedFirstname = "Test";

		resultSet = JDBCReusableMethods.executeQuery(Query);

		resultSet.next();
		String actualFirstname = resultSet.getString("firstname");

		assertEquals(expectedFirstname,actualFirstname);

	}

	@Given("Database connection kapatir.")
	public void database_connection_kapatir() {
		JDBCReusableMethods.closeConnection();
	}


}