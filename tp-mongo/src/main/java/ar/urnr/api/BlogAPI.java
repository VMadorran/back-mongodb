package ar.urnr.api;

import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;
import static spark.Spark.get;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.TextSearchOptions;

import spark.Spark;

public class BlogAPI {

	public static void main(String[] args) {

		get("/pages/:id", (req, res) -> {

			res.header("Access-Control-Allow-Origin", "*");

			String paginaId = req.params("id");
			Document document = null;

			try (MongoClient mongoClient = new MongoClient()) {
				MongoDatabase database = mongoClient.getDatabase("admin");
				MongoCollection<Document> collectionPages = database.getCollection("pages");
				document = collectionPages.find(Filters.eq("_id", new ObjectId(paginaId))).first();
			}

			return documentsToJson(Arrays.asList(document));

		});

		get("/byauthor", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");

			List<Document> results = new ArrayList<>();
			try (MongoClient mongoClient = new MongoClient()) {

				MongoDatabase database = mongoClient.getDatabase("admin");
				MongoCollection<Document> collectionAuthors = database.getCollection("posts");
				collectionAuthors
						.aggregate(Arrays.asList(new Document("$project", new Document("author", 1)),
								new Document("$group",
										new Document("_id", "$author").append("count", new Document("$sum", 1)))))
						.into(results);
			}

			return documentsToJson(results);
		});

		get("posts/latest", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");

			List<Document> results = new ArrayList<>();
			try (MongoClient mongoClient = new MongoClient()) {

				MongoDatabase database = mongoClient.getDatabase("admin");
				MongoCollection<Document> collectionPages = database.getCollection("posts");
				Bson projectiona = include("title", "resume");
				collectionPages.find().projection(projectiona).sort(descending("date")).limit(4).into(results);

			}
			List<Document> last4 = new ArrayList<>();
			last4.add(results.get(0));
			last4.add(results.get(1));
			last4.add(results.get(2));
			last4.add(results.get(3));

			return documentsToJson(results);

		});

		get("/posts/author/:nombreautor", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");

			String nombreAutor = req.params("nombreautor");
			List<Document> results = new ArrayList<>();

			try (MongoClient mongoClient = new MongoClient()) {
				MongoDatabase database = mongoClient.getDatabase("admin");
				MongoCollection<Document> collectionPosts = database.getCollection("posts");

				Document query = new Document("author", nombreAutor);

				FindIterable<Document> documents = collectionPosts.find(query);

				try (MongoCursor<Document> cursor = documents.iterator()) {
					while (cursor.hasNext()) {
						Document document = cursor.next();
						results.add(document);
					}
				}
			}

			return documentsToJson(results);
		});

		get("/posts/:id", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");

			String postId = req.params("id");
			Document document = null;
			try (MongoClient mongoClient = new MongoClient()) {

				MongoDatabase database = mongoClient.getDatabase("admin");
				MongoCollection<Document> collectionPages = database.getCollection("posts");
				document = collectionPages.find(Filters.eq("_id", new ObjectId(postId))).first();

			}

			return documentsToJson(Arrays.asList(document));

		});

		get("/search/:text", (req, res) -> {
			res.header("Access-Control-Allow-Origin", "*");

			String text = req.params("text");
			List<Document> results = new ArrayList<>();
			try (MongoClient mongoClient = new MongoClient()) {
				MongoDatabase database = mongoClient.getDatabase("admin");
				MongoCollection<Document> collectionPosts = database.getCollection("posts");

				collectionPosts.createIndex(Indexes.text("text"));
				TextSearchOptions options = new TextSearchOptions().caseSensitive(true);
				Bson filter = Filters.text(text, options);
				Bson projectiona = include("title", "resume", "author", "date");
				collectionPosts.find(filter).projection(projectiona).into(results);

			}

			return documentsToJson(results);
		});

		Spark.exception(Exception.class, (exception, request, response) -> {
			exception.printStackTrace();
		});

	}

	public static List<String> documentsToJson(List<Document> list) {
		List<String> jsonList = new ArrayList<>();
		for (Document document : list) {
			String json = document.toJson();
			jsonList.add(json);
		}
		return jsonList;
	}
}