package com.example.jpa.demo.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@ToString
@Component //通过@Value使用SpEL的地方，一定要此对象交由Spring进行管理
public class DemoProperties {
//第一：逻辑运算操作
    @Value("#{19 + 1}") // 20
    private double add;
    @Value("#{'String1 ' + 'string2'}") // "String1 string2"
    private String addString;

    @Value("#{20 - 1}") // 19
    private double subtract;

    @Value("#{10 * 2}") // 20
    private double multiply;

    @Value("#{36 / 2}") // 19
    private double divide;

    @Value("#{36 div 2}") // 18, the same as for / operator
    private double divideAlphabetic;

    @Value("#{37 % 10}") // 7
    private double modulo;

    @Value("#{37 mod 10}") // 7, the same as for % operator
    private double moduloAlphabetic;

    @Value("#{2 ^ 9}") // 512
    private double powerOf;

    @Value("#{(2 + 2) * 2 + 9}") // 17
    private double brackets;
// 第二：  逻辑比较符号
    @Value("#{1 == 1}") // true
    private boolean equal;

    @Value("#{1 eq 1}") // true
    private boolean equalAlphabetic;

    @Value("#{1 != 1}") // false
    private boolean notEqual;

    @Value("#{1 ne 1}") // false
    private boolean notEqualAlphabetic;

    @Value("#{1 < 1}") // false
    private boolean lessThan;

    @Value("#{1 lt 1}") // false
    private boolean lessThanAlphabetic;

    @Value("#{1 <= 1}") // true
    private boolean lessThanOrEqual;

    @Value("#{1 le 1}") // true
    private boolean lessThanOrEqualAlphabetic;

    @Value("#{1 > 1}") // false
    private boolean greaterThan;

    @Value("#{1 gt 1}") // false
    private boolean greaterThanAlphabetic;

    @Value("#{1 >= 1}") // true
    private boolean greaterThanOrEqual;

    @Value("#{1 ge 1}") // true
    private boolean greaterThanOrEqualAlphabetic;
//第三：逻辑关系运算符
    @Value("#{250 > 200 && 200 < 4000}") // true
    private boolean and;

    @Value("#{250 > 200 and 200 < 4000}") // true
    private boolean andAlphabetic;

    @Value("#{400 > 300 || 150 < 100}") // true
    private boolean or;

    @Value("#{400 > 300 or 150 < 100}") // true
    private boolean orAlphabetic;

    @Value("#{!true}") // false
    private boolean not;

    @Value("#{not true}") // false
    private boolean notAlphabetic;
    @Value("#{2 > 1 ? 'a' : 'b'}") // "b"
    private String ternary;

    @Value("#{demoProperties.someProperty != null ? demoProperties.someProperty : 'default'}")
    private String ternaryProperty;

    /**
     * 如果someProperty为null则返回default值。Elvis运算符是三元表达式简写
     */
    @Value("#{demoProperties.someProperty ?: 'default'}")
    private String elvis;

    /**
     * 如果系统属性pop3.port已定义会直接注入，如果未定义，则返回默认值25.
     */
    @Value("#{systemProperties['pop3.port'] ?: 25}")
    private Integer port;

    /**
     * 还可以用于安全引用运算符主要为了避免空指针，源于Groovy语言。很多时候你引用一个对象的方法或者属性时都需要做非空校验。为了避免此类问题、使用安全引用运算符只会返回null而不是抛出一个异常。
     */
//    @Value("#{demoPropertiesx?:someProperty}") // 如果someBean不为null则返回someProperty值。
    private String someProperty;
//第四部分：正则表达式的支持
    @Value("#{'100' matches '\\d+' }") // true
    private boolean validNumericStringResult;

    @Value("#{'100fghdjf' matches '\\d+' }") // false
    private boolean invalidNumericStringResult;

    @Value("#{'valid alphabetic string' matches '[a-zA-Z\\s]+' }") // true
    private boolean validAlphabeticStringResult;

    @Value("#{'invalid alphabetic string #$1' matches '[a-zA-Z\\s]+' }") // false
    private boolean invalidAlphabeticStringResult;

    @Value("#{demoProperties.someValue matches '\\d+'}") // true 如果 someValue 只有数字
    private boolean validNumericValue;

    private String someValue="";
    private String rootUser="root";
}
