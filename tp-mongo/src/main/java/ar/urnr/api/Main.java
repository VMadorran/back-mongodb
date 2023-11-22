package ar.urnr.api;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try (MongoClient mongoClient = new MongoClient()) {

			MongoDatabase database = mongoClient.getDatabase("admin");
			MongoCollection<Document> collectionPages = database.getCollection("pages");

			Document page1 = new Document("title", "Introduction").append("text", "This is the introduction page.")
					.append("author", "John Doe").append("date", LocalDateTime.now());
			Document page2 = new Document("title", "Chapter 1").append("text", "Content of chapter 1 goes here.")
					.append("author", "Jane Smith").append("date", LocalDateTime.now());

			collectionPages.insertMany(Arrays.asList(page1, page2));

		}

		try (MongoClient mongoClient = new MongoClient()) {

			MongoDatabase database = mongoClient.getDatabase("admin");
			MongoCollection<Document> collectionPosts = database.getCollection("posts");

//			 Instancia 1
			Document post1 = new Document("title", "Java Basics").append("resume", "Introduction to Java")
					.append("text", "This is a post about Java basics.")
					.append("tags", Arrays.asList("Java", "Programming"))
					.append("relatedlinks", Arrays.asList("https://example.com/java-tutorial"))
					.append("author", "John Doe").append("date", LocalDateTime.now().minusMonths(6));
//
//			// Instancia 2
			Document post2 = new Document("title", "Web Development").append("resume", "Web Dev Overview")
					.append("text", "Exploring web development technologies.")
					.append("tags", Arrays.asList("Web Development", "Programming"))
					.append("relatedlinks", Arrays.asList("https://example.com/web-dev"))
					.append("author", "David Clark").append("date", LocalDateTime.now().plusDays(32));
//
//			// Instancia 3
			Document post3 = new Document("title", "Machine Learning").append("resume", "ML Fundamentals")
					.append("text", "Introduction to machine learning concepts.")
					.append("tags", Arrays.asList("Machine Learning", "AI"))
					.append("relatedlinks", Arrays.asList("https://example.com/ml-intro"))
					.append("author", "David Clark").append("date", LocalDateTime.now().minusMonths(2));

//			// Instancia 4
			Document post4 = new Document("title", "Data Structures").append("resume", "Data Structures Overview")
					.append("text", "Understanding common data structures.")
					.append("tags", Arrays.asList("Data Structures", "Programming"))
					.append("relatedlinks", Arrays.asList("https://example.com/data-structures"))
					.append("author", "Alice Brown").append("date", LocalDateTime.now());

//			// Instancia 5
			Document post5 = new Document("title", "Mobile App Development").append("resume", "Mobile Dev Guide")
					.append("text", "Building mobile apps with popular frameworks.")
					.append("tags", Arrays.asList("Mobile Development", "Programming"))
					.append("relatedlinks", Arrays.asList("https://example.com/mobile-dev"))
					.append("author", "David Clark").append("date", LocalDateTime.now().minusWeeks(20));

//			// Instancia 6
			Document post6 = new Document("title", "Cloud Computing").append("resume", "Cloud Computing Basics")
					.append("text", "Exploring cloud computing services.")
					.append("tags", Arrays.asList("Cloud Computing", "Technology"))
					.append("relatedlinks", Arrays.asList("https://example.com/cloud-computing"))
					.append("author", "Eva Wilson").append("date", LocalDateTime.now().minusMonths(1));

//			// Instancia 7
			Document post7 = new Document("title", "Cybersecurity").append("resume", "Cybersecurity Best Practices")
					.append("text", "Tips for enhancing online security.")
					.append("tags", Arrays.asList("Cybersecurity", "Technology"))
					.append("relatedlinks", Arrays.asList("https://example.com/cybersecurity-tips"))
					.append("author", "David Clark").append("date", LocalDateTime.now().plusMonths(7));
//
//			// Instancia 8
			Document post8 = new Document("title", "Software Engineering")
					.append("resume", "Software Engineering Guide")
					.append("text", "Best practices in software engineering.")
					.append("tags", Arrays.asList("Software Engineering", "Programming"))
					.append("relatedlinks", Arrays.asList("https://example.com/software-engineering"))
					.append("author", "Laura Davis").append("date", LocalDateTime.now().minusMonths(3));
//
//			// Instancia 9
			Document post9 = new Document("title", "Artificial Intelligence").append("resume", "AI Exploration")
					.append("text", "Exploring the world of AI.")
					.append("tags", Arrays.asList("Artificial Intelligence", "Technology"))
					.append("relatedlinks", Arrays.asList("https://example.com/ai-exploration"))
					.append("author", "Michael Evans").append("date", LocalDateTime.now().minusMonths(3));

//			// Instancia 10
			Document post10 = new Document("title", "Database Management").append("resume", "Database Management Tips")
					.append("text", "Managing databases efficiently.")
					.append("tags", Arrays.asList("Database Management", "Technology"))
					.append("relatedlinks", Arrays.asList("https://example.com/database-management"))
					.append("author", "Laura Davis").append("date", LocalDateTime.now().minusMonths(3));

			collectionPosts
					.insertMany(Arrays.asList(post1, post2, post3, post4, post5, post6, post7, post8, post9, post10));

		}

	}

}
