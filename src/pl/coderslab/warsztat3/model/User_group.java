package pl.coderslab.warsztat3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User_group {

	private int id;
	private String name;

	public User_group(String name) {
		this.name = name;
	}

	public User_group() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	
	
	@Override
	public String toString() {
		return "User_group [id=" + id + ", name=" + name + "]";
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO user_group(name) VALUES (?);";
			String generatedColumns[] = { "ID" };
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.name);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
			ps.close();
			rs.close();
		} else {
			String sql = "UPDATE user_group SET name = ? WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.name);
			ps.setInt(2, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}

	public static User_group loadUser_groupByID(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM user_group WHERE id = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			User_group loadedGroup = new User_group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			ps.close();
			resultSet.close();
			return loadedGroup;
		}
		return null;
	}
	
	public static User_group[] loadAllUser_group (Connection conn) throws SQLException {
		ArrayList<User_group> allGroup = new ArrayList<>();
		String sql = "SELECT * FROM user_group;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		while (resultSet.next()) {
			User_group loadedGroup = new User_group();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			allGroup.add(loadedGroup);
	}
		ps.close();
		resultSet.close();
		User_group[] userGroup = new User_group[allGroup.size()];
		userGroup = allGroup.toArray(userGroup);
		return userGroup;
}
}
