from pymongo import MongoClient
from datetime import datetime

client = MongoClient()
db = client.testdb
db.post
while(1):
	ch=int(raw_input("1.Insert\n2.Display\n3.Update\n4.Delete Document\n5.Delete Collection\n6.Exit\nEnter Choice:"))
	if(ch==1):
		#insert
		title = raw_input("Enter the title to insert:")
		result = db.post.insert_one({"Title":title,"User":"Shubham","Message":"Its Awesome","Date Created":datetime.strptime("2016-08-04", "%Y-%m-%d"),"Likes":250})
		print "Inserted _id:",result.inserted_id
	elif(ch==2):
		#display
		cursor = db.post.find()
		for document in cursor:
			print(document)
	elif(ch==3):
		#update
		title1 = raw_input("Enter the title to update:")
		title2 = raw_input("Enter the new title:")
		result = db.post.update_many({"Title":title1},{"$set":{"Title":title2}})
		print "Records Modified:",result.modified_count
	elif(ch==4):
		#delete document
		title3 = raw_input("Enter the title to remove:")
		result = db.post.delete_many({"Title":title3})
		print "Records Deleted:",result.deleted_count
	elif(ch==5):
		#delete collection
		db.post.drop()
	elif(ch==6):
		exit(0)
