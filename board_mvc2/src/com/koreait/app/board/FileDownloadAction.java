package com.koreait.app.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionForward;

public class FileDownloadAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//서버에 저장되어 있는 파일의 이름(테스트1.txt)
		String systemname = req.getParameter("filename");
		//그 파일을 올릴 떄의 이름(테스트.txt)
		String orgname = req.getParameter("orgname");
		//파일이 저장되어 있는 폴더
		String saveFolder = "C:\\web_file";
		
		//C:\web_file\테스트1.txt
		String filename = saveFolder+"\\"+systemname;
		
		//다운로드를 위한 통로 역할
		InputStream is = null; //파일을 읽어올 통로
		OutputStream os = null; //파일을 써줄 통로
		
		//inputstream과 outputstream이 둘 다 필요한 이유
		//서버입장에서는(자바는) 같은 컴퓨터에 있는 외부파일을 읽어서 사용자 로컬 네트워크로 써줘야 함.
		//FileDownloadAction 은 test.txt를 받아와서 client에 보내줘야함.
		//따라서 읽어오기 위한 스트림, 사용자 컴퓨터에 써주기 위한 스트림 두개가 필요함.
		
		//다운받으려는 파일을 자바로 객체로 가져옴
		File file = new File(filename);
		
		//file 객체를 읽어오기 위한 통로 개설3
		is = new FileInputStream(file); // "C:\\web_file"; 이 위치에 있는 파일을 읽어오기 위한 통로를 개설하는것. 
		
		//다운로드를 요청한 사용자의 로컬 정보가 담긴 헤더
		String client = req.getHeader("User-Agent"); //이 요청 헤드에는 다운로드를 누른 사용자의 로컬 정보가 담겨있음
		resp.reset();//깔끔하게 비우고
		
		//파일 다운로드를 해줄 준비, 세팅
		resp.setContentType("application/octet-stream"); //이건 정해져있는 세팅
		resp.setHeader("Content-Description", "JSP Generated Data");
		
		
		String dwName = ""; //이대로 다운로드하면 시스템네임으로 저장이 됨.
//		.....%A2%20%C2....+테스트.txt => 이런식으로 작성된 파일명을 아래 식으로 인코딩 해줌.
		
		try {
			try {
				//orgname과 systemname 이 동일하지 않다는 뜻
				URLEncoder.encode(orgname,"UTF-8").replaceAll("\\+","%20"); //여기서 역슬래시 두개는 + 를 붙이기 위함임.(안그러면 정규식으로 인식해버림)
			} catch (UnsupportedEncodingException e) {
				dwName = URLEncoder.encode(file.getName(),"UTF-8"); //위에서 실패했으면 세스템네임으로 다운로드네임에 넣어줌
				//orgname과 systemname이 동일하다는 뜻
			} //orgname이 만약에 systemname과 동일하면  replaceAll할때 예외발생함.-> try catch
			//dwName은 원래의 orgname을 가지고 있다.
			
			
			//클라이언트의 로컬정보를 담은 client 안에 MSIE 문자열이 포함되어 있는지를 비교(있으면 -1이 아니고, 없으면 -1)
			if(client.indexOf("MSIE") != -1) { //MSIE가 포함되어 있다는 뜻(인터넷익스플로러를 쓰고 있는 상태)
				//MSIE인 경우
				resp.setHeader("Content-Disposition", "attachement; fileName=" +dwName);
			} else {
				//그 외의 경우 파일이름 양쪽에 쌍따옴표 붙여줘야함.
				resp.setHeader("Content-Disposition", "attachement; fileName=\"" +dwName+"\"");
				resp.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
			}
			resp.setHeader("Content-Length", file.length()+"");
			
			//파일을 사용자의 컴퓨터에 써주기 위한 통로 개설
			os = resp.getOutputStream();
			
			//파일을 바이튿단위로 쪼개서 담아줄 배열
			byte[] b = new byte[(int)file.length()]; //그냥 length하면 long형으로 나옴
			int leng = 0;
			
			//파일의 끝이 오기 전까지 반복하며 읽는다.
			while((leng = is.read(b,0,b.length)) != -1) {
				//읽어온 정보(파일의 데이터)를 os 통로를 통해 사용자의 컴퓨터에 써주기
				os.write(b,0,leng);
			}
		
		} catch (Exception e) {
			System.out.println("FileDownloadAction : "+e);
		} finally { //stream 반환
			//열려있는 통로들 전부 닫기
			if(is!=null) {
				is.close();
			}
			if(os!=null) {
				os.close();
			}
		}
		
		return null;
	}
	
	
}
