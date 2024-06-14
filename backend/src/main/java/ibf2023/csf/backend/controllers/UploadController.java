package ibf2023.csf.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import ibf2023.csf.backend.services.PictureService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

// You can add addtional methods and annotations to this controller. 
// You cannot remove any existing annotations or methods from UploadController
@Controller
@RequestMapping(path = "/api")
public class UploadController {

	@Autowired
	private PictureService pictureService;

	@Value("${preconfigured.threshold}")
	private String threshold;

	@Value("${preconfigured.threshold.unit}")
	private String thresholdUnit;

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

		// GET TOTAL FILE SIZE
		Long totalFileSizeBytes = pictureService.getTotalFileSize();

		// COMPARE WITH THRESHOLD
		Long thresholdLong = Long.parseLong(threshold);
		String thresholdUnitUc = thresholdUnit.toUpperCase();
		Long thresholdBytes;

		if (thresholdUnitUc.equals("MB")) {
			thresholdBytes = thresholdLong * 1000000;
		} else if (thresholdUnitUc.equals("GB")) {
			thresholdBytes = thresholdLong * 1000000 * 1000;
		} else {
			thresholdBytes = thresholdLong;
		}

		JsonObject payload;

		if ((totalFileSizeBytes + picture.getSize()) <= thresholdBytes) {
			// If have enough space, save to MongoDB and DO
			try {

				String pk = pictureService.save(title, comments, date, picture);
				payload = Json.createObjectBuilder().add("id", pk).build();
				return ResponseEntity.ok().body(payload.toString()); // 200

			} catch (Exception e) {
				payload = Json.createObjectBuilder().add("message", e.getMessage()).build();
				return ResponseEntity.status(500).body(payload.toString()); // 500
			}
		} else {
			payload = Json.createObjectBuilder()
					.add("message",
							"The upload has exceeded your monthly upload quota of " + threshold + " "
									+ thresholdUnitUc)
					.build();
			return ResponseEntity.status(413).body(payload.toString()); // 413
		}
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
