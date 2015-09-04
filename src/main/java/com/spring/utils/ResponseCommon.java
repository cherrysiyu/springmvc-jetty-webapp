package com.spring.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;



/**
 * @Description :Response公用方法类
 * @author: Cherry
 * @version 1.0
 * @data:Oct 19, 2011
 */
public class ResponseCommon {

	/**
	 * 输出JSON数据，Object不需要转换，方法内部自己转换
	 * @param response
	 * @param object
	 * @throws Exception
	 */
	public static void printJSON(HttpServletResponse response, Object object)throws IOException {
		response.setContentType("application/json");
		printObject(response,JacksonUtils.getInstance().writerJavaObject2JSON(object));
	}

	/**
	 * 不负责转换object的类容，如果需要转换则自行转换
	 * @param response
	 * @param object
	 * @throws IOException
	 */
	public static void printObject(HttpServletResponse response, Object object)throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Content-Encoding", "gzip");
		PrintWriter pw = getGzipPrintWriter(response);
		pw.print(object);
		pw.close();
	}

	/**
	 * 调用这个之前，请DataExportUtils.exportDatas2Excel(dataBean),这样页面就可以了
	 * 媒体返回类型为Excel
	 * @param response
	 * @param fileName 要导出的文件名称
	 * @throws IOException
	 */
	public static void responseExcel(HttpServletResponse response,String fileName) throws IOException {
		responseContentDisposition(response, fileName, "application/msexcel");
	}

	/**
	 * 返回文件
	 * @param response
	 * @param fileName 文件路径
	 * @throws IOException
	 */
	public static void responseFile(HttpServletResponse response,String fileName) throws IOException {
		responseContentDisposition(response, fileName,"application/octet-stream");
	}

	/**
	 * 调用这个之前，请DataExportUtils.exportDatas2CSV(dataBean),这样页面就可以了
	 * 媒体返回类型为csv
	 * @param response
	 * @param fileName 要导出的文件名称
	 * @throws IOException
	 */
	public static void responseCsv(HttpServletResponse response, String fileName)throws IOException {
		responseContentDisposition(response, fileName, "application/csv");
	}

	/**
	 * response ContentDisposition 以附件形式返回文件
	 * @param response
	 * @param fileName 文件名
	 * @param ContentType response ContentType HTTP内容类型
	 * @throws IOException
	 */
	public static void responseContentDisposition(HttpServletResponse response,
			String fileName, String ContentType) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-disposition", "attachment;filename="+ fileName);
		response.setContentType(ContentType);

		outputFile(response, fileName);
	}

	/**
	 * 发送文本。使用UTF-8编码。
	 * @param response   HttpServletResponse
	 * @param text 发送的字符串
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "text/plain;charset=UTF-8", text);
	}

	/**
	 * 发送json。使用UTF-8编码。
	 * @param response  HttpServletResponse
	 * @param text  发送的字符串
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	/**
	 * 发送xml。使用UTF-8编码。
	 * @param response  HttpServletResponse
	 * @param text   发送的字符串
	 */
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "text/xml;charset=UTF-8", text);
	}

	/**
	 * 发送内容。使用UTF-8编码。
	 * 
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void render(HttpServletResponse response, String contentType,String text) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		try {
			response.getWriter().write(text);
			response.getWriter().close();
		} catch (IOException e) {
		}
	}
	/**
	 * 输出文件
	 * @param response
	 * @param fileName 文件名
	 * @throws IOException
	 */
	private static void outputFile(HttpServletResponse response, String fileName)throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName)); 
		int i = 0;
		while ((i = bis.read()) != -1)
			response.getOutputStream().write(i);

		bis.close();
		response.getOutputStream().close();
	}

	/**
	 * 得到Gzip方式的输出
	 * @param response
	 * @return Gzip PrintWriter
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private static PrintWriter getGzipPrintWriter(HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		return new PrintWriter(new OutputStreamWriter(new GZIPOutputStream(new BufferedOutputStream(response.getOutputStream())), "UTF-8"));
	}
	
}
