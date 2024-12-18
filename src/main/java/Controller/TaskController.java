package Controller;

import Model.ClientHandlerBO;
import Model.TaskEntity;
import Utils.QueueManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.List;
import java.io.File;
import java.io.IOException;

@WebServlet("/task")
public class TaskController extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     if (!ServletFileUpload.isMultipartContent(request)) {
	         response.getWriter().write("Error: Form must include enctype=multipart/form-data.");
	         return;
	     }
	     
	     int userId = (Integer) request.getSession().getAttribute("userId");
	     DiskFileItemFactory factory = new DiskFileItemFactory();
	     ServletFileUpload upload = new ServletFileUpload(factory);

	     try {
	    	 List<FileItem> items = upload.parseRequest(request);
	    	 String folderConvertedName = (String) request.getSession().getAttribute("repoUser");
	         File uploadedFile = null;
	         String type = null;
	      
	         for (FileItem item : items) { 
	        	 if (item.isFormField()) {
	                  if ("type".equals(item.getFieldName())) {
	                      type = item.getString(); 
	                  }
	        	 }
	        	 if(!item.isFormField())
	        	 {	        		 
	        		 try {
	        			 System.out.println(type);
	        			 String fileName = item.getName();
	        			 
	        			 String uploadDir = getServletContext().getRealPath("/") + "Uploads/";
	        			 System.out.println("Địa chỉ lưu file chưa chuyển đổi (UploadDir): " + uploadDir);
	        			 String convertDir = getServletContext().getRealPath("/") + "/" + folderConvertedName + "/";
	        			 System.out.println("Địa chỉ lưu file đã chuyển đổi (ConvertDir): " + convertDir);
	        			 
	        			 File inputDir = new File(uploadDir);
	        			 if (!inputDir.exists()) 
	        				 inputDir.mkdirs(); 
	        			 File outputDir = new File(convertDir);
	        			 if(!outputDir.exists()) 
	        				 outputDir.mkdir();
	        			 
	        			 uploadedFile = new File(uploadDir + fileName);
	        			 item.write(uploadedFile); 
	        			 
	        			 
	        			 if (type == null || uploadedFile == null) {
	        				 response.getWriter().write("Error: Missing type or file data.");
	        				 return;
	        			 }
	        			 
	        			 int countTask = ClientHandlerBO.getInstance().saveNumberOfTasksForUser(userId, fileName, type, uploadedFile.getAbsolutePath());
	        			 if (countTask > 0) {
	        				 TaskEntity task = new TaskEntity(userId, type, uploadedFile.getAbsolutePath(), convertDir, folderConvertedName);
	        				 QueueManager.addTask(task);
	        				 
	        			 } else {
	        				 response.getWriter().write("Error: Could not add task to the database.");
	        			 }        			 
	        		 }catch (Exception e) {
						e.printStackTrace();
					}
	        	 }	             
	         }
	         response.sendRedirect("view/dashboard.jsp");

	     } catch (Exception e) {
	         e.printStackTrace();
	         response.getWriter().write("Error: " + e.getMessage());
	     }
	 }

}
