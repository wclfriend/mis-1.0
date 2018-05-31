package cn.com.cs.aop;

public class HelloWorldImpl2 implements HelloWorld {
	public void printHelloWorld() {
		System.out.println("2.No.1");
	}

	public void doPrint() {
		System.out.println("2.No.2");
		return;
	}
}