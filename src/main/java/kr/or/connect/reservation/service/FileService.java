package kr.or.connect.reservation.service;

import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.dto.FileInfo;

public interface FileService {
	int postFile(MultipartFile file, String create_date);

	FileInfo getFileInfo(int fileId);
}
