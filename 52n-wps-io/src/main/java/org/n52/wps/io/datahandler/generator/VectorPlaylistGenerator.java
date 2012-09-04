/***************************************************************
Copyright (C) 2012
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

package org.n52.wps.io.datahandler.generator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.n52.wps.io.data.IData;
import org.n52.wps.io.data.binding.complex.VectorPlaylistBinding;

public class VectorPlaylistGenerator extends AbstractGenerator {

	public VectorPlaylistGenerator(){
		super();
		supportedIDataTypes.add(VectorPlaylistBinding.class);	
	}
	
	public InputStream generateStream(IData data, String mimeType, String schema)
			throws IOException {
		String str;
		if (data == null){
			str = "";
		}
		else{
			str = (String) ((VectorPlaylistBinding)data).getPayload();			
		}
		InputStream is = new ByteArrayInputStream(str.getBytes());
	 
		return is;
	}

}
