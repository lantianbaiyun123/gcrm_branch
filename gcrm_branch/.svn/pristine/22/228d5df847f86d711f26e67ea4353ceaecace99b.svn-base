package com.baidu.gcrm.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleMath {
	private static final int DEFAULT_SCALE = 4;

	/**
	 * 两个Double数相加
	 * 
	 */
	public static double add(Double d1, Double d2) {
		BigDecimal b1 = new BigDecimal(d1.toString());
		BigDecimal b2 = new BigDecimal(d2.toString());
		return b1.add(b2).doubleValue();
	}

	/**
	 * 两个Double数相减
	 * @param d1 被减数
	 * @param d2 减数
	 * 
	 */
	public static double sub(Double d1, Double d2) {
		BigDecimal b1 = new BigDecimal(d1.toString());
		BigDecimal b2 = new BigDecimal(d2.toString());
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 两个Double数相乘
	 * 
	 */
	public static double mul(Double d1, Double d2) {
        return mul(d1, d2, DEFAULT_SCALE);
	}
	
	/**
	 * 两个Double数相乘,结果保留指定位，四舍五入
	 * 
	 */
	public static double mul(Double d1, Double d2, int scale) {
		BigDecimal b1 = new BigDecimal(d1.toString());
		BigDecimal b2 = new BigDecimal(d2.toString());
		BigDecimal result = b1.multiply(b2);
		result = result.setScale(scale, RoundingMode.HALF_UP);
		return result.doubleValue();
	}

	/**
	 * 两个Double数相除，结果四舍五入
	 * @param d1 被除数
	 * @param d2 除数
	 * 
	 */
	public static double div(Double d1, Double d2) {
		BigDecimal b1 = new BigDecimal(d1.toString());
		BigDecimal b2 = new BigDecimal(d2.toString());
		return b1.divide(b2, DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 两个Double数相除，结果四舍五入
	 * 
	 */
	public static double div(Double d1, Double d2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (d2.doubleValue() == 0) {
			throw new IllegalArgumentException("The devide must not be zero");
		}
		BigDecimal b1 = new BigDecimal(d1.toString());
		BigDecimal b2 = new BigDecimal(d2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 两个Double数相除，结果向下取整，eg. 10/3=3
	 * 
	 */
	public static double divToFloor(Double d1, Double d2) {
		if (d2.doubleValue() == 0) {
			throw new IllegalArgumentException("The devide must not be zero");
		}
		BigDecimal b1 = new BigDecimal(d1.toString());
		BigDecimal b2 = new BigDecimal(d2.toString());;
		return b1.divide(b2, 0, BigDecimal.ROUND_FLOOR).doubleValue();
	}

}
