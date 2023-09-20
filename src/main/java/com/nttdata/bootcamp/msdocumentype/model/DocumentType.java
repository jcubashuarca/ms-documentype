package com.nttdata.bootcamp.msdocumentype.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentType {
	
	@Id
    private String id;
    
	private String code;
    
    private String description;

}
