package com.capstone.domain.userFile.service;

import com.capstone.domain.file.entity.File;
import com.capstone.domain.file.exception.FileNotFoundException;
import com.capstone.domain.file.respository.FileRepository;
import com.capstone.domain.file.service.FileService;
import com.capstone.domain.user.entity.User;
import com.capstone.domain.user.exception.UserNotFoundException;
import com.capstone.domain.user.repository.UserRepository;
import com.capstone.domain.user.service.UserService;
import com.capstone.domain.userFile.dto.UserFileRequest;
import com.capstone.domain.userFile.entity.UserFile;
import com.capstone.domain.userFile.mapper.UserFileMapper;
import com.capstone.domain.userFile.repository.UserFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserFileService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final UserFileRepository userFileRepository;

    private final FileService fileService;
    private final UserService userService;

    private final UserFileMapper userFileMapper;
    @Transactional
    public void updateProfile(UserFileRequest userFileRequest, Long uno) throws Exception {
        User user = this.userRepository.findByUno(uno).orElseThrow(() -> new UserNotFoundException());

        if(userFileRequest.getFiles()!=null) {
            MultipartFile[] files = userFileRequest.getFiles();


            if(!files[0].isEmpty()) {
                UserFile userFileDelete = this.userFileRepository.findByUnoAndCategory(uno, "main");
                if(userFileDelete != null) {
                    userFileRepository.delete(userFileDelete);
                }

                Long fno = this.fileService.userUploadFile(userFileRequest.getFiles()[0]);
                File file = this.fileRepository.findByFno(fno).orElseThrow(() -> new FileNotFoundException());
                UserFile userFile = userFileMapper.toEntity(file, user, "main");
                this.userFileRepository.save(userFile);
            }

            if(!files[1].isEmpty()) {
                UserFile userFileDelete = this.userFileRepository.findByUnoAndCategory(uno, "back");
                if(userFileDelete != null) {
                    userFileRepository.delete(userFileDelete);
                }
                Long fno = this.fileService.userUploadFile(userFileRequest.getFiles()[1]);
                File file = this.fileRepository.findByFno(fno).orElseThrow(() -> new FileNotFoundException());
                UserFile userFile = userFileMapper.toEntity(file, user, "back");
                this.userFileRepository.save(userFile);
            }
        }

        userService.userProfileUpdate(userFileRequest.getIntroduce(), user);


    }
}
