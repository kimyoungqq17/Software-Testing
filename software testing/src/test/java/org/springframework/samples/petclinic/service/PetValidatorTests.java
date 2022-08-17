package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PetValidatorTests {

	PetValidator validator = new PetValidator();

	Pet pet = new Pet();

	Errors error = new Errors() {
		@Override
		public String getObjectName() {
			return null;
		}

		@Override
		public void setNestedPath(String nestedPath) {

		}

		@Override
		public String getNestedPath() {
			return null;
		}

		@Override
		public void pushNestedPath(String subPath) {

		}

		@Override
		public void popNestedPath() throws IllegalStateException {

		}

		@Override
		public void reject(String errorCode) {

		}

		@Override
		public void reject(String errorCode, String defaultMessage) {

		}

		@Override
		public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

		}

		@Override
		public void rejectValue(String field, String errorCode) {

		}

		@Override
		public void rejectValue(String field, String errorCode, String defaultMessage) {
			System.out.println(field + " of the pet needs to be checked again");

		}

		@Override
		public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

		}

		@Override
		public void addAllErrors(Errors errors) {

		}

		@Override
		public boolean hasErrors() {
			return false;
		}

		@Override
		public int getErrorCount() {
			return 0;
		}

		@Override
		public List<ObjectError> getAllErrors() {
			return null;
		}

		@Override
		public boolean hasGlobalErrors() {
			return false;
		}

		@Override
		public int getGlobalErrorCount() {
			return 0;
		}

		@Override
		public List<ObjectError> getGlobalErrors() {
			return null;
		}

		@Override
		public ObjectError getGlobalError() {
			return null;
		}

		@Override
		public boolean hasFieldErrors() {
			return false;
		}

		@Override
		public int getFieldErrorCount() {
			return 0;
		}

		@Override
		public List<FieldError> getFieldErrors() {
			return null;
		}

		@Override
		public FieldError getFieldError() {
			return null;
		}

		@Override
		public boolean hasFieldErrors(String field) {
			return false;
		}

		@Override
		public int getFieldErrorCount(String field) {
			return 0;
		}

		@Override
		public List<FieldError> getFieldErrors(String field) {
			return null;
		}

		@Override
		public FieldError getFieldError(String field) {
			return null;
		}

		@Override
		public Object getFieldValue(String field) {
			return null;
		}

		@Override
		public Class<?> getFieldType(String field) {
			return null;
		}
	};

	@Test
	@DisplayName("path 1-2-3-4-5-6-7-8")
	public void path1() {
		pet.setName(null);
		pet.setId(null);
		pet.setType(null);
		pet.setBirthDate(null);
		validator.validate(pet, error);
	}

	@Test
	@DisplayName("path 1-2-3-4-5-6-8")
	public void test2() {
		pet.setName(null);
		pet.setType(null);
		pet.setId(null);
		pet.setBirthDate(LocalDate.now());
		validator.validate(pet, error);
	}

	@Test
	@DisplayName("path 1-2-3-4-6-7-8")
	public void test3() {
		pet.setName(null);
		pet.setType(null);
		pet.setId(1);
		pet.setBirthDate(null);
		validator.validate(pet, error);
	}

	@Test
	@DisplayName("path 1-2-3-4-6-8")
	public void test4() {
		pet.setName(null);
		pet.setType(null);
		pet.setId(1);
		pet.setBirthDate(LocalDate.now());
		validator.validate(pet, error);
	}

	@Test
	@DisplayName("path 1-2-4-5-6-7-8")
	public void test5() {
		pet.setName("Norange");
		pet.setType(null);
		pet.setId(null);
		pet.setBirthDate(null);
		validator.validate(pet, error);
	}

	@Test
	@DisplayName("path 1-2-4-5-6-8")
	public void test6() {
		pet.setName("Norange");
		pet.setType(null);
		pet.setId(null);
		pet.setBirthDate(LocalDate.now());
		validator.validate(pet, error);
	}

	@Test
	@DisplayName("path 1-2-4-6-7-8")
	public void test7() {
		pet.setName("Norange");
		pet.setType(null);
		pet.setId(1);
		pet.setBirthDate(null);
		validator.validate(pet, error);
	}

	@Test
	@DisplayName("path 1-2-4-6-8")
	public void test8() {
		pet.setName("Norange");
		pet.setType(null);
		pet.setId(1);
		pet.setBirthDate(LocalDate.now());
		validator.validate(pet, error);
	}

}
