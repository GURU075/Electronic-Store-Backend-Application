package com.guru.electronic.strore.serviceImpls;

import com.guru.electronic.strore.exceptions.BadApiRequestException;
import com.guru.electronic.strore.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();
        logger.info("FileName :{}",originalFilename);
        String fileName = UUID.randomUUID().toString();
        String extension =originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension=fileName+extension;
        String fullPathWithFileName =path+fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg")){
                   File folder = new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }

            //upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
        }else{
            throw new BadApiRequestException("File with "+extension+"this extension not allowed!!");
        }
        return fileNameWithExtension;
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath=path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }


}
