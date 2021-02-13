package kr.or.connect.reservation.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
	// get방식으로 요청이 올 경우 업로드 폼을 보여줍니다.
	@GetMapping("/uploadform")
	public String uploadform() {
		return "uploadform";
	}

	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {

		System.out.println("파일 이름 : " + file.getOriginalFilename());
		System.out.println("파일 크기 : " + file.getSize());

		try (
				// 맥일 경우
				FileOutputStream fos = new FileOutputStream("/tmp/" + file.getOriginalFilename());
				// 윈도우일 경우
				// FileOutputStream fos = new FileOutputStream("c:/tmp/" +
				// file.getOriginalFilename());
				InputStream is = file.getInputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");
		}
		return "uploadok";
	}
	
	@GetMapping("/download")
	public void download(HttpServletResponse response) {
        // 직접 파일 정보를 변수에 저장해 놨지만, 이 부분이 db에서 읽어왔다고 가정한다.
		String fileName = "intelliJ shortcut.jpg";
		String saveFileName = "/tmp/intelliJ shortcut.jpg"; // 맥일 경우 "/tmp/connect.png" 로 수정
		String contentType = "image/jpg";
		int fileLength = 961399; // connect.png 파일의 크기와 같아야 합니다. 파일의 크기를 꼭 확인해주세요.
                //파일의 크기와 같지 않을 경우 프로그램이 멈추지 않고 계속 실행되거나, 잘못된 정보가 다운로드 될 수 있습니다.
                // 위의 파일크기를 구하는 부분은 다음과 같은 방식으로 수정되는 것이 좋습니다. 코드를 간단하게 구현하기 위해서 직접 length를 입력하였습니다.
                // File file = new File(saveFileName);
                // long fileLength = file.length();
		
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Length", "" + fileLength);
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        
        try(
                FileInputStream fis = new FileInputStream(saveFileName);
                OutputStream out = response.getOutputStream();
        ){
        	    int readCount = 0;
        	    byte[] buffer = new byte[1024];
            while((readCount = fis.read(buffer)) != -1){
            		out.write(buffer,0,readCount);
            }
        }catch(Exception ex){
            throw new RuntimeException("file Save Error");
        }
	}
}
