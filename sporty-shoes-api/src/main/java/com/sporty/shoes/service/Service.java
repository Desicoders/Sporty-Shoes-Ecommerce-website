package com.sporty.shoes.service;

import java.util.List;

public interface Service<E> {
	E save(E obj);
	List <E> getAll();
	E get(int id);
	void delete(int id);
	E update(int id,String fieldname,Object FieldValue); 

}
