package com.koreait.sboard.common;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component	// 빈 등록을 했기 때문에 주소값을 달라고 하면 된다!!
public class FileUtils {
	
	@Autowired	// 주소값을 자동으로 매핑 시켜줄 것이다.
	private ServletContext ctx;	// application 객체 의미한다.
	
	// Base 경로 리턴.
	public String getBasePath(String more) {
		return ctx.getRealPath(more);
	}
	
	// 경로 폴더 만들기.
	public void makeDirectories(String path) {
		File file = new File(path);
		
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	// 확장자 리턴.
	public String getExt(String fileNm) {
//		return FilenameUtils.getExtension(fileNm);
		return fileNm.substring(fileNm.lastIndexOf(".") + 1);
	}
	
	public String getRandomFileNm() {
		return UUID.randomUUID().toString();	// 파일 이름 중복 막음.
	}
	
	public String getRandomFileNm(String originalFileNm) {
		return getRandomFileNm() + "." + getExt(originalFileNm);
	}
	
	public String saveFile(MultipartFile mf, String folder) {
		String basePath = getBasePath(folder);
		makeDirectories(basePath);
		
		String fileNm = getRandomFileNm(mf.getOriginalFilename());	// fileNm : DB에 넣어줘야 하는 값.
		
		try {
			// 파일이 저장되어야 할 위치정보.
			File file = new File(basePath, fileNm);	// 굳이 /를 적지 않아도, 자동으로 /를 넣어준다.
			mf.transferTo(file);
			
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		return fileNm;
	}
	
	public boolean delFile(String path) {
		String basePath = getBasePath("/resources");
		File file = new File(basePath, path);
		
		if(file.exists()) {
			 return file.delete();
		}
		return false;
	}
}
