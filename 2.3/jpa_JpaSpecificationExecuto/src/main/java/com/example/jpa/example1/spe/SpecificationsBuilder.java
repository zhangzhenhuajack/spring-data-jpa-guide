package com.example.jpa.example1.spe;

import com.example.jpa.example1.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 处理请求产生
 * @param <Entity>
 */
public class SpecificationsBuilder<Entity> {

	private final List<SearchCriteria> params;

	//初始化params，保证每次实例都是一个新的ArrayList
	public SpecificationsBuilder() {
		params = new ArrayList<SearchCriteria>();
	}

	//利用正则表达式取我们search参数里面的值，解析成SearchCriteria对象
	public Specification<Entity> buildSpecification(String search) {
		Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
		Matcher matcher = pattern.matcher(search + ",");
		while (matcher.find()) {
			this.with(matcher.group(1), Operator.fromOperator(matcher.group(2)), matcher.group(3));
		}
		return this.build();
	}

	//根据参数返回我们刚才创建的SearchCriteria
	private SpecificationsBuilder with(String key, Operator operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}
	//根据我们刚才创建的MySpecification返回所需要的Specification
	private Specification<Entity> build() {
		if (params.size() == 0) {
			return null;
		}

		List<Specification> specs = params.stream()
				.map(MySpecification<User>::new)
				.collect(Collectors.toList());

		Specification result = specs.get(0);

		for (int i = 1; i < params.size(); i++) {
			result = Specification.where(result)
					.and(specs.get(i));
		}
		return result;
	}
}
