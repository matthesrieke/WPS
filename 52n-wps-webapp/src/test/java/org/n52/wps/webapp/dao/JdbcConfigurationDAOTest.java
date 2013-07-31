package org.n52.wps.webapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;
import org.n52.wps.webapp.api.AlgorithmEntry;
import org.n52.wps.webapp.common.AbstractTest;
import org.n52.wps.webapp.testmodules.TestConfigurationModule1;
import org.n52.wps.webapp.testmodules.TestConfigurationModule2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@ActiveProfiles("test")
public class JdbcConfigurationDAOTest extends AbstractTest  {

	@Autowired
	private ConfigurationDAO configurationDAO;

	private String moduleClassName = TestConfigurationModule1.class.getName();
	private String module2ClassName = TestConfigurationModule2.class.getName();

	@Test
	public void testGetConfigurationEntryValue() throws URISyntaxException {
		Object stringValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.string.key");
		assertEquals("Test Value", stringValue);
		
		Object integerValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.integer.key");
		assertEquals(23, Integer.parseInt(integerValue.toString()));
		
		Object doubleValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.double.key");
		assertEquals(11.3, Double.parseDouble(doubleValue.toString()), 0);
		
		Object booleanValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.boolean.key");
		assertEquals(true, Boolean.parseBoolean(booleanValue.toString()));
		
		Object fileValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.file.key");
		assertEquals(new File("test_path"), new File(fileValue.toString()));
		
		Object uriValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.uri.key");
		assertEquals(new URI("test_path"),  new URI(uriValue.toString()));
		
		Object nullValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "non.existing.entry");
		assertNull(nullValue);
	}

	@Test
	public void testInsertConfigurationEntryValue() throws URISyntaxException {
		assertNull(configurationDAO.getConfigurationEntryValue(module2ClassName, "test.string.key"));
		configurationDAO.insertConfigurationEntryValue(module2ClassName, "test.string.key", "inserted string");
		Object stringValue = configurationDAO.getConfigurationEntryValue(module2ClassName, "test.string.key");
		assertEquals("inserted string", stringValue);
		
		assertNull(configurationDAO.getConfigurationEntryValue(module2ClassName, "test.integer.key"));
		configurationDAO.insertConfigurationEntryValue(module2ClassName, "test.integer.key", 99);
		Object integerValue = configurationDAO.getConfigurationEntryValue(module2ClassName, "test.integer.key");
		assertEquals(99, Integer.parseInt(integerValue.toString()));
		
		assertNull(configurationDAO.getConfigurationEntryValue(module2ClassName, "test.double.key"));
		configurationDAO.insertConfigurationEntryValue(module2ClassName, "test.double.key", 99.9);
		Object doubleValue = configurationDAO.getConfigurationEntryValue(module2ClassName, "test.double.key");
		assertEquals(99.9, Double.parseDouble(doubleValue.toString()), 0);
		
		assertNull(configurationDAO.getConfigurationEntryValue(module2ClassName, "test.boolean.key"));
		configurationDAO.insertConfigurationEntryValue(module2ClassName, "test.boolean.key", false);
		Object booleanValue = configurationDAO.getConfigurationEntryValue(module2ClassName, "test.boolean.key");
		assertEquals(false, Boolean.parseBoolean(booleanValue.toString()));
		
		assertNull(configurationDAO.getConfigurationEntryValue(module2ClassName, "test.file.key"));
		configurationDAO.insertConfigurationEntryValue(module2ClassName, "test.file.key", "inserted_path");
		Object fileValue = configurationDAO.getConfigurationEntryValue(module2ClassName, "test.file.key");
		assertEquals(new File("inserted_path"), new File(fileValue.toString()));
		
		assertNull(configurationDAO.getConfigurationEntryValue(module2ClassName, "test.uri.key"));
		configurationDAO.insertConfigurationEntryValue(module2ClassName, "test.uri.key", "inserted_path");
		Object uriValue = configurationDAO.getConfigurationEntryValue(module2ClassName, "test.uri.key");
		assertEquals(new URI("inserted_path"),  new URI(uriValue.toString()));
	}
	
	@Test
	public void testUpdateConfigurationEntryValue() throws URISyntaxException {
		
		Object stringValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.string.key");
		assertEquals("Test Value", stringValue);
		configurationDAO.updateConfigurationEntryValue(moduleClassName, "test.string.key", "inserted string");
		stringValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.string.key");
		assertEquals("inserted string", stringValue);
		
		Object integerValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.integer.key");
		assertEquals(23, Integer.parseInt(integerValue.toString()));
		configurationDAO.updateConfigurationEntryValue(moduleClassName, "test.integer.key", 99);
		integerValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.integer.key");
		assertEquals(99, Integer.parseInt(integerValue.toString()));
		
		Object doubleValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.double.key");
		assertEquals(11.3, Double.parseDouble(doubleValue.toString()), 0);
		configurationDAO.updateConfigurationEntryValue(moduleClassName, "test.double.key", 99.9);
		doubleValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.double.key");
		assertEquals(99.9, Double.parseDouble(doubleValue.toString()), 0);
		
		Object booleanValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.boolean.key");
		assertEquals(true, Boolean.parseBoolean(booleanValue.toString()));
		configurationDAO.updateConfigurationEntryValue(moduleClassName, "test.boolean.key", false);
		booleanValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.boolean.key");
		assertEquals(false, Boolean.parseBoolean(booleanValue.toString()));
		
		Object fileValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.file.key");
		assertEquals(new File("test_path"), new File(fileValue.toString()));
		configurationDAO.updateConfigurationEntryValue(moduleClassName, "test.file.key", "inserted_path");
		fileValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.file.key");
		assertEquals(new File("inserted_path"), new File(fileValue.toString()));
		
		Object uriValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.uri.key");
		assertEquals(new URI("test_path"),  new URI(uriValue.toString()));
		configurationDAO.updateConfigurationEntryValue(moduleClassName, "test.uri.key", "inserted_path");
		uriValue = configurationDAO.getConfigurationEntryValue(moduleClassName, "test.uri.key");
		assertEquals(new URI("inserted_path"),  new URI(uriValue.toString()));
	}

	@Test
	public void testGetAlgorithmEntry() {
		AlgorithmEntry entry1 = configurationDAO.getAlgorithmEntry(moduleClassName, "name1");
		assertEquals("name1", entry1.getAlgorithm());
		assertTrue(entry1.isActive());
		
		AlgorithmEntry entry2 = configurationDAO.getAlgorithmEntry(moduleClassName, "name2");
		assertEquals("name2", entry2.getAlgorithm());
		assertTrue(entry2.isActive());
	}

	@Test
	public void testInsertAlgorithmEntry() {
		assertNull(configurationDAO.getAlgorithmEntry(module2ClassName, "name1"));
		configurationDAO.insertAlgorithmEntry(module2ClassName, "name1", false);
		AlgorithmEntry entry1 = configurationDAO.getAlgorithmEntry(module2ClassName, "name1");
		assertEquals("name1", entry1.getAlgorithm());
		assertFalse(entry1.isActive());
	}
	
	@Test
	public void testUpdateAlgorithmEntry() {
		AlgorithmEntry entry1 = configurationDAO.getAlgorithmEntry(moduleClassName, "name1");
		assertTrue(entry1.isActive());
		configurationDAO.updateAlgorithmEntry(moduleClassName, "name1", false);
		entry1 = configurationDAO.getAlgorithmEntry(moduleClassName, "name1");
		assertFalse(entry1.isActive());
	}
}