import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;


public class mongo {
	
	static MongoClient mongoClient=new MongoClient("localhost",27017);
	static DB dbDatabase=mongoClient.getDB("student1");
	
	static DBCollection collection=dbDatabase.getCollection("studentInfo");
@SuppressWarnings("deprecation")
public static void main(String[] args) {
	
		int choice;
		do{
			System.out.println("MENU");
			System.out.println("1->Insert Document");
			System.out.println("2->Update ");
			System.out.println("3->Display the Collections");
			System.out.println("4->Search for Particular Collection");
			System.out.println("5->Drop the collections");

			Scanner sc=new Scanner(System.in);
			choice=sc.nextInt();
			switch(choice)
			{
			
			case 1:
				Insert();
					break;
			case 2:
				Update();
				break;
			case 3:
				Display();
					break;
			case 4:
				Search();
				break;
			case 5:
				drop();
				break;
			}
		}while(choice!=5);	
}
static void drop()
{
	collection.drop();
}

static void Display()
{
DBCursor cur=collection.find();
	
	while(cur.hasNext())
	{
		System.out.println(cur.next());
	}
}
static void Insert(){
	Scanner sc=new Scanner(System.in);
	
	System.out.println("Enter the name of student");
	String name=sc.next();
	
	System.out.println("Enter the Age of person");
	int age=sc.nextInt();
	
	System.out.println("Enter the  number of subjects he want to Enroll");
	int numberOfSubject=sc.nextInt();
	int i=0;
	String[] sub=new String[5];
	int[] sir=new int[5];
	BasicDBObject embededDocument=new BasicDBObject();
	while(numberOfSubject--!=0)
	{
		System.out.println("Enter the from DOS,DSPS,OSA,DMSA,MAC");
		sub[i]=sc.next();
		
		System.out.println("Enter the Marks for subjects");
		sir[i]=sc.nextInt();
		
		
		embededDocument.put(sub[i],sir[i]);
		
		i++;
	}
	BasicDBObject document=new BasicDBObject();
	document.put("Name", name);
	document.put("Age", age);
	
	document.put("SubjectInfo", embededDocument);
	
	
	System.out.println("Enter the Date of Birth year,month,date of Student");
	int year=sc.nextInt();
	int month=sc.nextInt();
	int day=sc.nextInt();
	Date date=new Date(year,month,day);
	document.put("Date_Of_Birth",date);
	
	collection.insert(document);
}

static void Update(){
	Scanner sc =new Scanner(System.in);
	System.out.println("Enter the Nameof person");
	String searchName=sc.next();

	BasicDBObject query=new BasicDBObject();
	System.out.println("Enter the Field you want to update");
	System.out.println("From below Fields");
	System.out.println("Name\n Age\n SubjectInfo\n Date_Of_Birth");
	
	String searchField = sc.next();
	{
		BasicDBObject searchIntheDocument=new BasicDBObject();
		System.out.println("Enter the value of searchField");
		String searchValue=sc.next();
		query.put("$set",new BasicDBObject().append(searchField,searchValue));
		
		searchIntheDocument.put("Name", searchName);
	collection.update(searchIntheDocument,query);
	
}	
}

static void Search(){
	System.out.println("Enter the key and Value for document to search ");
	Scanner sc=new Scanner(System.in);
	String key=sc.next();
	Object value;
	if(sc.hasNextInt())
	{
		value=sc.nextInt();
	}
	else
	{
		value=sc.next();
	}
	System.out.println(value.getClass().getSimpleName());
	BasicDBObject search=new BasicDBObject();
	search.put(key, value);
	DBCursor cursor=collection.find(search);
	while(cursor.hasNext())
		{
		System.out.println(cursor.next());
		
		}
}
}
