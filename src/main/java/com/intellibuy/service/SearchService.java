package com.intellibuy.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.search.spell.LuceneLevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intellibuy.entity.EntityToDB;
import com.intellibuy.entity.Product;

@Service
public class SearchService {
	
	private final double cutoff = 0.5;
	private LuceneLevenshteinDistance LLD = new LuceneLevenshteinDistance();
	@Autowired
	private JdbcService jdbcService;
	
	public class SearchQuery<T extends EntityToDB> implements Comparable<SearchQuery<T>>{
		
		private T entity;
		private Double similarity;

		public T getEntity() {
			return entity;
		}
		
		public SearchQuery(T entity, Double similarity) {
			super();
			this.entity = entity;
			this.similarity = similarity;
		}
		@Override
		public int compareTo(SearchQuery<T> o) {
			return 0 - (this.similarity.compareTo(o.similarity));
		}
		
	}
	
	public Product[] searchProduct(String name){
		List<Product> prods = jdbcService.findAll(new Product());
		List<SearchQuery<Product>> query = new ArrayList<>();
		for (Product prod: prods) {
			Double sim = similar(name, prod.getName());
			if (sim > cutoff) {
				query.add(new SearchQuery<Product>(prod, sim));	
			}
		}
		Object[] searchQ = query.toArray();
		Arrays.sort(searchQ);
		Product[] p = new Product[searchQ.length];
		for (int i = 0; i < searchQ.length; i++) {
			p[i] = ((SearchQuery<Product>) searchQ[i]).getEntity();
		}
		return p;
	}

	private Double similar(String input, String target) {
		input.trim();
		String[] sArray = input.split("\\s+");
		Double distance = 0.0;
		for (String s: sArray) {
			distance += similar0(s, target);
		}
		System.out.println(input + "<-->" + target + " : " + distance/sArray.length);
		return distance/sArray.length;
	}

	private Double similar0(String input, String target) {
		target.trim();
		String[] targetArray = target.split("\\s+");
		Double distance = 0.0;
		for (String s: targetArray) {
			distance = Math.max(distance, LLD.getDistance(input, s));
		}
		return distance;
	}
	

}
