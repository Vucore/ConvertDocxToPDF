package Model;

import java.util.*;
import java.sql.*;

public class DatabaseManager {
	private static final String URL = "jdbc:mysql://localhost:3306/ltm_apiexample";
    private static final String USER = "root";
    private static final String PASSWORD = "";
  
    public DatabaseManager()
    {
    	
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    public int saveNumberOfTasksForUser(int _userId, String _fileName, String _type, String _uploadedFileAbsolutePath) throws SQLException
    {
    	int result = 0;
    	Connection conn = null;
    	PreparedStatement ps = null;
    	try {
    		conn = getConnection();
    		String sql = "INSERT INTO tasks (user_id, file_name, type, input_data, status) VALUES (?, ?, ?, ?, ?)";
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, _userId);
    		ps.setString(2, _fileName);
    		ps.setString(3, _type);
    		ps.setString(4, _uploadedFileAbsolutePath);
    		ps.setString(5, "processing");
    		
    		result = ps.executeUpdate();
    	}catch (Exception e) {
    		e.printStackTrace();
			System.out.println("Lỗi lưu Task vào CSDL");
		}finally {
			if (ps != null) try { ps.close(); } catch (SQLException e) {}
	         if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
    	return result;    		      
    }

    public boolean isExistUser(String _username, String _password) throws SQLException
    {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
    	try{
			conn = getConnection();
    		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, _username);
			ps.setString(2, _password);
			rs = ps.executeQuery();
			return rs.next();
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Có lỗi khi kiểm tra User");
		}finally {
			if (rs != null) try { rs.close(); } catch (SQLException e) {}
			if (ps != null) try { ps.close(); } catch (SQLException e) {}
			if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		System.out.println("Không tìm thấy người dùng");
    	return false;   	
    }
    public boolean createAccount(String _username, String _email, String _password) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, _username);
            ps.setString(2, _email);
            ps.setString(3, _password);

            int rowsInserted = ps.executeUpdate(); 
            return rowsInserted > 0; 
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("Lỗi khi tạo tài khoản");
        } finally {
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
        return false;
    }
    public Integer getIDUser(String _username)
    {
    	try(Connection conn = getConnection()){
			String sql = "SELECT id FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, _username);
			ResultSet rs = ps.executeQuery();
			 if (rs.next()) {
	                return rs.getInt("id"); 
	            }
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Có lỗi khi tìm ID User");
		}
		System.out.println("Không tìm thấy người dùng hoặc có lỗi");
    	return null;   
    }
    
    public List<Map<String, Object>> getTaskByUserId(int _userId){
    	List<Map<String, Object>> tasks = new ArrayList<Map<String,Object>>();
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	String sql = "SELECT * FROM tasks WHERE user_id = ?";
    	try {
    		conn = getConnection();
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, _userId);
    		rs = ps.executeQuery();
    		while(rs.next())
    		{
    			Map<String, Object> task = new HashMap<String, Object>();
    			task.put("id", rs.getInt("id"));
    			task.put("file_name", rs.getString("file_name"));
    			task.put("type", rs.getString("type"));
    			task.put("status", rs.getString("status"));
    			task.put("created_at", rs.getString("created_at"));
    			task.put("updated_at", rs.getString("updated_at"));
    			tasks.add(task);
    		}
    	}catch (Exception e) {
    		System.out.println("Lỗi khi lấy Task của User");
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch (SQLException e) {}
	         if (ps != null) try { ps.close(); } catch (SQLException e) {}
	         if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		return tasks;
    }
    public void updateTaskToDatabase(TaskEntity _taskEntity)
    {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	try {
    		conn = getConnection();
    		String sql = "UPDATE tasks SET status = ?, result = ?, resultRelative_path = ? WHERE user_id = ? AND input_data = ?";
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, _taskEntity.getStatus());  
            ps.setString(2, _taskEntity.getResult());   
            ps.setString(3, _taskEntity.getResultRelativePath());
            ps.setInt(4, _taskEntity.getUserId());         
            ps.setString(5, _taskEntity.getInputData());
            ps.executeUpdate();
    	}catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Lỗi khi cập nhật Task vào CSDL");
		}finally {
            if (ps != null) try { ps.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }
    public String getResultRelativePathTask(int _taskId, int _userId)
    {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
    		conn = getConnection();
    		String sql = "SELECT resultRelative_path FROM tasks WHERE id = ? AND user_id = ?";
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, _taskId);
    		ps.setInt(2, _userId);
    		rs = ps.executeQuery();
    		if(rs.next())
    		{
    			return rs.getString("resultRelative_path");
    		}
    	}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Lỗi khi lấy đường dẫn tương đối kết quả");
		}finally {
			if (rs != null) try { rs.close(); } catch (SQLException e) {}
	         if (ps != null) try { ps.close(); } catch (SQLException e) {}
	         if (conn != null) try { conn.close(); } catch (SQLException e) {}
		}
		return null;
    }
}
