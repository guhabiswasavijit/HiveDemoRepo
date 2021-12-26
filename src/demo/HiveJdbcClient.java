package demo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.cloudera.hive.jdbc.HS2DataSource;


public class HiveJdbcClient {
	 
	public static void main(String[] args) throws Exception{
		    Connection connection = null;
		    HS2DataSource ds = new com.cloudera.hive.jdbc.HS2DataSource();
		    ds.setURL("jdbc:hive2://127.0.0.1:10000/default;AuthMech=0;transportMode=binary;");
		    connection = ds.getConnection();
		    Statement stmt = connection.createStatement();
		    String tableName = "testHiveDriverTable";
		    stmt.executeQuery("drop table " + tableName);
		    ResultSet res = stmt.executeQuery("create table " + tableName + " (key int, value string)");
		    // show tables
		    String sql = "show tables '" + tableName + "'";
		    System.out.println("Running: " + sql);
		    res = stmt.executeQuery(sql);
		    if (res.next()) {
		      System.out.println(res.getString(1));
		    }
		    // describe table
		    sql = "describe " + tableName;
		    System.out.println("Running: " + sql);
		    res = stmt.executeQuery(sql);
		    while (res.next()) {
		      System.out.println(res.getString(1) + "\t" + res.getString(2));
		    }
		 
		    // load data into table
		    // NOTE: filepath has to be local to the hive server
		    // NOTE: /tmp/a.txt is a ctrl-A separated file with two fields per line
		    String filepath = "C:\\SampleData\\employees.csv";
		    sql = "load data local inpath '" + filepath + "' into table " + tableName;
		    System.out.println("Running: " + sql);
		    res = stmt.executeQuery(sql);
		 
		    // select * query
		    sql = "select * from " + tableName;
		    System.out.println("Running: " + sql);
		    res = stmt.executeQuery(sql);
		    while (res.next()) {
		      System.out.println(String.valueOf(res.getInt(1)) + "\t" + res.getString(2));
		    }
		 
		    // regular hive query
		    sql = "select count(1) from " + tableName;
		    System.out.println("Running: " + sql);
		    res = stmt.executeQuery(sql);
		    while (res.next()) {
		      System.out.println(res.getString(1));
		    }
		  }

	}


