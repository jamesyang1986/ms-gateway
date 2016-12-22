package cn.ms.gateway;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class TestTree {

	public static void main(String[] args) {
		List<TreeNode> menuList = new ArrayList<TreeNode>();
		menuList.add(new TreeNode("1",null,""));
		menuList.add(new TreeNode("1.1","1",""));
		menuList.add(new TreeNode("1.2","1",""));
		menuList.add(new TreeNode("1.3","1",""));
		menuList.add(new TreeNode("2",null,""));
		
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		for (TreeNode node1 : menuList) {
			boolean mark = false;
			for (TreeNode node2 : menuList) {
				if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
					mark = true;
					if (node2.getChildren() == null){
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
		
		// 转为json格式
		System.out.println(JSON.toJSONString(nodeList));
	}

}
