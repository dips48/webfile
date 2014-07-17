package com.dips.demo;

import java.awt.List;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{   public static final String README="readme.txt";
    public static void main( String[] args )throws Exception
    {
        Selector selector=Selector.open();
        ServerSocketChannel ssc=ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ServerSocket ss=ssc.socket();
        ss.bind(new InetSocketAddress(2001));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
        	try{
        	int num=selector.select();
        	Set<SelectionKey> keys=selector.selectedKeys();
        	Iterator<SelectionKey> it=keys.iterator();
        	while(it.hasNext()){
        		SelectionKey sk=it.next();
        		if((sk.readyOps()&SelectionKey.OP_ACCEPT)==SelectionKey.OP_ACCEPT){
        			ServerSocketChannel sscd=(ServerSocketChannel)sk.channel();
        			SocketChannel sc=sscd.accept();
        			sc.configureBlocking(false);
        			sc.register(selector,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        			it.remove();
        		}else{
        			SocketChannel sc=(SocketChannel)sk.channel();
        			ByteBuffer buf=ByteBuffer.allocate(2000);
        			int res=sc.read(buf);
        			if(res>0){        	
        			byte[] obArray=buf.array();
        			//获取文件请求
        			ByteArrayInputStream in=new ByteArrayInputStream(obArray,0,res);
        			ObjectInputStream oin=new ObjectInputStream(in);
        			FileUpload fu=(FileUpload)oin.readObject();
        			if(fu.getFilePath()==null){
        				fu.setFilePath("D:"+File.separator);
        			}else{
        			fu.setFilePath("D:"+File.separator+fu.getFilePath());}
        			Operation(fu,sc);}
        			it.remove();
        		}
        	}
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
    }
    public static void Operation(FileUpload fu,SocketChannel sc)throws Exception{
    	String FileName=fu.getFilePath()+File.separator+fu.getFileName();
    	File f=new File(FileName);
    	System.out.println(FileName);
    	if(fu.isOperation()==1){//执行添加操作
    	if(!f.exists()){
    		f.mkdir();
    	}
    	if(fu.getContent().equals(null)){}else{
    	File fcontent=new File(fu.getFilePath()+File.separator+fu.getFileName()+File.separator+README);
    	FileOutputStream fin=new FileOutputStream(fcontent);
    	FileChannel fc=fin.getChannel();
    	ByteBuffer buf=ByteBuffer.allocate(fu.getContent().getBytes().length);
    	buf.put(fu.getContent().getBytes());
    	buf.flip();
    	fc.write(buf);
    	fc.close();
    	fin.close();
    	}
    	}else if(fu.isOperation()==2){//执行查询操作
    	FileExt fe=new FileExt();
    	fe.setName(fu.getFileName());
    	fe.setPath(fu.getFilePath().substring(3));
    	String[] strArray=f.list();
    	for(String str:strArray){
    		if(!str.equals(README)){
    		fe.getSubFile().add(str);}
    	}
 		File fcontent=new File(FileName+File.separator+README);
    	fe.setContent(getContent(fcontent));
    	ByteArrayOutputStream out=new ByteArrayOutputStream();
		ObjectOutputStream oout=new ObjectOutputStream(out);
		oout.writeObject(fe);
		byte[] obarray=out.toByteArray();
		ByteBuffer buf=ByteBuffer.allocate(obarray.length);
		buf.put(obarray);
		buf.flip();
		sc.write(buf);
    	}else{ //执行删除操作
    		DeleteFile(f);
    	}
    }
    public static void DeleteFile(File f){
    	File[] fl=f.listFiles();
    	for(File ft:fl){
    		if(!ft.getName().equals(README)){
    			DeleteAllFile(ft);
    		}
    	}
    }
    public static void DeleteAllFile(File f){
    	if(f.isDirectory()){
    		File[] fl=f.listFiles();
    		for(File ft:fl){
    			DeleteAllFile(ft);
    		}
    	}
    	f.delete();
    }
    public static String getContent(File f)throws Exception{
    	FileInputStream fin=new FileInputStream(f);
    	FileChannel fc=fin.getChannel();
    	ByteBuffer buf=ByteBuffer.allocate(500);
    	int res=fc.read(buf);
    	String temp="";
    	while(res==500){
    		buf.flip();
    		temp=temp+new String(buf.array());
    		buf.clear();
    		res=fc.read(buf);
    	}
    	temp=temp+new String(buf.array(),0,res);
    	fc.close();
    	fin.close();
    	return temp;
    }
 
}
