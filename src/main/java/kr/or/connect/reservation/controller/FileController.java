package kr.or.connect.reservation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.service.FileService;

@Controller
@RequestMapping(path = "/api")
public class FileController {
	private FileService fileService;

	FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@ApiOperation(value = "Get Image")
	@ApiResponses({ @ApiResponse(code = 200, message = "Success Get Image"),
			@ApiResponse(code = 500, message = "Reservation Get Exception!!~~!!") })
	@GetMapping(path = "/files/{fileId}")
	public void getImage(@PathVariable(name = "fileId") int fileId, HttpServletResponse response) {
		FileInfo fileInfo = fileService.getFileInfo(fileId);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileInfo.getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", fileInfo.getContentType());
		response.setHeader("Content-Length", "" + new File(fileInfo.getSaveFileName()).length());
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		try (FileInputStream fis = new FileInputStream(fileInfo.getSaveFileName());
				OutputStream out = response.getOutputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = fis.read(buffer)) != -1) {
				out.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");
		}
	}
}
