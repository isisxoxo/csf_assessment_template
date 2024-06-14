package ibf2023.csf.backend.repositories;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import ibf2023.csf.backend.models.UploadInfo;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3;

	// TODO Task 4.1
	// You may change the method signature by adding parameters and/or the return
	// type
	// You may throw any exception
	public UploadInfo save(MultipartFile file, String title) throws IOException {

		// User metadata (String)
		Map<String, String> userData = new HashMap<>();
		userData.put("upload-timestamp", (new Date()).toString());
		userData.put("name", title);
		userData.put("filename", file.getOriginalFilename());

		// Metadata for file
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());
		metadata.setUserMetadata(userData);

		String key = UUID.randomUUID().toString().substring(0, 8);

		// Bucket name (OceanDigital), key, input stream, metadata
		PutObjectRequest putReq = new PutObjectRequest("csf-assessment-isis", key, file.getInputStream(), metadata);

		// Make the object publicly available
		putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

		// PUT THE OBJECT
		s3.putObject(putReq);

		// Digital Ocean URL of the image uploaded
		UploadInfo uploadInfo = new UploadInfo(file.getSize(), s3.getUrl("csf-assessment-isis", key).toString());
		return uploadInfo;
	}
}
