package cn.ms.gateway;

public class Nav {

	private String order;
	private String title;
	private Nav[] navs=new Nav[10];

	public Nav() {
	}

	public Nav(String order, String title) {
		this.order = order;
		this.title = title;
	}

	public Nav(String order, String title, Nav[] navs) {
		this.order = order;
		this.title = title;
		this.navs = navs;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Nav[] getNavs() {
		return navs;
	}

	public void setNavs(Nav[] navs) {
		this.navs = navs;
	}

	@Override
	public String toString() {
		return "Nav [order=" + order + ", title=" + title + ", navs=" + navs
				+ "]";
	}

}
