package Model;

import java.sql.SQLException;
import java.util.*;

public class ClientHandlerBO {
	private DatabaseManager dbManager = new DatabaseManager();
	
	private static ClientHandlerBO instance;
	public static ClientHandlerBO getInstance() {
		if(instance == null) {
			instance = new ClientHandlerBO();
		}
		return instance;
	}
	private ClientHandlerBO() {
		
	}
	public boolean isValidUser(String _username, String _password) throws SQLException
	{
		return dbManager.isExistUser(_username, _password);
	}
	public boolean createAccount(String _username, String _email, String _password) throws SQLException
	{
		return dbManager.createAccount(_username, _email, _password);
	}
	public Integer getIDUser(String _username)
	{
		return dbManager.getIDUser(_username);
	}
	public List<Map<String , Object>> getTasksByUserId(Integer _userId)
	{
		return dbManager.getTaskByUserId(_userId);
	}
	public int saveNumberOfTasksForUser(int _userId, String _fileName, String _type, String _uploadFilePath) throws SQLException
	{
		return dbManager.saveNumberOfTasksForUser(_userId, _fileName , _type, _uploadFilePath);
	}
	public void updateTaskToDatabase(TaskEntity _taskEntity)
	{
		dbManager.updateTaskToDatabase(_taskEntity);
	}
	public String getResultRelativePathTask(int _taskId, int _userId)
	{
		return dbManager.getResultRelativePathTask(_taskId, _userId);
	}
}
