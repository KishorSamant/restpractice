package services;

import java.io.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;


@Path("/hello")
public class Service1 {
	 private static final String SERVER_UPLOAD_LOCATION_FOLDER = "D:\\Temp\\RESTPractice\\";

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			 @FormDataParam("file") InputStream fileInputStream,
			 @FormDataParam("file") FormDataContentDisposition contentDispositionHeader){
		String filePath = SERVER_UPLOAD_LOCATION_FOLDER + contentDispositionHeader.getFileName();
		        // save the file to the server
		        saveFile(fileInputStream, filePath);
		 
		        String output = "File saved to server location : " + filePath;
		        return Response.status(200).entity(output).build();

		
	}
	
	// save uploaded file to a defined location on the server
	    private void saveFile(InputStream uploadedInputStream,
	            String serverLocation) {
	        try {
	            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
	            int read = 0;
	            byte[] bytes = new byte[1024];
	 
	            outpuStream = new FileOutputStream(new File(serverLocation));
	            while ((read = uploadedInputStream.read(bytes)) != -1) {
	                outpuStream.write(bytes, 0, read);
	            }
	            outpuStream.flush();
	            outpuStream.close();
	        } catch (IOException e) {
	 
	            e.printStackTrace();
	        }
	    }

}
