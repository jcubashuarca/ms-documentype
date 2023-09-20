package com.nttdata.bootcamp.msdocumentype.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.bootcamp.msdocumentype.model.DocumentType;
import com.nttdata.bootcamp.msdocumentype.repository.DocumentTypeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentTypeService {

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	public Mono<DocumentType> create(DocumentType parameter) {
		log.debug("create executed {}", parameter);
		if (parameter.getId() == null) {
			String id = UUID.randomUUID().toString();
			parameter.setId(id);
		}
		return documentTypeRepository.save(parameter);
	}

	public Mono<DocumentType> delete(String parameterId) {
		log.debug("delete executed {}", parameterId);
		return documentTypeRepository.findById(parameterId).flatMap(existingparameter -> documentTypeRepository
				.delete(existingparameter).then(Mono.just(existingparameter)));
	}

	public Flux<DocumentType> findAll() {
		log.debug("findAll executed");
		return documentTypeRepository.findAll();
	}

	public Flux<DocumentType> findByCode(String parameter) {
		log.debug("findByName executed {}", parameter);
		return documentTypeRepository.findByCode(parameter);
	}
}
