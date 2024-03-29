package com.baidu.gcrm.common.auth;

import java.util.SortedSet;

import com.baidu.rigel.crm.rights.utils.PathPatternMatcher;

public class UrlUtils {
	/**
	 * 匹配路径是否在控制域的范围内
	 * 
	 * @param urls
	 * @param path
	 * @param urlPattern
	 * @return
	 */
	public static boolean urlMatch(SortedSet<String> urls, String path) {

		if (urls == null || urls.size() == 0)
			return false;

		SortedSet<String> hurl = urls.headSet(path + "\0");
		SortedSet<String> turl = urls.tailSet(path + "\0");

		if (hurl.size() > 0) {
			String before = hurl.last();
			if (pathMatch(path, before))
				return true;
		}

		if (turl.size() > 0) {
			String after = turl.first();
			if (pathMatch(path, after))
				return true;
		}

		return false;
	}

	/**
	 * 匹配路径是否在控制域的范围内
	 * 
	 * @param path
	 * @param domain
	 * @return
	 */
	private static boolean pathMatch(String path, String domain) {
		if (PathPatternMatcher.isPattern(domain)) {
			return PathPatternMatcher.match(domain, path);
		} else {
			return domain.equals(path);
		}
	}
}
