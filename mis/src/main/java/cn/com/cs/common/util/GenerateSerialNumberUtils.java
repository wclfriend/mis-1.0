package cn.com.cs.common.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GenerateSerialNumberUtils {

	private static final DecimalFormat documentationTypeFormat = new DecimalFormat("00");

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssMS");

	private static final DateFormat dateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

	private static final DecimalFormat decimalFormat = new DecimalFormat("000");

	private static final Random random = new Random();
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(dateFormat.format(new Date())+ decimalFormat.format(random.nextInt(999)));
		}
	}
	

	/**
	 * 订单号
	 * @return
	 */
	public static synchronized String generatorUserId() {
		return dateFormat.format(new Date()) + decimalFormat.format(random.nextInt(999));
	}
	
	
	/**
	 * 订单号
	 * @return
	 */
	public static synchronized String generatorOrderSerialNumber() {
		return dateFormat.format(new Date()) + decimalFormat.format(random.nextInt(999));
	}

	/**
	 * 保单号生成
	 * 
	 * @param serialNumber
	 *            10位流水号
	 * @param documentationType
	 *            四位单证类型
	 * @return
	 */
	public static String generateOrderSerialNumber(long serialNumber, int documentationType) {
		// 计算得到校验码
		long checkCode = 98 - (serialNumber * 1000000 + documentationType * 100) % 97;
		return decimalFormat.format(serialNumber) + documentationTypeFormat.format(checkCode) + documentationType;
	}

}
