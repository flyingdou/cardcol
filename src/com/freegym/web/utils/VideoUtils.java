package com.freegym.web.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.util.Log;

/**
 * 获取视频缩略图
 * 
 * @author WH1307018
 * 
 */
public class VideoUtils {

	/**
	 * 功能函数
	 * 
	 * @param inputFile
	 *            待处理视频，需带路径
	 * @return
	 * @throws IOException
	 */
	public static boolean convert(String inputFile, String newimg, String webPath) {
		if (!checkfile(inputFile)) {
			// System.out.println(inputFile + " is not file");
			return false;
		}
		try {
			if (process(inputFile, newimg, webPath)) {
				// System.out.println("ok");
				return true;
			}
		} catch (Exception e) {
			Log.error("convert video fail", e);
			return false;
		}
		return true;
	}

	// 检查文件是否存在
	private static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) { return false; }
		return true;
	}

	/**
	 * 转换过程 ：先检查文件类型，在决定调用 processFlv还是processAVI
	 * 
	 * @param inputFile
	 * @param outputFile
	 * @return
	 * @throws IOException
	 */
	private static boolean process(String inputFile, String newimg, String webPath) throws IOException {
		int type = checkContentType(inputFile);
		boolean status = false;
		if (type == 0) {
			String inputType = inputFile.substring(inputFile.lastIndexOf(".") + 1, inputFile.length()).toLowerCase();
			if (inputType.equals("flv")) {
				processImg(inputFile, newimg, webPath);
				// System.out.println("图片生成完成！！");
			} else {
				processImg(inputFile, newimg, webPath);
				// System.out.println("直接转型后图片生成完成！");
			}
		} else if (type == 1) {
			processImg(inputFile, newimg, webPath);
		} else {
			processImg(inputFile, newimg, webPath);
		}
		return status;
	}

	/**
	 * 检查视频类型
	 * 
	 * @param inputFile
	 * @return ffmpeg 能解析返回0，不能解析返回1
	 */
	private static int checkContentType(String inputFile) {
		String type = inputFile.substring(inputFile.lastIndexOf(".") + 1, inputFile.length()).toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		// 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) { return 1; }
		return 9;
	}

	/**
	 * 截图
	 * 
	 * @param inputFile
	 * @param newimg
	 * @throws IOException
	 */
	public static void processImg(String inputFile, String newimg, String webPath) throws IOException {
		List<String> commend = new java.util.ArrayList<String>();

		commend.add(webPath + "ffmpeg\\ffmpeg.exe");
		commend.add("-i");
		commend.add(inputFile);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("2");
		commend.add("-t");
		commend.add("0.001");
		commend.add("-s");
		commend.add("100x100");
		commend.add(newimg);

		StringBuffer test = new StringBuffer();
		for (int i = 0; i < commend.size(); i++)
			test.append(commend.get(i) + " ");
		// System.out.println("图片生成命令：" + test.toString());
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(commend);
		builder.start();
	}
}
