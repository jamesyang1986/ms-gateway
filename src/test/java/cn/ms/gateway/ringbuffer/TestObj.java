package cn.ms.gateway.ringbuffer;

public class TestObj {

	long value;
	
	public TestObj(int value) {
		this.value=value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public long getValue() {
		return value;
	}
}
