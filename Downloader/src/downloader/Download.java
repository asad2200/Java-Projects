/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

/**
 *
 * @author asadj
 */
public class Download implements Runnable{

    String link;
    File out;
    JTextArea ta;
    JProgressBar bar;
    public Download(String link,File out,JTextArea ta,JProgressBar bar){
        this.link=link;
        this.out=out;
        this.ta=ta;
        this.bar=bar;
    }
    
    
    @Override
    public void run() {
        try{
            
            URL url = new URL(link);
            HttpURLConnection http=(HttpURLConnection)url.openConnection();
            double fileSize = (double) http.getContentLengthLong();
            BufferedInputStream in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(this.out);
            BufferedOutputStream bout=new BufferedOutputStream(fos,1024);
            byte[] buffer=new byte[1024];
            double downloaded=0.0;
            int read =0;
            double percentage=0.0;
            while((read = in.read(buffer,0,1024)) >= 0){
                bout.write(buffer, 0, read);
                downloaded += read;
                percentage = (downloaded*100)/fileSize;
                bar.setValue((int) percentage);
                String percent=String.format("%.2f",percentage);
                ta.append("Download "+ percent +"% of a File\n");
            }
            bout.close();
            in.close();
            ta.setText("\n Downlaoded Complete");
        }catch(Exception e){
           ta.setText(""+e);
        }
    }
    
}
