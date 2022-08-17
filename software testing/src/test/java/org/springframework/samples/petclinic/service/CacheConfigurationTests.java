package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.samples.petclinic.persistence.OwnerRepository;
import org.springframework.samples.petclinic.persistence.PetRepository;
import org.springframework.ui.ModelMap;
import org.springframework.util.SerializationUtils;

import javax.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CacheConfigurationTests {

	CacheConfiguration cacheConfig = new CacheConfiguration();

	@Test
	@DisplayName("Testing CacheConfiguration creation")
	public void testCacheConfigurationConstructor() {
		assertNotNull(cacheConfig.petclinicCacheConfigurationCustomizer());
	}

}
