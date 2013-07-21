/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 * 
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 * 
 * This program is free software; you can redistribute and/or modify it under 
 * the terms of the GNU General Public License version 2 as published by the 
 * Free Software Foundation.
 * 
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */

package org.n52.wps.webapp.api;

import java.util.Map;

import org.n52.wps.webapp.api.types.ConfigurationEntry;

public interface ConfigurationManager {

	/**
	 * Get all classes that implements the {@code ConfigurationModule} interface. Modules are mapped by their fully
	 * qualified name.
	 * 
	 * @return A map of all configuration modules.
	 */
	Map<String, ConfigurationModule> getAllConfigurationModules();

	/**
	 * Get all configuration modules of a particular category.
	 * 
	 * @param category
	 *            the category of the modules
	 * @return A map of all configuration modules of the specified category.
	 * @see ConfigurationCategory
	 */
	Map<String, ConfigurationModule> getAllConfigurationModulesByCategory(ConfigurationCategory category);

	/**
	 * Get only active configuration modules of a particular category.
	 * 
	 * @param category
	 *            the category of the modules
	 * @return A map of all active configuration modules of the specified category.
	 * @see ConfigurationCategory
	 */
	Map<String, ConfigurationModule> getActiveConfigurationModulesByCategory(ConfigurationCategory category);

	/**
	 * Get a configuration module by its fully qualified name.
	 * 
	 * @param moduleClassName
	 *            the fully qualified name of the module required
	 * @return The configuration module or {@code null} if no module is found.
	 */
	ConfigurationModule getConfigurationModule(String moduleClassName);

	/**
	 * Get a configuration entry.
	 * 
	 * @param moduleClassName
	 *            the fully qualified name of the module holding the configuration entry
	 * @param entryKey
	 *            the configuration entry key
	 * @return The configuration entry or {@code null} if no entry is found.
	 */
	ConfigurationEntry<?> getConfigurationEntry(String moduleClassName, String entryKey);

	/**
	 * Get the configuration entry value and return it as the expected type.
	 * 
	 * @param module
	 *            the fully qualified name of the module holding the configuration entry
	 * @param entryKey
	 *            the configuration entry key
	 * @param requiredType
	 *            the required type of the return value
	 * @return The entry value in the required type
	 * @throws WPSConfigurationException
	 *             if the entry's value type cannot be be parsed to the required type
	 */
	<T> T getConfigurationEntryValue(String moduleClassName, String entryKey, Class<T> requiredType)
			throws WPSConfigurationException;

	/**
	 * Set the value of a configuration entry. The {@code Object} value will be parsed to the entry type.
	 * 
	 * @param moduleClassName
	 *            the fully qualified name of the module holding the configuration entry
	 * @param entryKey
	 *            the entry key
	 * @param value
	 *            the value to be set
	 * @throws WPSConfigurationException
	 *             if the value cannot be parsed to the correct entry type
	 */
	void setConfigurationEntryValue(String moduleClassName, String entryKey, Object value)
			throws WPSConfigurationException;

	/**
	 * Get an algorithm entry.
	 * 
	 * @param moduleClassName
	 *            the fully qualified name of the module holding the algorithm entry
	 * @param algorithm
	 *            the algorithm name
	 * @return The algorithm entry or {@code null} if no entry is found.
	 */
	AlgorithmEntry getAlgorithmEntry(String moduleClassName, String algorithm);

	/**
	 * Set the value of an algorithm entry.
	 * 
	 * @param moduleClassName
	 *            the fully qualified name of the module holding the algorithm entry
	 * @param algorithm
	 *            the algorithm name
	 * @param active
	 *            the algorithm status
	 */
	void setAlgorithmEntry(String moduleClassName, String algorithm, boolean active);
}