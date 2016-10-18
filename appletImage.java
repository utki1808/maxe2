import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;


public class appletImage extends Applet {
	Image img;
	MediaTracker tr;
	File temp;
	public void init(){
		try{
			Mongo mongo =new Mongo("localhost",27017);
			DB db=mongo.getDB("ImageData");
			
			File file=new File("/home/student/gridfs.jpeg");
			
			
			
	//save the file
			GridFS gridfs=new GridFS(db,"downloads");
			GridFSInputFile gfsfile=gridfs.createFile(file);
			gfsfile.setFilename("destfile");
			gfsfile.save();
			
   //read the file
			GridFSDBFile outfile=gridfs.findOne(gfsfile.getFilename());
			System.out.println(outfile);
			temp=File.createTempFile("tempfile1", ".tmp");
			System.out.println(temp.getPath());
			outfile.writeTo(temp);
			}catch(Exception e)
			{
				System.out.println("exception occured!!");
			}
	}
	public void  paint(Graphics g)
	{
		
		img=getImage(getCodeBase(),temp.getPath());
		
		g.drawImage(img,50,50,100,100,this);
		
	}
	

}
