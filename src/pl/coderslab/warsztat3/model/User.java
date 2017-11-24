package pl.coderslab.warsztat3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {

	private long id;
	private String username;
	private String email;
	private String password;
	private int userGroupId;

	public User(String username, String email, String password) {
		super();
		this.id = 0l;
		this.username = username;
		this.email = email;
		setPassword(password);
		this.userGroupId = 1;
	}

	public User() {
		super();
		this.id = 0l;
		this.username = "";
		this.email = "";
		this.password = "";
		this.userGroupId = 0;
	}
	
	

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", userGroupId=" + userGroupId + "]" + "\n";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}

	public long getId() {
		return id;
	}

	public void save(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO users(username, email, password, user_group_id) " + "VALUES(?, ?, ?, ?);";
			String[] generatedColumns = { "ID" };
			PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4,userGroupId);
			ps.executeUpdate();
			ResultSet gk = ps.getGeneratedKeys();
			if (gk.next()) {
				this.id = gk.getLong(1);
			}
			gk.close();
			ps.close();
		} else {
			String sql = "UPDATE users SET username = ?, email = ?, password = ?, "
					+ "user_group_id = ? WHERE id = ?; ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.username);
			ps.setString(2, this.email);
			ps.setString(3, this.password);
			ps.setInt(4, 1); //userGroupId); zamiast "1"
			ps.setLong(5, this.id);
			ps.executeUpdate();
			ps.close();
		}
	}

	public static User loadUserById(Connection conn, long id) throws SQLException {
		String sql = "SELECT * FROM users WHERE id =?;"; // pobierze użytkownika
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		ResultSet resultSet = ps.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getLong("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			ps.close();
			resultSet.close();
			return loadedUser;
		}
		return null;
	}
	
	public static User[] loadAllUser (Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM users;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet resultSet = ps.executeQuery();
		while(resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			users.add(loadedUser);
		}
		ps.close();
		resultSet.close();

		User [] userArray = new User[users.size()];
		userArray = users.toArray(userArray);
		return userArray;
		
	}
	
	public void delete(Connection conn) throws SQLException {
		if(this.id != 0) {
			String sql = "DELETE FROM users WHERE id = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, this.id);
			ps.executeUpdate();
			this.id = 0;
		}
	}
	
}
