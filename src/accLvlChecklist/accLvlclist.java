package accLvlChecklist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

public class accLvlclist {

	public static void run_query(String s, String port) {
		try{
//			FileWriter fw = new FileWriter("C:\\Users\\shashank.maurya\\eclipse-workspace\\accLvlChecklist\\src\\queryresult.txt");
           // Class.forName("com.mysql.jdbc.Driver");
			String link="jdbc:mysql://localhost:$port/".replace("$port", port);
            Connection con= DriverManager.getConnection(link,"shashank.maurya","8#tTgPET7");
            Statement stmt=con.createStatement();
            // ResultSet rs=stmt.executeQuery("SELECT * FROM dbrouter.map_account_product WHERE fk_account_id=70165");
            System.out.println(s+"\n");
            ResultSet rs=stmt.executeQuery(s);
            int n=rs.getMetaData().getColumnCount();
//            System.out.println(n);
            for(int i=1;i<=n;i++)
            	System.out.print(rs.getMetaData().getColumnName(i)+"\t");
            System.out.println();
            while(rs.next()) {
//            	System.out.println(rs.getString(1));
                // System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
                for(int i=1;i<=n;i++) {
//                	fw.write(rs.getString(i)+"\t");
                	System.out.print(rs.getString(i)+"\t\t\t");
                }
//                fw.write("\n");
                System.out.println();
//            	System.out.println(rs.getString(1));
            }
//            fw.close();
            con.close();
        }catch(Exception e){ 
        	e.printStackTrace();
        }
	}
	public static String replace_all(String query, String schema, String account_id, String product_id) throws IOException{
//		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//		System.out.print("Schema: ");
//		String schema=br.readLine();
//		System.out.print("account_id: ");
//		String account_id=br.readLine();
//		System.out.println(query);
		query=query.replace("$schema", schema);
		query=query.replace("$account_id", account_id);
		query=query.replace("$product_id", product_id);
//		System.out.println(query);
		return query;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//		System.out.print("Port: ");
//		String p=br.readLine();
//		System.out.print("Schema: ");
//		String schema=br.readLine();
//		System.out.print("account_id: ");
//		String account_id=br.readLine();
//		System.out.print("product_id: ");
//		String product_id=br.readLine();
		
		String port_screw="3005"; //plat: 2020, gold: 3005
		String port_db="3006";
		String schema="rayalcanindb";
		String account_id="70634";
		String product_id="8";
		
		int c=0;
		try {
			File obj = new File("C:\\Users\\shashank.maurya\\eclipse-workspace\\accLvlChecklist\\src\\querymastersheet.txt");
			Scanner reader  = new Scanner(obj);
			while(reader.hasNextLine()) {
//				System.out.println(reader.nextLine()+"\n\n");
				String q=reader.nextLine();
				if(c++%2==0) {
					System.out.println("\n"+q);
					continue;
				}
				q=replace_all(q,schema, account_id,product_id);
//				System.out.println(q);
				run_query(q,(q.contains("dbrouter.") || q.contains("analytics."))?port_screw:port_db); 
//				run_query("SELECT MAX(chart_access_history.update_time), fk_chart_id FROM analytics.chart_access_history JOIN analytics.chart ON fk_chart_id=pk_chart_id WHERE fk_account_id=70165 AND fk_product_id=10 AND chart_access_history.create_user NOT LIKE \"%highradius%\" GROUP BY fk_chart_id;");
			}
			reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
}
