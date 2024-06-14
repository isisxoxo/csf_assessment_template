package ibf2023.csf.backend.services;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.models.UploadInfo;
import ibf2023.csf.backend.repositories.ImageRepository;
import ibf2023.csf.backend.repositories.PictureRepository;

@Service
public class PictureService {

	@Autowired
	private PictureRepository pictureRepo;

	@Autowired
	private ImageRepository imageRepo;

	// TODO Task 5.1
	// You may change the method signature by adding parameters and/or the return
	// type
	// You may throw any exception
	public String save(String title, String comments, String date, MultipartFile picture)
			throws IOException, ParseException {
		UploadInfo uploadInfo = imageRepo.save(picture, title);
		return pictureRepo.save(title, comments, date, uploadInfo);
	}

	public Long getTotalFileSize() {
		return pictureRepo.getTotalFileSize();
	}
}
