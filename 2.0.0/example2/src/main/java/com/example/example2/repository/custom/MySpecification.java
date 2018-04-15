package com.example.example2.repository.custom;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 扩展Specification
 * @param <T>
 */
public class MySpecification<T> implements Specification<T> {
    /**
     * 属性分隔符
     */
    private static final String PROPERTY_SEPARATOR = ".";
    /**
     * and条件组
     */
    List<Cnd> andConditions = new ArrayList<>();
    /**
     * or条件组
     */
    List<Cnd> orConditions = new ArrayList<>();
    /**
     * 排序条件组
     */
    List<Order> orders = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        Predicate restrictions = cb.and(getAndPredicates(root, cb));
        restrictions = cb.and(restrictions, getOrPredicates(root, cb));
        cq.orderBy(getOrders(root, cb));
        return restrictions;
    }

    public MySpecification and(Collection<Cnd> conditions) {
        andConditions.addAll(conditions);
        return this;
    }

    public MySpecification and(Cnd... conditions) {
        for (Cnd condition : conditions) {
            andConditions.add(condition);
        }
        return this;
    }

    public MySpecification or(Collection<Cnd> conditions) {
        orConditions.addAll(conditions);
        return this;
    }

    public MySpecification or(Cnd... conditions) {
        for (Cnd condition : conditions) {
            orConditions.add(condition);
        }
        return this;
    }

    public MySpecification desc(String property) {
        this.orders.add(Order.desc(property));
        return this;
    }

    public MySpecification asc(String property) {
        this.orders.add(Order.asc(property));
        return this;
    }

    public MySpecification order(String property, Sort.Direction direction) {
        this.orders.add(new Order(property, direction));
        return this;
    }

    public MySpecification orders(Order... orders) {
        this.orders.addAll(Arrays.asList(orders));
        return this;
    }

    public MySpecification orders(Collection<Order> orders) {
        this.orders.addAll(orders);
        return this;
    }

    private Predicate getAndPredicates(Root<T> root, CriteriaBuilder cb) {
        Predicate restrictions = cb.conjunction();
        for (Cnd condition : andConditions) {
            if (condition == null) {
                continue;
            }
            Path<?> path = this.getPath(root, condition.property);
            if (path == null) {
                continue;
            }
            switch (condition.operator) {
                case eq:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String) {
                            if (!((String) condition.value).isEmpty()) {
                                restrictions = cb.and(restrictions, cb.equal(path, condition.value));
                            }
                        } else {
                            restrictions = cb.and(restrictions, cb.equal(path, condition.value));
                        }
                    }
                    break;
                case ge:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.ge((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case gt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.gt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case in:
                    restrictions = cb.and(restrictions, path.in(condition.value));
                    break;
                case le:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.le((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case lt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.and(restrictions, cb.lt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case ne:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.notEqual(path, condition.value));
                        } else {
                            restrictions = cb.and(restrictions, cb.notEqual(path, condition.value));
                        }
                    }
                    break;
                case like:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, "%" + condition.value + "%"));
                        }
                    }
                    break;
                case ilike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, (String) condition.value));
                        }
                    }
                    break;
                case llike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, "%" + condition.value));
                        }
                    }
                    break;
                case rlike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.and(restrictions, cb.like((Path<String>) path, condition.value + "%"));
                        }
                    }
                    break;
                case notIn:
                    restrictions = cb.and(restrictions, path.in(condition.value).not());
                    break;
                case isNull:
                    restrictions = cb.and(restrictions, path.isNull());
                    break;
                case isNotNull:
                    restrictions = cb.and(restrictions, path.isNotNull());
                    break;
            }
        }
        return restrictions;
    }

    private Predicate getOrPredicates(Root<T> root, CriteriaBuilder cb) {
        Predicate restrictions = cb.conjunction();
        for (Cnd condition : orConditions) {
            if (condition == null) {
                continue;
            }
            Path<?> path = this.getPath(root, condition.property);
            if (path == null) {
                continue;
            }
            switch (condition.operator) {
                case eq:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String) {
                            if (!((String) condition.value).isEmpty()) {
                                restrictions = cb.or(restrictions, cb.equal(path, condition.value));
                            }
                        }
                    } else {
                        restrictions = cb.or(restrictions, path.isNull());
                    }
                    break;
                case ge:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.ge((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case gt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.gt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case in:
                    restrictions = cb.or(restrictions, path.in(condition.value));
                    break;
                case le:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.le((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case lt:
                    if (Number.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof Number) {
                        restrictions = cb.or(restrictions, cb.lt((Path<Number>) path, (Number) condition.value));
                    }
                    break;
                case ne:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.notEqual(path, condition.value));
                        } else {
                            restrictions = cb.or(restrictions, cb.notEqual(path, condition.value));
                        }
                    }
                    break;
                case like:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, "%" + condition.value + "%"));
                        }
                    }
                    break;
                case ilike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, (String) condition.value));
                        }
                    }
                    break;
                case llike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, "%" + condition.value));
                        }
                    }
                    break;
                case rlike:
                    if (condition.value != null) {
                        if (String.class.isAssignableFrom(path.getJavaType()) && condition.value instanceof String && !((String) condition.value).isEmpty()) {
                            restrictions = cb.or(restrictions, cb.like((Path<String>) path, condition.value + "%"));
                        }
                    }
                    break;
                case notIn:
                    restrictions = cb.or(restrictions, path.in(condition.value).not());
                    break;
                case isNull:
                    restrictions = cb.or(restrictions, path.isNull());
                    break;
                case isNotNull:
                    restrictions = cb.or(restrictions, path.isNotNull());
                    break;
            }
        }
        return restrictions;
    }

    private List<javax.persistence.criteria.Order> getOrders(Root<T> root, CriteriaBuilder cb) {
        List<javax.persistence.criteria.Order> orderList = new ArrayList<>();
        if (root == null || CollectionUtils.isEmpty(orders)) {
            return orderList;
        }
        for (Order order : orders) {
            if (order == null) {
                continue;
            }
            String property = order.getProperty();
            Sort.Direction direction = order.getDirection();
            Path<?> path = this.getPath(root, property);
            if (path == null || direction == null) {
                continue;
            }
            switch (direction) {
                case ASC:
                    orderList.add(cb.asc(path));
                    break;
                case DESC:
                    orderList.add(cb.desc(path));
                    break;
            }
        }
        return orderList;
    }

    /**
     * 获取Path
     *
     * @param path         Path
     * @param propertyPath 属性路径
     * @return Path
     */
    private <X> Path<X> getPath(Path<?> path, String propertyPath) {
        if (path == null || StringUtils.isEmpty(propertyPath)) {
            return (Path<X>) path;
        }
        String property = StringUtils.substringBefore(propertyPath, PROPERTY_SEPARATOR);
        return getPath(path.get(property), StringUtils.substringAfter(propertyPath, PROPERTY_SEPARATOR));
    }

    /**
     * 条件
     */
    public static class Cnd {
        Operator operator;
        String property;
        Object value;

        public Cnd(String property, Operator operator, Object value) {
            this.operator = operator;
            this.property = property;
            this.value = value;
        }

        public Cnd(String property, Operator operator) {
            this.operator = operator;
            this.property = property;
        }

        /**
         * 相等
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd eq(String property, Object value) {
            return new Cnd(property, Operator.eq, value);
        }

        /**
         * 不相等
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd ne(String property, Object value) {
            return new Cnd(property, Operator.ne, value);
        }

        /**
         * 大于
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd gt(String property, Object value) {
            return new Cnd(property, Operator.gt, value);
        }

        /**
         * 小于
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd lt(String property, Object value) {
            return new Cnd(property, Operator.lt, value);
        }

        /**
         * 大于等于
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd ge(String property, Object value) {
            return new Cnd(property, Operator.ge, value);
        }

        /**
         * 小于等于
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd le(String property, Object value) {
            return new Cnd(property, Operator.le, value);
        }

        /**
         * 模糊like %%
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd like(String property, Object value) {
            return new Cnd(property, Operator.like, value);
        }

        /**
         * 模块右like xxx%
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd rlike(String property, Object value) {
            return new Cnd(property, Operator.rlike, value);
        }

        /**
         * 模糊左like %xxx
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd llike(String property, Object value) {
            return new Cnd(property, Operator.llike, value);
        }

        /**
         * 自定义模糊
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd ilike(String property, Object value) {
            return new Cnd(property, Operator.ilike, value);
        }

        /**
         * 集合中
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd in(String property, Object value) {
            return new Cnd(property, Operator.in, value);
        }

        /**
         * 不在集合中
         *
         * @param property
         * @param value
         * @return
         */
        public static Cnd notIn(String property, Object value) {
            return new Cnd(property, Operator.notIn, value);
        }

        /**
         * 是空
         *
         * @param property
         * @return
         */
        public static Cnd isNull(String property) {
            return new Cnd(property, Operator.isNull);
        }

        /**
         * 不是空
         *
         * @param property
         * @return
         */
        public static Cnd isNotNull(String property) {
            return new Cnd(property, Operator.isNotNull);
        }
    }

    /**
     * 排序
     */
    public static class Order {
        private String property;
        private Sort.Direction direction = Sort.Direction.ASC;

        /**
         * 构造方法
         */
        public Order() {
        }

        /**
         * 构造方法
         *
         * @param property  属性
         * @param direction 方向
         */
        public Order(String property, Sort.Direction direction) {
            this.property = property;
            this.direction = direction;
        }

        /**
         * 返回递增排序
         *
         * @param property 属性
         * @return 递增排序
         */
        public static Order asc(String property) {
            return new Order(property, Sort.Direction.ASC);
        }

        /**
         * 返回递减排序
         *
         * @param property 属性
         * @return 递减排序
         */
        public static Order desc(String property) {
            return new Order(property, Sort.Direction.DESC);
        }

        @Override
        public String toString() {
            return property + " " + direction.name();
        }

        public Sort.Direction getDirection() {
            return direction;
        }

        public String getProperty() {
            return property;
        }
    }

    /**
     * 运算符
     */
    public enum Operator {
        /**
         * 等于
         */
        eq(" = "),
        /**
         * 不等于
         */
        ne(" != "),
        /**
         * 大于
         */
        gt(" > "),
        /**
         * 小于
         */
        lt(" < "),
        /**
         * 大于等于
         */
        ge(" >= "),
        /**
         * 小于等于
         */
        le(" <= "),
        /**
         * 类似
         */
        like(" like "),
        /**
         * 类似
         */
        rlike(" like "),
        /**
         * 类似
         */
        llike(" like "),
        /**
         * 类似
         */
        ilike(" like "),
        /**
         * 包含
         */
        in(" in "),
        /**
         * 包含
         */
        notIn(" not in "),
        /**
         * 包含
         */
        not(" not "),
        /**
         * 为Null
         */
        isNull(" is NULL "),
        /**
         * 不为Null
         */
        isNotNull(" is not NULL ");
        Operator(String operator) {
            this.operator = operator;
        }
        private String operator;
        public String getOperator() {
            return operator;
        }
        public void setOperator(String operator) {
            this.operator = operator;
        }
    }
}
