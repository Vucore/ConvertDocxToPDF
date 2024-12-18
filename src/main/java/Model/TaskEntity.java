package Model;

public class TaskEntity {
    private int userId;
    private String type; // Loại tác vụ: PDF->Word, Đạo văn
    private String inputData;
    private String status;
    private String result;
    private String result_AbsolutePath;
    private String folderSaveName;
    private String result_RelativePath;

    public TaskEntity(int _userId, String _type, String _inputData, String _resultAbsolutePath, String _folderSaveName) {
        this.userId = _userId;
        this.type = _type;
        this.inputData = _inputData;
        this.result_AbsolutePath = _resultAbsolutePath;
        this.folderSaveName = _folderSaveName;
    }
    public int getUserId() {
        return userId;
    }
    public String getType() {
    	return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String _status) {
        this.status = _status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String _result) {
        this.result = _result;
    }

	public String getInputData() {
		return inputData;
	}

	public String getResultAbolutePath()
	{
		return result_AbsolutePath;
	}
	
	public String getFolderSaveName()
	{
		return folderSaveName;
	}
	public String getResultRelativePath() {
		return result_RelativePath;
	}
	public void setResultRelativePath(String _result_RelativePath) {
		this.result_RelativePath = _result_RelativePath;
	}

}
