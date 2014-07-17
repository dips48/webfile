package com.dips.demo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;

/**
 * Servlet implementation class Return
 */
public class Return extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Return() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FileExt fe=(FileExt)request.getSession().getAttribute("FileExt");
		String str=fe.getPath();
		int index=str.lastIndexOf(File.separator);
		if(index>0){
			FileUpload fu=new FileUpload();
			fu.setFilePath(str.substring(0,index));
			fu.setFileName(str.substring(index+1));
			fu.setOperation(2);
			fu.setContent("  "+request.getParameter(str));
			
			SocketChannel sc=SocketChannel.open();
			sc.connect(new InetSocketAddress(2001));
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			ObjectOutputStream oout=new ObjectOutputStream(out);
			oout.writeObject(fu);
			oout.flush();
			byte[] obArray=out.toByteArray();
			ByteBuffer buf=ByteBuffer.allocate(obArray.length);
			buf.put(obArray);
			buf.flip();
			sc.write(buf);
			
			ByteBuffer obBuf=ByteBuffer.allocate(2000);
			int size=sc.read(obBuf);
			byte[] obarray=obBuf.array();
			ByteArrayInputStream in=new ByteArrayInputStream(obarray,0,size);
			ObjectInputStream oin=new ObjectInputStream(in);
			try {
				fe=(FileExt)oin.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.getSession().setAttribute("FileExt", fe);
		}
		response. sendRedirect ("home.jsp") ;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
