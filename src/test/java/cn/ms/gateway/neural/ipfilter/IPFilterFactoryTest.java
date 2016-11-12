package cn.ms.gateway.neural.ipfilter;

import java.util.HashMap;
import java.util.Map;

import cn.ms.gateway.neural.blackwhitelist.BlackWhiteIPListFactory;
import cn.ms.gateway.neural.blackwhitelist.BlackWhiteIPListType;

public class IPFilterFactoryTest {

	public static void main(String[] args) {
		BlackWhiteIPListFactory ipff = new BlackWhiteIPListFactory();
		Map<BlackWhiteIPListType, String> bwIPs = new HashMap<BlackWhiteIPListType, String>();
		bwIPs.put(BlackWhiteIPListType.BLACKLIST, "192.168.0.*;192.168.1.0-192.168.2.10");
		ipff.setBlackWhiteIPs(bwIPs);

		System.out.println(ipff.check(BlackWhiteIPListType.BLACKLIST, "192.168.0.1"));
		System.out.println(ipff.check(BlackWhiteIPListType.BLACKLIST, "192.168.0.100"));
		System.out.println(ipff.check(BlackWhiteIPListType.BLACKLIST, "192.168.1.1"));
		System.out.println(ipff.check(BlackWhiteIPListType.BLACKLIST, "192.160.0.1"));
	}

}
