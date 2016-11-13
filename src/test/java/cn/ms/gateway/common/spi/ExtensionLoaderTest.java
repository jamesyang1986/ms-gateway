package cn.ms.gateway.common.spi;

public class ExtensionLoaderTest {

	public static void main(String[] args) {
		DemoInterface di=ExtensionLoader.getExtensionLoader(DemoInterface.class).getAdaptiveExtension();
		System.out.println(di);
	}
	
}
