package in.webcrawler.sample;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBTestSamples {

	public void test1() {
		
		MongoCredential credential = MongoCredential.createCredential("root", "admin", "1995".toCharArray());
		
		MongoClient mongoClient = new MongoClient(new ServerAddress("192.168.1.106", 27017), credential, MongoClientOptions.builder().build());
		
		MongoDatabase database = mongoClient.getDatabase("WebCrawler");
		
		MongoCollection<Document> collection = database.getCollection("test");
		    
		System.out.println("Inserting using BasicDBObjects...");
		
		Document basic1 = new Document();

		//basic1.put("_id", "1");

		basic1.put("type", "basic");

		basic1.put("first-name", "Amaury");

		basic1.put("last-name", "Valdes"); 

		collection.insertOne(basic1);

		System.out.println("Employee 1: " + basic1.toJson());
		
		mongoClient.close();

	}
	
	public static void main(String[] args) {

		new MongoDBTestSamples().test1();
	}
}
