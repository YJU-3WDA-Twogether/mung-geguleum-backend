package com.capstone.global.aws.service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class S3Service {

    // service
    private final AmazonS3 amazonS3;

    private String useOnlyOneFileName;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String location;
    
    private  GeneratePresignedUrlRequest generatePresignedUrlRequest;

    //파일다운로드
//    public ResponseEntity<byte[]> getObject(String storedFileName) throws IOException {
//        S3Object o = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));
//        S3ObjectInputStream objectInputStream = ((S3Object) o).getObjectContent();
//        byte[] bytes = IOUtils.toByteArray(objectInputStream);
// 
//        String fileName = URLEncoder.encode(storedFileName, "UTF-8").replaceAll("\\+", "%20");
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        httpHeaders.setContentLength(bytes.length);
//        httpHeaders.setContentDispositionFormData("attachment", fileName);
// 
//        return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
// 
//    }
    
    //파일 업로드전 키와 filename 발급 메소드
    public Map<String, Object> getPreSignedUrl(Long size) {
    	  Map<String, Object> result = new HashMap<>();
    	 
    	  List<Serializable> list = new ArrayList<>();
    	  if(size==0) {
    		  return result;
    	  }
    	
    	  for(int i = 0 ; i <size ; i++) {
	        String onlyOneFileName = onlyOneFileName();
	
	        useOnlyOneFileName = onlyOneFileName;
	 
	        generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, onlyOneFileName);
	        list.add(useOnlyOneFileName);
	        System.out.println(onlyOneFileName);
    	  }
    	  result.put("encodedFileName", list);
    	  result.put("preSignedUrl", amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString());

        return result;
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 10;
        expiration.setTime(expTimeMillis);
        log.info(expiration.toString());
        return expiration;
    }

    private String onlyOneFileName(){
        return UUID.randomUUID().toString();

    }

    public String findByName(String path) {
        log.info("Generating signed URL for file name {}", useOnlyOneFileName);
        return "https://"+bucket+".s3."+location+".amazonaws.com/"+path+"/"+useOnlyOneFileName;
    }
}
