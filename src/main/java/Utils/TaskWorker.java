package Utils;

import java.io.IOException;
import com.cloudmersive.client.invoker.ApiException;
import Model.ClientHandlerBO;
import Model.TaskEntity;

public class TaskWorker extends Thread {
	enum ActionType {
		WordtoPDF
	}
    @Override
    public void run() {
        while (true) {
            try {
            	TaskEntity task = QueueManager.getTask();
                if(task != null)
                {
                	String result_RelativePathFile;
                	if (ActionType.WordtoPDF.name().equals(task.getType())) {
                		try {
							result_RelativePathFile = APIClient.convertWordToPDF(task.getInputData(), task.getResultAbolutePath(), task.getFolderSaveName());
							
							task.setStatus("Completed");
		                	task.setResult("Converted File Success!");
		                	task.setResultRelativePath(result_RelativePathFile);
		                	
		                	updateTaskToDatabase(task);
		                	System.out.println("Cập nhật kết quả vào database thành công");
						} catch (ApiException | IOException e) {
							e.printStackTrace();
						}
                	}             
                	else {
                		task.setResult("Unknown Task Type");
                		task.setStatus("Failed");
                	}                      
                	
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateTaskToDatabase(TaskEntity task) {
    	ClientHandlerBO.getInstance().updateTaskToDatabase(task);
    }
}


