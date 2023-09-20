package com.nttdata.bootcamp.msdocumentype.application;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.java.parameter.web.model.ParameterModel;
import com.nttdata.bootcamp.msdocumentype.model.DocumentType;
import com.nttdata.bootcamp.msdocumentype.service.DocumentTypeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/documenttype")
public class DocumentTypeController {

	@Value("${spring.application.name}")
	String name;

	@Value("${server.port}")
	String port;

	@Autowired
	private DocumentTypeService documentTypeService;

	@PostMapping
	public Mono<ResponseEntity<DocumentType>> create(@RequestBody DocumentType request) {
		log.info("create executed {}", request);
		return documentTypeService.create(request)
				.flatMap(c -> Mono.just(ResponseEntity
						.created(URI.create(String.format("http://%s:%s/%s/%s", name, port, "DocumentType", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping("/code/{code}")
	public Mono<ResponseEntity<Flux<DocumentType>>> findByCode(@PathVariable String code) {
		log.info("getByName executed {}", code);
		return Mono.just(ResponseEntity.ok().body(documentTypeService.findByCode(code)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id) {
		log.info("deleteById executed {}", id);
		return documentTypeService.delete(id).map(r -> ResponseEntity.ok().<Void>build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping
	public Mono<ResponseEntity<Flux<DocumentType>>> getAll() {
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok().body(documentTypeService.findAll()));

	}
}
