package org.pentaho.di.repository.pur;

import java.util.List;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryAttributeInterface;
import org.pentaho.di.repository.StringObjectId;

import com.pentaho.repository.pur.data.node.DataNode;
import com.pentaho.repository.pur.data.node.DataProperty;

public class PurRepositoryAttribute implements RepositoryAttributeInterface {

	private DataNode	dataNode;
	private List<DatabaseMeta>	databases;

	public PurRepositoryAttribute(DataNode dataNode, List<DatabaseMeta> databases) {
		this.dataNode = dataNode;
		this.databases = databases;
	}

	public void setAttribute(String code, String value) {
		dataNode.setProperty(code, value);
	}

	public String getAttributeString(String code) {
		DataProperty property = dataNode.getProperty(code);
		if (property!=null) {
			return property.getString();
		}
		return null;
	}

	public void setAttribute(String code, boolean value) {
		dataNode.setProperty(code, value);
	}

	public boolean getAttributeBoolean(String code) {
		DataProperty property = dataNode.getProperty(code);
		if (property!=null) {
			return property.getBoolean();
		}
		return false;
	}

	public void setAttribute(String code, long value) {
		dataNode.setProperty(code, value);
	}

	public long getAttributeInteger(String code) {
		DataProperty property = dataNode.getProperty(code);
		if (property!=null) {
			return property.getLong();
		}
		return 0L;
	}

	public void setAttribute(String code, DatabaseMeta databaseMeta) {
		dataNode.setProperty(code, databaseMeta.getObjectId().getId());
	}

	public DatabaseMeta getAttributeDatabaseMeta(String code) {
		DataProperty property =  dataNode.getProperty(code);
		if (property==null || Const.isEmpty(property.getString())) {
			return null;
		}
		ObjectId id = new StringObjectId(property.getString());
		return DatabaseMeta.findDatabase(databases, id);
	}
}