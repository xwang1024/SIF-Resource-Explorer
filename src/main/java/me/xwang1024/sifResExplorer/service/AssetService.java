package me.xwang1024.sifResExplorer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import me.xwang1024.sifResExplorer.config.SIFConfig;
import me.xwang1024.sifResExplorer.config.SIFConfig.ConfigName;
import me.xwang1024.sifResExplorer.data.ImagDao;
import me.xwang1024.sifResExplorer.data.impl.ImagDaoImpl;
import me.xwang1024.sifResExplorer.vo.AssetItemVO;

public class AssetService {
	private static AssetService instance;
	private ImagDao imageDao = new ImagDaoImpl();

	private List<AssetItemVO> assetList;
	private PathNode root;

	private AssetService() {
	}

	public static AssetService getInstance() {
		if (instance == null) {
			instance = new AssetService();
		}
		return instance;
	}

	private void initAssetList() {
		assetList = new ArrayList<AssetItemVO>();
		List<String> texbList = imageDao.getTexbList();
		HashSet<String> texbSet = new HashSet<String>();
		for (String path : texbList) {
			String absRoot = SIFConfig.getInstance().get(ConfigName.assetsPath);
			String relativePath = "assets"
					+ path.replace('\\', '/').replace(absRoot.replace('\\', '/'), "");
			texbSet.add(relativePath);
		}
		List<String> imagList = imageDao.getImagList();
		for (String path : imagList) {
			try {
				AssetItemVO vo = new AssetItemVO();
				String absRoot = SIFConfig.getInstance().get(ConfigName.assetsPath);
				String relativePath = "assets"
						+ path.replace('\\', '/').replace(absRoot.replace('\\', '/'), "");
				String refTexturepath = imageDao.getRefTextureFilePath(path);
				vo.setImageFilePath(relativePath);
				vo.setRefTextureFilePath(refTexturepath);
				assetList.add(vo);
				texbSet.remove(refTexturepath);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
		for (String path : texbSet) {
			AssetItemVO vo = new AssetItemVO();
			vo.setImageFilePath("<Unknown>");
			vo.setRefTextureFilePath(path);
			assetList.add(vo);
		}
	}

	private void initPathNode() {
		if (assetList == null) {
			initAssetList();
		}
		root = new PathNode("Root");
		for (AssetItemVO vo : assetList) {
			String path = vo.getImageFilePath();
			path = new String(path.replaceAll("assets/", ""));
			String[] sp = path.split("/");
			Map<String, PathNode> current = root.getChildren();
			for (int i = 0; (sp.length > 1 && i < sp.length - 1)
					|| (sp.length == 1 && i < sp.length); i++) {
				if (!current.containsKey(sp[i])) {
					PathNode child = new PathNode(sp[i]);
					current.put(sp[i], child);
				}
				current = current.get(sp[i]).getChildren();
			}
		}
	}

	public static void printTree(final PathNode node, String blank) {
		System.out.println(blank + node.getName());
		Iterator<Entry<String, PathNode>> iter = node.children.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, PathNode> entry = iter.next();
			PathNode v = entry.getValue();
			printTree(v, " " + blank);
		}
	}

	public List<AssetItemVO> getAssetList() {
		if (assetList == null) {
			initAssetList();
		}
		return assetList;
	}

	public PathNode getRootNode() {
		if (assetList == null) {
			initAssetList();
		}
		if (root == null) {
			initPathNode();
		}
		return root;
	}

	public List<AssetItemVO> getAssetListByPath(String keywords, String... path) {
		if (assetList == null) {
			initAssetList();
		}
		List<AssetItemVO> list = new ArrayList<AssetItemVO>();
		if (path == null || path.length == 0) {
			list.addAll(assetList);
		} else if (path[0].equals("<Unknown>")) {
			for (AssetItemVO vo : assetList) {
				if (vo.getImageFilePath().equals(path[0])) {
					if (vo.getImageFilePath().contains(keywords)) {
						list.add(vo);
					}
				}
			}
		} else {
			for (AssetItemVO vo : assetList) {
				String[] sp = vo.getImageFilePath().replace("\\", "/").split("/");
				boolean flag = true;
				for (int i = 0; i < path.length; i++) {
					if (!(i + 1 < sp.length && sp[i + 1].equals(path[i]))) {
						flag = false;
						break;
					}
				}
				if (flag) {
					list.add(vo);
				}
			}
		}
		if (keywords != null && !keywords.equals("")) {
			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).getImageFilePath().contains(keywords)) {
					list.remove(i);
					i--;
				}
			}
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		SIFConfig.getInstance().loadConfig();
		// for (AssetItemVO vo : l) {
		// System.out.println(vo);
		// }
		AssetService service = AssetService.getInstance();
		// PathNode node = service.getRootNode();
		// AssetService.printTree(node, " ");
		List<AssetItemVO> l = service.getAssetListByPath("<Unknown>");
		for (AssetItemVO vo : l) {
			System.out.println(vo);
		}
	}

	public static class PathNode {
		String name;
		Map<String, PathNode> children = new HashMap<String, PathNode>();

		PathNode(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Map<String, PathNode> getChildren() {
			return children;
		}

		public void setChildren(Map<String, PathNode> children) {
			this.children = children;
		}

	}
}
