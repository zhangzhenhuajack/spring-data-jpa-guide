package com.example.jpa.demo;

import com.example.jpa.demo.db.UserInfo;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpELTest {
	public static void main(String[] args) {
		UserInfo rootUserInfo = UserInfo.builder().name("jack").build();
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name");

		EvaluationContext context = new StandardEvaluationContext(rootUserInfo);
		String name = (String) exp.getValue(context);
		System.out.println(name);
//
//		String expressionStr = "1 + 2";
//		ExpressionParser parpser = new SpelExpressionParser(); //SpelExpressionParser是Spring内部对ExpressionParser的唯一最终实现类
//		Expression exp = parpser.parseExpression(expressionStr); //把该表达式，解析成一个Expression对象：SpelExpression
//
//		// 方式一：直接计算
//		Object value = exp.getValue();
//		System.out.println(value.toString()); //3
//		// 若你在@Value中或者xml使用此表达式，请使用#{}包裹~~~~~~~~~~~~~~~~~
//		System.out.println(parpser.parseExpression("T(System).getProperty('user.dir')").getValue()); //E:\work\remotegitcheckoutproject\myprojects\java\demo-war
//		System.out.println(parpser.parseExpression("T(java.lang.Math).random() * 100.0").getValue()); //27.38227555400853
//
//		// 方式二：定义环境变量，在环境内计算拿值
//		// 环境变量可设置多个值：比如BeanFactoryResolver、PropertyAccessor、TypeLocator等~~~
//		// 有环境变量，就有能力处理里面的占位符 ${}
//		EvaluationContext context = new StandardEvaluationContext();
//		System.out.println(exp.getValue(context)); //3
//
//		// Turn on:
//// - auto null reference initialization
//// - auto collection growing
//		SpelParserConfiguration config = new SpelParserConfiguration(true,true);
//
//		ExpressionParser parser = new SpelExpressionParser(config);
	}
}
