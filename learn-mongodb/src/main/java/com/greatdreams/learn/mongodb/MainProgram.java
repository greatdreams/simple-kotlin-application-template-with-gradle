package com.greatdreams.learn.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainProgram {
    private static String HOST = "127.0.0.1";
    private static Integer PORT = 27017;
    private static String CONNECTIONSTRING = "mongodb://localhost:27017";

    public static void main(String []args) {
        // Make a Connection
        MongoClient client = new MongoClient(HOST, PORT);
        // MongoClient client = new MongoClient(CONNECTIONSTRING);
        // Access a Database
        MongoDatabase db = client.getDatabase("test");
        // Access a Collection
        MongoCollection<Document> collection = db.getCollection("test");

        // Query the Collection
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());
        // Insert a Document
        /*
        Document doc = new Document("name", "test")
                .append("type", "database")
                .append("count", 1)
                .append("versions", Arrays.asList("V3.2", "v3.0", "v2.6"))
                .append("info", new Document("x", 203).append("y", 102));
        collection.insertOne(doc);
        */
        collection.insertMany(Arrays.asList(
                Document.parse("{ item: 'journal', qty: 25, size: { h: 14, w: 21, uom: 'cm' }, status: 'A' }"),
                Document.parse("{ item: 'notebook', qty: 50, size: { h: 8.5, w: 11, uom: 'in' }, status: 'A' }"),
                Document.parse("{ item: 'paper', qty: 100, size: { h: 8.5, w: 11, uom: 'in' }, status: 'D' }"),
                Document.parse("{ item: 'planner', qty: 75, size: { h: 22.85, w: 30, uom: 'cm' }, status: 'D' }"),
                Document.parse("{ item: 'postcard', qty: 45, size: { h: 10, w: 15.25, uom: 'cm' }, status: 'A' }")
        ));

        FindIterable<Document> findIterable = collection.find(and(eq("status", "A"), lt("qty", 30)));

        System.out.println(findIterable.first());
        client.close();
    }
}
