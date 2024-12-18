package Utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.cloudmersive.client.ConvertDocumentApi;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;

public class APIClient {
    public static String convertWordToPDF(String _fileAbsolutePath, String _resultAbsolutePath, String _folderSaveName) throws ApiException, FileNotFoundException, IOException {
    	ApiClient client = Configuration.getDefaultApiClient();
        ApiKeyAuth apiKey = (ApiKeyAuth) client.getAuthentication("Apikey");
        apiKey.setApiKey("f759286b-df2f-4c6c-96c2-cd169147877e");  

        ConvertDocumentApi apiInstance = new ConvertDocumentApi();
        File inputFile = new File(_fileAbsolutePath);
        byte[] result = apiInstance.convertDocumentDocxToPdf(inputFile);

        System.out.println("Tên Folder lưu FILE Convert: " + _folderSaveName);
        System.out.println("Địa chỉ đường dẫn lưu file Convert tuyệt đối: " + _resultAbsolutePath);
        String outputFileName = inputFile.getName().replace(".docx", ".pdf");
        File outputFile = new File(_resultAbsolutePath + outputFileName);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(result);
        }
        
        return _folderSaveName + "/" + outputFileName;
    }
}