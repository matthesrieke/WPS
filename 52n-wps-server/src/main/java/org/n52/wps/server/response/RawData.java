/*****************************************************************
Copyright (C) 2007-2013
by 52 North Initiative for Geospatial Open Source Software GmbH

Contact: Andreas Wytzisk
52 North Initiative for Geospatial Open Source Software GmbH
Martin-Luther-King-Weg 24
48155 Muenster, Germany
info@52north.org

This program is free software; you can redistribute and/or modify it under 
the terms of the GNU General Public License version 2 as published by the 
Free Software Foundation.

This program is distributed WITHOUT ANY WARRANTY; even without the implied
WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program (see gnu-gpl v2.txt). If not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
visit the Free Software Foundation web page, http://www.fsf.org.

 ***************************************************************/
package org.n52.wps.server.response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.opengis.wps.x100.ProcessDescriptionType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.n52.wps.io.IOHandler;
import org.n52.wps.io.data.IBBOXData;
import org.n52.wps.io.data.IComplexData;
import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.ILiteralData;
import org.n52.wps.server.ExceptionReport;
import org.opengis.geometry.Envelope;

/*
 * @author foerster
 *
 */
public class RawData extends ResponseData {

	static Logger LOGGER = LoggerFactory.getLogger(RawData.class);
	/**
	 * @param obj
	 * @param id
	 * @param schema
	 * @param encoding
	 * @param mimeType
	 */
	public RawData(IData obj, String id, String schema, String encoding, String mimeType, String algorithmIdentifier, ProcessDescriptionType description) throws ExceptionReport{
		super(obj, id, schema, encoding, mimeType, algorithmIdentifier, description);
		if(obj instanceof IComplexData){
			prepareGenerator();
		}
		
	}
	
	public InputStream getAsStream() throws ExceptionReport {
		try {
			if(obj instanceof ILiteralData){
				String result = ""+obj.getPayload();
				 InputStream is = new ByteArrayInputStream(result.getBytes());
				 return is;
			}
			if(obj instanceof IBBOXData){
				Envelope result = (Envelope) obj.getPayload();
				String resultString  = "";
				resultString = resultString + "<wps:BoundingBoxData xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" ";
				if(result.getCoordinateReferenceSystem()!=null && result.getCoordinateReferenceSystem().getIdentifiers().size()>0){
					String crs = result.getCoordinateReferenceSystem().getIdentifiers().iterator().next().toString();
					resultString = resultString + "crs=\""+crs+"\"";
					resultString = resultString + " dimensions=\""+result.getDimension()+"\"";					
				}
				resultString = resultString + ">";
				double[] lowerCorner = result.getLowerCorner().getCoordinate();
				double[] upperCorner = result.getUpperCorner().getCoordinate();
				resultString = resultString +"<ows:LowerCorner xmlns:ows=\"http://www.opengis.net/ows/1.1\">"+lowerCorner[0]+" "+lowerCorner[1]+"</ows:LowerCorner>";
				resultString = resultString +"<ows:UpperCorner xmlns:ows=\"http://www.opengis.net/ows/1.1\">"+upperCorner[0]+" "+upperCorner[1]+"</ows:UpperCorner>";
				resultString = resultString+ "</wps:BoundingBoxData>";
				InputStream is = new ByteArrayInputStream(resultString.getBytes());
				return is;
			}
			//complexdata
			if(encoding == null || encoding == "" || encoding.equalsIgnoreCase(IOHandler.DEFAULT_ENCODING)){
				return generator.generateStream(obj, mimeType, schema);
			}
			else if(encoding.equalsIgnoreCase(IOHandler.ENCODING_BASE64)){
				return generator.generateBase64Stream(obj, mimeType, schema);
				
			}
		} catch (IOException e) {
			throw new ExceptionReport("Error while generating Complex Data out of the process result", ExceptionReport.NO_APPLICABLE_CODE, e);
		}
		throw new ExceptionReport("Could not determine encoding. Use default (=not set) or base64", ExceptionReport.NO_APPLICABLE_CODE);
	}

	
}
