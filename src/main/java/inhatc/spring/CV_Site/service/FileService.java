package inhatc.spring.CV_Site.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws IOException { //파일 업로드

        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //확장자 찾기
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullPath = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullPath);

        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) { //파일 삭제
        File deleteFile = new File(filePath);
        if(deleteFile.exists()) {
            deleteFile.delete();
            log.info("============> " + filePath + " 파일 삭제 성공");
        } else {
            log.info("============> " + filePath + " 파일이 존재하지 않습니다.");
        }
    }
}
