package com.challenge.school.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.challenge.school.domain.School;

public class SchoolSpecification {
	
	public static Specification<School> byId(Long id) {
		return (root, query, cb) -> id != null ? cb.equal(root.get("id"), id) : null;
	}
	
	public static Specification<School> hasName(String name) {
		return (root, query, cb) -> name != null ? cb.equal(root.get("name"), name) : null;
//		return (Root<School> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
//			if (name != null) {
//				return cb.equal(root.get("name"), name);
//			} else {
//				return null;
//			}
//		};
	}
	
	public static Specification<School> hasNameLike(String name) {
		return (root, query, cb) -> name != null ? cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%") : null;
	}
}
