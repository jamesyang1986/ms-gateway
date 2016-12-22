package cn.ms.gateway;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.markdownj.MarkdownProcessor;

import com.alibaba.fastjson.JSON;

/**
 * MarkDown转为HTML源码,并支持导航
 * 
 * @author lry
 *
 */
public class MarkDownRead {

	public static final int MAX_DEPTH = 6;// 目录深度

	public static void main(String[] args) {
		try {
			System.out.println(read("/Users/lry/Documents/workspace/microservice_work/ms-gateway/test.md", "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> read(String filePath, String encoding) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// html源码
		String resultHtml = "";

		String context = getMarkdownContext(filePath, encoding);
		MarkdownProcessor processor = new MarkdownProcessor();
		String html = processor.markdown(context);
		Document doc = Jsoup.parse(html);

		List<TreeNode> menuList = new ArrayList<TreeNode>();
		Integer[] tempNoList = new Integer[MAX_DEPTH];
		for (Element element : doc.body().children()) {
			String tagName = element.tagName();
			String id = null;
			for (int i = 1; i <= MAX_DEPTH; i++) {
				if (("h" + i).equals(tagName)) {
					tempNoList[i - 1] = tempNoList[i - 1] != null ? (tempNoList[i - 1] + 1) : 1;
					// 重置后半部分
					for (int k = i; k < tempNoList.length; k++) {
						tempNoList[i] = null;
					}

					// 计算前半部分序号ID
					StringBuffer stringBuffer = new StringBuffer();
					for (int j = 0; j < i; j++) {
						stringBuffer.append(tempNoList[j]);
						if (j < i - 1) {
							stringBuffer.append(".");
						}
					}

					id = stringBuffer.toString();
					String parentId = null;
					if (id.indexOf(".") > 0) {
						parentId = id.substring(0, id.lastIndexOf("."));
					}
					menuList.add(new TreeNode(stringBuffer.toString(),parentId, element.text()));
					break;
				}
			}

			//$NON-NLS-修改正文标题$
			if (id != null) {
				resultHtml += element.text(id + " " + element.text()).outerHtml() + "\n";
			} else {
				resultHtml += element.outerHtml() + "\n";
			}
		}

		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		for (TreeNode node1 : menuList) {
			boolean mark = false;
			for (TreeNode node2 : menuList) {
				if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
					mark = true;
					if (node2.getChildren() == null) {
						node2.setChildren(new ArrayList<TreeNode>());
					}
					node2.getChildren().add(node1);
					break;
				}
			}
			if (!mark) {
				nodeList.add(node1);
			}
		}

		map.put("navs", Jsoup.parse(resultHtml).html());
		map.put("context", JSON.toJSONString(nodeList));
		
		return map;
	}

	/**
	 * 读取指定Markdown的文本内容
	 * 
	 * @param fileUrl
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	private static String getMarkdownContext(String fileUrl, String encoding) throws IOException {
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
