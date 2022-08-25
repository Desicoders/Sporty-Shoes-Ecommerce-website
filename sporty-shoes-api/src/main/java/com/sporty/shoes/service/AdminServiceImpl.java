package com.sporty.shoes.service;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.sporty.shoes.Repositry.AdminRepo;
import com.sporty.shoes.enteties.Admins;
import com.sporty.shoes.exceptions.AdminNotFoundException;

@Service
public class AdminServiceImpl implements com.sporty.shoes.service.Service<Admins> {
	
	@Autowired
	AdminRepo adminRepo;

	
	@Override
	public Admins save(Admins obj) {
		return adminRepo.saveAndFlush(obj);
	}

	@Override
	public List<Admins> getAll() {
		
		return adminRepo.findAll();
	}

	@Override
	public Admins get(int id) {
		
		return adminRepo.findById(id).orElseThrow(() -> new AdminNotFoundException("No Admin with id = "+id));
	}

	@Override
	public void delete(int id) {
		adminRepo.deleteById(id);
	}

	@Override
	public Admins update(int id, String fieldname, Object FieldValue) {
		Admins admins=get(id);
		Field field=ReflectionUtils.findField(admins.getClass(), fieldname);
		field.setAccessible(true);
		ReflectionUtils.setField(field, admins, FieldValue);
		adminRepo.saveAndFlush(admins);
		return admins;
	}

}
