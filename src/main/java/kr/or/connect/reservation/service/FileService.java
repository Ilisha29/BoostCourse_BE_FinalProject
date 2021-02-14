package kr.or.connect.reservation.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	int postFile(MultipartFile file, String create_date);
}
