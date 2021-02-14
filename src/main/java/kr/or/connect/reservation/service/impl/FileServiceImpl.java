package kr.or.connect.reservation.service.impl;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.reservation.dao.FileDao;
import kr.or.connect.reservation.dto.FileInfo;
import kr.or.connect.reservation.service.FileService;

@Service
public class FileServiceImpl implements FileService {
	private static final String FILE_DIRECTORY_PATH = "/Users/minskim/Desktop/upload/";
	private FileDao fileDao;
	
	FileServiceImpl(FileDao fileDao){
		this.fileDao = fileDao;
	}
	
	@Override
	@Transactional
	public int postFile(MultipartFile file, String create_date) {
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(file.getOriginalFilename());
		fileInfo.setSaveFileName(FILE_DIRECTORY_PATH+file.getOriginalFilename());
		fileInfo.setContentType(file.getContentType());
		try (FileOutputStream fos = new FileOutputStream(FILE_DIRECTORY_PATH + file.getOriginalFilename());
				InputStream is = file.getInputStream();) {
			int readCount = 0;
			byte[] buffer = new byte[1024];
			while ((readCount = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
		} catch (Exception ex) {
			throw new RuntimeException("file Save Error");
		}
		fileDao.postFile(fileInfo, create_date);	
		return fileDao.getFileInfoId(file.getOriginalFilename(),create_date);
	}

	@Override
	public FileInfo getFileInfo(int fileId) {
		return fileDao.getFileInfo(fileId);
	}
	
}
