package pe.com.platformsample;

import org.alfresco.repo.model.Repository;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;


public class Utils {
	private static final StoreRef STORE_REF = StoreRef.STORE_REF_WORKSPACE_SPACESSTORE;
	private static final String BASE_PATH = "workspace/SpacesStore/Company Home";
	private Repository repository;
	
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public NodeRef getNodeByPath(String nodePath) {
		if (isBlank(nodePath))
			return null;

		nodePath = BASE_PATH + "/" + nodePath;
		return repository.findNodeRef("path", formatPath(nodePath).split("/"));
	}

	public static boolean isBlank(String ptext) {
		return ptext == null || ptext.trim().length() == 0 || ptext.trim().toLowerCase().equals("null");
	}

	public String formatPath(String path) {
		if (path.trim().startsWith("/"))
			path = path.trim().substring(1, path.length());

		if (path.trim().endsWith("/"))
			path = path.trim().substring(0, path.length() - 1);

		return path;
	}
	

}
