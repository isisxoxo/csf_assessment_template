package ibf2023.csf.backend.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.services.PictureService;
import jakarta.json.Json;

// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path = "/api")
public class UploadController {

	@Autowired
	private PictureService pictureService;

	// TODO Task 5.2
	// You may change the method signature by adding additional parameters and
	// annotations.
	// You cannot remove any any existing annotations and parameters from
	// postUpload()
	@PostMapping(path = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postUpload(
			@RequestPart String title,
			@RequestPart String comments,
			@RequestPart String date,
			@RequestPart MultipartFile picture) {

		System.out.println(">>>> TITLE: " + title); // TO REMOVE
		System.out.println(">>>> COMMENTS: " + comments); // TO REMOVE
		System.out.println(">>>> DATE: " + date); // TO REMOVE - DATE: 2024-06-14T03:30:12.927Z
		System.out.println(">>>> PICTURE: " + picture.getOriginalFilename()); // TO REMOVE

		// GET FILE SIZE
		pictureService.getTotalFileSize();

		// SAVE TO MONGO AND DO
		// try {
		// pictureService.save(title, comments, date, picture); // Saved as bytes
		// } catch (IOException | ParseException e) {
		// e.printStackTrace();
		// }

		// // TO ADD ON OTHERS
		return ResponseEntity.ok(
				Json.createObjectBuilder().build().toString());
	}

	// @PostMapping(path="/image/upload", consumes =
	// MediaType.MULTIPART_FORM_DATA_VALUE)
	// public ResponseEntity<String> postUpload(@RequestPart MultipartFile picture)
	// {

	// return ResponseEntity.ok(
	// Json.createObjectBuilder().build().toString()
	// );
	// }
}
