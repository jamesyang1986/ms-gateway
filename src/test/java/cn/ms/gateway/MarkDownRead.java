package cn.ms.gateway;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.markdownj.MarkdownProcessor;

/**
 * MarkDown转为HTML源码,并支持导航
 * @author lry
 *
 */
public class MarkDownRead {

	public static final int MAX_DEPTH = 6;// 目录深度

	public static void main(String[] args) {
		read("/Users/lry/Documents/workspace/microservice_work/ms-gateway/test.md", "UTF-8");
	}
	
	public static void read(String filePath, String encoding) {
		//html源码
		String resultHtml="";
		
		try {
			String context=getMarkdownContext(filePath, encoding);
			MarkdownProcessor processor = new MarkdownProcessor();
			String html=processor.markdown(context);
			Document doc=Jsoup.parse(html);
			
			Integer[] tempNoList=new Integer[MAX_DEPTH];
			for (Element element: doc.body().children()) {
				String tagName=element.tagName();
				String outerHtml = element.outerHtml();
				for (int i = 1; i <= MAX_DEPTH; i++) {
					if(("h"+i).equals(tagName)){
						//TODO 需要添加超链接
						resultHtml="<a href=\"#\">"+outerHtml+"</a>\n";
						tempNoList[i-1]= tempNoList[i-1] != null ? (tempNoList[i-1] + 1): 1;
						//重置后半部分
						for (int k = i; k < tempNoList.length; k++) {
							tempNoList[i] = null;
						}
						
						//计算前半部分序号ID
						StringBuffer stringBuffer=new StringBuffer();
						for (int j = 0; j < i; j++) {
							stringBuffer.append(tempNoList[j]);
							if(j<i-1){
								stringBuffer.append(".");
							}
						}
						
						System.out.println(i+"-->"+stringBuffer.toString()+"-->"+resultHtml);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取指定Markdown的文本内容
	 * 
	 * @param fileUrl
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	private static  String getMarkdownContext(String fileUrl, String encoding) throws IOException {
		File file = new File(URLDecoder.decode(fileUrl, encoding));
		BufferedReader in = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = in.readLine()) != null) {
			sb.append(line).append("\n");
		}
		in.close();
		
		return sb.toString();
	}

}
