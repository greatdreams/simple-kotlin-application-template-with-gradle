##### learn-mongodb
##### What's mongodb?

MongoDB is an open-source document database that
provides high performance, high availability, and
automatic scaling.

A record in MongoDB is a document, which is a data structure 
composed of field and value pairs. MongoDB documents are
 similar to JSON objects. The values of fields may include
  other documents, arrays, and arrays of documents.
  
![](img/1.svg)

The advantages of using documents are:

* Documents (i.e. objects) correspond to native data types in many programming languages.
* Embedded documents and arrays reduce need for expensive joins.
* Dynamic schema supports fluent polymorphism.

###### start mongodb server
```bash
$$MONGODB_HOME/mongod
```
Example

```bash
$ export MONGODB_HOME=/Users/greatdreams/softwares/mongodb-osx-x86_64-3.6.3
$ mkdir -p $MONGODB_HOME/data/db
$ $MONGODB_HOME/bin/mongod --dbpath $MONGODB_HOME/data/db --bind_ip 127.0.0.1 --port 27017
```

##### Database and Collections
* MongoDB stores [BSON documents](https://docs.mongodb.com/manual/core/document/#bson-document-format),
 i.e. data records, in [collections](https://docs.mongodb.com/manual/reference/glossary/#term-collection); 
 the collections in databases.
*  In MongoDB, databases hold collections of documents.
*  MongoDB stores documents in collections. Collections are analogous to tables in relational databases.
* If a database does not exist, MongoDB creates the database when you first store data for that database. As such, 
  you can switch to a non-existent database and perform the following operation in the mongo shell:
  
  ```$xslt
  use myNewDB
  
  db.myNewCollection1.insertOne( { x: 1 } )
  ```
* MongoDB stores documents in collections. Collections are analogous to tables in relational databases.  
* If a collection does not exist, MongoDB creates the collection when you first store data for that collection.

  ```$xslt
   db.myNewCollection2.insertOne( { x: 1 } )
   db.myNewCollection3.createIndex( { y: 1 } )
  ```
* By default, a collection does not require its documents to have the same schema; i.e. the documents in a single collection do not need to have the same set of fields and the data type for a field can differ across documents within a collection.
  
  Starting in MongoDB 3.2, however, you can enforce document validation rules for a collection during update and insert operations. See Schema Validation for details. 
  
##### Document

 MongoDB stores data records as BSON documents. BSON is a binary representation of JSON documents, though it contains more data types than JSON. For the BSON spec, 
 see bsonspec.org. See also BSON Types.
  
  
##### Reference
1. [mongodb homepage](https://www.mongodb.com/)
2. [Install MongoDB Community Edition on macOS¶](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)
2. [java driver for mongodb](https://mongodb.github.io/mongo-java-driver/)
3. [MongoDB Driver Quick Start](http://mongodb.github.io/mongo-java-driver/3.6/driver/getting-started/quick-start/)
4. [Query Documents](https://docs.mongodb.com/manual/tutorial/query-documents/)