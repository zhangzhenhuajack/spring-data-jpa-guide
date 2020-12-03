package com.example.jpa.example1.spe;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;

public class MySpecification<Entity> implements Specification<Entity> {
	private SearchCriteria criteria;

	public MySpecification (SearchCriteria criteria) {
		this.criteria = criteria;
	}

	/**
	 * 实现实体根据不同的字段，不同的Operator组合成不同的Predicate条件
	 *
	 * @param root            must not be {@literal null}.
	 * @param query           must not be {@literal null}.
	 * @param builder  must not be {@literal null}.
	 * @return a {@link Predicate}, may be {@literal null}.
	 */
	@Override
	public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperation().compareTo(Operator.GT)==0) {
			return builder.greaterThanOrEqualTo(
					root.<String> get(criteria.getKey()), criteria.getValue().toString());
		}
		else if (criteria.getOperation().compareTo(Operator.LT)==0) {
			return builder.lessThanOrEqualTo(
					root.<String> get(criteria.getKey()), criteria.getValue().toString());
		}
		else if (criteria.getOperation().compareTo(Operator.LK)==0) {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(
						root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
			} else {
				return builder.equal(root.get(criteria.getKey()), criteria.getValue());
			}
		}
		return null;
	}
}
