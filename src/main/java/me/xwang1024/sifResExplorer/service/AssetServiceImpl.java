package me.xwang1024.sifResExplorer.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.xwang1024.sifResExplorer.config.SIFConfig;
import me.xwang1024.sifResExplorer.config.SIFConfig.ConfigName;
import me.xwang1024.sifResExplorer.data.ImagDao;
import me.xwang1024.sifResExplorer.data.impl.ImagDaoImpl;
import me.xwang1024.sifResExplorer.vo.AssetItemVO;

public class AssetServiceImpl {
	private ImagDao imageDao = new ImagDaoImpl();

	public List<AssetItemVO> getAssetList() {
		List<AssetItemVO> l = new ArrayList<AssetItemVO>();
		List<String> pathList = imageDao.getImagList();
		for (String path : pathList) {
			try {
				AssetItemVO vo = new AssetItemVO();
				String absRoot = SIFConfig.getInstance().get(ConfigName.assetsPath);
				String relativePath = path.replace('/', '\\').replace(absRoot, "").replaceAll("^\\\\image\\\\", "");
				String[] pathTree = relativePath.split("\\\\");
				String refTexturepath = imageDao.getRefTextureFilePath(path);
				vo.setImagePath(new File(path).getName());
				vo.setRefTexturepath(new File(refTexturepath).getName());
				vo.setPathTree(pathTree);
				l.add(vo);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
		return l;
	}
	
	
	public static void main(String[] args) throws Exception {
		SIFConfig.getInstance().loadConfig();
		List<AssetItemVO> l = new AssetServiceImpl().getAssetList();
		for(AssetItemVO vo: l) {
			System.out.println(vo);
		}
	}
}
