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
	
	public static void insertBmonData(Lesson lesson){
		try (
			Connection connection = (Connection) DriverManager.getConnection(
									"jdbc:mysql://133.242.235.62:3306/CS_FEELCYCLE",
            							SettingInitializer.getMySQLUser(),
            							SettingInitializer.getMySQLPass());

            Statement statement = (Statement) connection.createStatement();
        ) {

            String sql = "INSERT INTO BMON_LESSON (LESSON_MASHINE, LESSON_TENPO, LESSON_TIME_TO,LESSON_TIME_FROM, LESSON_DATE, INSTRUCTOR, LESSON_NAME,USER_ID) "
            		+ "VALUES ('" + lesson.getLessonMashine() + "', '" + lesson.getLessonTenpo() + "', '"
            		+ lesson.getLessonTimeTo() +  "', '" + lesson.getLessonTimeFrom() + "', '"
    				+ lesson.getLessonDate() +  "', '" + lesson.getLessonInstructor() + "', '"
    				+ lesson.getLessonName() +  "', '" + "yanagisawa.trade@gmail.com"+ "');";
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
						statement.executeQuery("select * from POINT WHERE name = '" + siteName + "' ORDER BY id desc;");

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

	public static Lesson getBmonLessonDate() {
		Lesson lesson = new Lesson();
		try (
				Connection connection = (Connection) DriverManager.getConnection(
										"jdbc:mysql://133.242.235.62:3306/CS_FEELCYCLE",
	            							SettingInitializer.getMySQLUser(),
	            							SettingInitializer.getMySQLPass());

	            Statement statement = (Statement) connection.createStatement();
	        ) {
				ResultSet rs =
						statement.executeQuery("select * from BMON_LESSON ORDER BY ID desc LIMIT 1;");

				while(rs.next()) {
					lesson.setLessonMashine(rs.getString("LESSON_MASHINE"));
					lesson.setLessonTenpo(rs.getString("LESSON_TENPO"));
					lesson.setLessonTimeTo(rs.getString("LESSON_TIME_TO"));
					lesson.setLessonTimeFrom(rs.getString("LESSON_TIME_FROM"));
					lesson.setLessonDate(rs.getString("LESSON_DATE"));
					lesson.setLessonInstructor(rs.getString("INSTRUCTOR"));
					lesson.setLessonName(rs.getString("LESSON_NAME"));
					lesson.setLessonUserId(rs.getString("USER_ID"));
				}

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return lesson;
	}

	public static int countBmonLesson() {
		String count = "";

		try (
				Connection connection = (Connection) DriverManager.getConnection(
										"jdbc:mysql://133.242.235.62:3306/CS_FEELCYCLE",
	            							SettingInitializer.getMySQLUser(),
	            							SettingInitializer.getMySQLPass());

	            Statement statement = (Statement) connection.createStatement();
	        ) {
				ResultSet rs =
						statement.executeQuery("select COUNT(*) from BMON_LESSON;");
				while(rs.next()) {
					count = rs.getString(1);
					System.out.println(count.toString());
				}

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return Integer.parseInt(count);
	}


}
