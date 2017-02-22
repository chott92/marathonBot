/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package de.chott.marathonbot.service.util;

import de.chott.marathonbot.service.SingletonService;

/**
 *
 * @author chot
 */
public class UtilService implements SingletonService {

	public String getAppFolderFilepath() {
		String jarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String[] split = jarPath.split("/");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < split.length - 1; i++) {
			sb.append(split[i]).append("/");
		}
		return sb.toString();
	}

}
