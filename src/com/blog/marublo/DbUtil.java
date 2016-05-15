package com.blog.marublo;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DbUtil {

	public static void insertPointData(Point point){
		try (
			Connection connection = (Connection) DriverManager.getConnection(
            							SettingInitializer.getMySQL(),
            							SettingInitializer.getMySQLUser(),
            							SettingInitializer.getMySQLPass());

            Statement statement = (Statement) connection.createStatement();
        ) {

            String sql = "INSERT INTO POINT (name, point, date_info) VALUES ('" + point.getName() + "', '" + point.getPoint() + "', '" + point.getDate() +"');";
            int result = statement.executeUpdate(sql);
            //System.out.println("結果２：" + result);

        } catch (SQLException e) {

            e.printStackTrace();
        }
	}

	public static List<Point> selectPointData(String siteName){
		List<Point> pointList = new ArrayList<>();
		try (
				Connection connection = (Connection) DriverManager.getConnection(
	            							SettingInitializer.getMySQL(),
	            							SettingInitializer.getMySQLUser(),
	            							SettingInitializer.getMySQLPass());

	            Statement statement = (Statement) connection.createStatement();
	        ) {
				ResultSet rs =
						statement.executeQuery("select * from POINT WHERE name = '" + siteName + "' ORDER BY date_info asc;");

				while(rs.next()) {
					Point point = new Point();
					point.setName(rs.getString("name"));
					point.setPoint(rs.getString("point"));
					point.setDate(rs.getString("date_info"));
					pointList.add(point);
				}

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return pointList;
	}


}
