package cn.com.cs.aop;

public class HelloWorldImpl1 implements HelloWorld {
	public void printHelloWorld() {
		System.out.println("1.No.1");
	}

	public void doPrint() {
		System.out.println("1.No.2");
		return;
	}
}