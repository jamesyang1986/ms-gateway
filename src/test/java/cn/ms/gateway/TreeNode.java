package cn.ms.gateway;

import java.util.List;

public class TreeNode {
	
	private String id;
	private String name;
	private String parentId;
	private List<TreeNode> children;

	public TreeNode() {
	}

	public TreeNode(String id,String parentId,String name) {
		this.id=id;
		this.parentId=parentId;
		this.name=name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", name=" + name + ", parentId="
				+ parentId + ", children=" + children + "]";
	}

}