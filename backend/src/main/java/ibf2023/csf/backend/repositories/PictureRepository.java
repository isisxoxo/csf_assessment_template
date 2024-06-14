package ibf2023.csf.backend.repositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import ibf2023.csf.backend.models.Pic;
import ibf2023.csf.backend.models.UploadInfo;

@Repository
public class PictureRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	// TODO Task 4.2
	// You may change the method signature by adding parameters and/or the return
	// type
	// You may throw any exception

	/*
	 * db.travelpics.insert({ 
	 * date: xx,
	 * title: "xx",
	 * comments: "xx",
	 * url: "xx",
	 * fileSize: xx
	 * })
	 */
	public String save(String title, String comments, String date, UploadInfo uploadInfo) throws ParseException {

		// 2024-06-14T03:41:05.203Z
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date newDate = sdf.parse(date);
		Pic pic = new Pic(title, comments, newDate, uploadInfo.getUrl(), uploadInfo.getFileSize());
		Pic result = mongoTemplate.save(pic);

		System.out.println(">>>>SAVED!!!!"); // TO REMOVE

		return result.getId();
	}

	/*
	 * db.travelpics.aggregate([
	 * {
	 * "$project": {
	 * month: { $month: "$date" },
	 * year: { $year: "$date"},
	 * fileSize: 1
	 * }
	 * },
	 * {
	 * "$match": {"month": 06 }
	 * },
	 * {
	 * "$match": {"year": 2024 }
	 * },
	 * {
	 * "$group": {
	 * _id: '',
	 * totalSize: { $sum: '$fileSize' }
	 * }
	 * }
	 * ])
	 * 
	 */
	public Long getTotalFileSize() {
		// public long getTotalFileSize() {

		Calendar calendar = Calendar.getInstance();
		Integer month = calendar.get(Calendar.MONTH) + 1; // Current month
		Integer year = calendar.get(Calendar.YEAR); // Current year

		ProjectionOperation projectionOperation = Aggregation.project("fileSize")
				.and(DateOperators.Month.monthOf("date")).as("month").and(DateOperators.Year.yearOf("date")).as("year");

		MatchOperation matchOperation = Aggregation.match(Criteria.where("month").is(month));
		MatchOperation matchOperation2 = Aggregation.match(Criteria.where("year").is(year));

		GroupOperation groupOperation = Aggregation.group("month", "year").sum("fileSize").as("totalSize");
		Aggregation pipeline = Aggregation.newAggregation(projectionOperation, matchOperation, matchOperation2,
				groupOperation);

		AggregationResults<Document> aggregationResults = mongoTemplate.aggregate(pipeline, "travelpics",
				Document.class);

		List<Document> result = aggregationResults.getMappedResults();
		// Output: [Document{{_id=Document{{month=6, year=2024}}, totalSize=692446}}]

		Long totalFileSizeBytes = result.get(0).getLong("totalSize");
		System.out.println(">>>> TOTAL FILE SIZE: " + totalFileSizeBytes);
		return totalFileSizeBytes;
	}
}
