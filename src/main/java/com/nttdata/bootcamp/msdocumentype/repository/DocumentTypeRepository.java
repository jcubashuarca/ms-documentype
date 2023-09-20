package com.nttdata.bootcamp.msdocumentype.repository;


import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;

import com.nttdata.bootcamp.msdocumentype.model.DocumentType;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class DocumentTypeRepository {
	
	private final ReactiveRedisOperations<String,  DocumentType> reactiveRedisOperations;

	public Mono<DocumentType> save(DocumentType documentType) {
		return this.reactiveRedisOperations.<String, DocumentType>opsForHash()
				.put("documenttypes", documentType.getId(), documentType).log().map(p -> documentType);
	}
	
	public Mono<DocumentType> delete(DocumentType documentType) {
		return this.reactiveRedisOperations.<String, DocumentType>opsForHash().remove("documenttypes", documentType.getId())
				.flatMap(p -> Mono.just(documentType));
	}
	
	public Flux<DocumentType> findAll() {
		return this.reactiveRedisOperations.<String, DocumentType>opsForHash().values("documenttypes");
	}
	
	public Flux<DocumentType> findByCode(String parameter) {
		return this.findAll().filter(p -> p.getCode().equals(parameter));
	}
	
	public Mono<DocumentType> findById(String id) {
		return this.reactiveRedisOperations.<String, DocumentType>opsForHash().get("documenttypes", id);
	}

}
