package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Product;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> searchProducts(String name, Integer largeCategoryId, Integer middleCategoryId, Integer smallCategoryId, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        // 結合
        Join<?, ?> smallCategory = product.join("categorySmall", JoinType.LEFT);
        Join<?, ?> middleCategory = smallCategory.join("middleCategory", JoinType.LEFT);
        Join<?, ?> largeCategory = middleCategory.join("largeCategory", JoinType.LEFT);

        // 検索条件
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(product.get("name"), "%" + name + "%"));
        }

        if (smallCategoryId != null) {
            predicates.add(cb.equal(smallCategory.get("id"), smallCategoryId));
        }

        if (middleCategoryId != null) {
            predicates.add(cb.equal(middleCategory.get("id"), middleCategoryId));
        }

        if (largeCategoryId != null) {
            predicates.add(cb.equal(largeCategory.get("id"), largeCategoryId));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(product.get("id")));

        // ページング付きのクエリ
        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        // 件数取得
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        Join<?, ?> countSmall = countRoot.join("categorySmall", JoinType.LEFT);
        Join<?, ?> countMiddle = countSmall.join("middleCategory", JoinType.LEFT);
        Join<?, ?> countLarge = countMiddle.join("largeCategory", JoinType.LEFT);

        countQuery.select(cb.count(countRoot));
        List<Predicate> countPredicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            countPredicates.add(cb.like(countRoot.get("name"), "%" + name + "%"));
        }
        if (smallCategoryId != null) {
            countPredicates.add(cb.equal(countSmall.get("id"), smallCategoryId));
        }
        if (middleCategoryId != null) {
            countPredicates.add(cb.equal(countMiddle.get("id"), middleCategoryId));
        }
        if (largeCategoryId != null) {
            countPredicates.add(cb.equal(countLarge.get("id"), largeCategoryId));
        }

        countQuery.where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(typedQuery.getResultList(), pageable, total);
    }
}