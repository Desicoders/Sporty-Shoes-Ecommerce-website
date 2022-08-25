package com.sporty.shoes.service;

import java.lang.reflect.Field;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import com.sporty.shoes.Repositry.UserRepo;
import com.sporty.shoes.enteties.Users;
import com.sporty.shoes.exceptions.UserNotFoundException;
@org.springframework.stereotype.Service
@Transactional
public class UserServiceImpl implements Service<Users> {

	@Autowired
	UserRepo userRepo;

	@Override
	public List<Users> getAll() {
		return userRepo.findAll();
	}

	@Override
	public Users get(int id) {
		return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("no user with id = " + id));
	}

	@Override
	public void delete(int id) {
		userRepo.deleteById(id);
	}

	@Override
	public Users update(int id, String fieldname, Object FieldValue) {
		Users users = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("no user with id = " + id));
		Field field = ReflectionUtils.findField(users.getClass(), fieldname);
		field.setAccessible(true);
		ReflectionUtils.setField(field, users, FieldValue);
		userRepo.save(users);
		return users;
		

	}

	@Override
	public Users save(Users obj) {
		userRepo.saveAndFlush(obj);
		return obj;
	}
	
	public List<Users> getUsesrByName(String name)
	{
		return userRepo.findByNameContaining(name);
	}

	

	

}
