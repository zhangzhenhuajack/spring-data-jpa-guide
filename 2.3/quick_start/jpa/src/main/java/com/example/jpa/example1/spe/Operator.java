package com.example.jpa.example1.spe;

public enum Operator {
	/**
	 * 等于
	 */
	EQ("="),
	/**
	 * 等于
	 */
	LK(":"),
	/**
	 * 不等于
	 */
	NE("!="),
	/**
	 * 大于
	 */
	GT(">"),
	/**
	 * 小于
	 */
	LT("<"),
	/**
	 * 大于等于
	 */
	GE(">=");
	Operator(String operator) {
		this.operator = operator;
	}
	public static Operator fromOperator(String operator) {
		for (Operator v: Operator.values()) {
			if (v.operator.equals(operator)) {
				return v;
			}
		}
		return EQ;
	}
	private String operator;
}
