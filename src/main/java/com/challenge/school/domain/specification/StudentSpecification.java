package com.challenge.school.domain.specification;

import org.springframework.data.jpa.domain.Specification;

import com.challenge.school.domain.Student;


public class StudentSpecification {
	
	public static Specification<Student> byId(Long id) {
		return (root, query, cb) -> id != null ? cb.equal(root.get("id"), id) : null;
	}
	
	public static Specification<Student> hasName(String name) {
		return (root, query, cb) -> name != null ? cb.equal(root.get("name"), name) : null;
	}
	
	public static Specification<Student> hasNameLike(String name) {
		return (root, query, cb) -> name != null ? cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%") : null;
	}
}
