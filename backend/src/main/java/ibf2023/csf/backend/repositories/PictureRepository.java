package ibf2023.csf.backend.repositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.DateOperators;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
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
	 * db.travelpics.aggregate(
	 * {
	 * $group: {
	 * _id: { month: { $month: "$date" } , year: { $year: "$date" } },
	 * totalSize: { $sum: "$fileSize" }
	 * }}
	 * )
	 */
	public void getTotalFileSize() {
		// public long getTotalFileSize() {

		ProjectionOperation projectionOperation = Aggregation.project().and(DateOperators.Month.monthOf("date"))
				.as("month").and(DateOperators.Year.yearOf("date")).as("year");
		GroupOperation groupOperation = Aggregation.group("year", "month").sum("fileSize").as("totalSize");

		Aggregation pipeline = Aggregation.newAggregation(projectionOperation, groupOperation);

		AggregationResults<Document> aggregationResults = mongoTemplate.aggregate(pipeline, "travelpics",
				Document.class);

		// // 4. Get Mapped Results to String
		System.out.println("=======================");
		System.out.println(aggregationResults.getMappedResults().toString());
		;

	}
}
