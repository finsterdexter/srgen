package cz.eowyn.srgen.io;

import java.io.*;

import cz.eowyn.srgen.model.Repository;
import cz.eowyn.srgen.model.RepositoryTree;
import java.util.Hashtable;

public abstract class Loader {
	protected Repository repository = null;

	public Loader (Repository repository) {
		this.repository = repository;
	}

	public abstract void ImportFile (String filename, RepositoryTree root, Class objectClass, Hashtable formats_map) throws IOException;
}
