package com.kc.eh.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.kc.eh.common.vo.JsonResult;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("upload")
public class UploadController {

	// 测试用方法
	@RequestMapping("/springUploadtest")
	@ResponseBody
	// public JsonResult springUpload(HttpServletRequest request){
	public JsonResult springUploadtest(@RequestParam("myFile[]") MultipartFile file, Model model,
			HttpServletRequest request) {
		String message = "upload succeed";
		if (file == null) {
			message = "upload failed";
			System.out.println(message);
		} else {
			System.out.println(file.getOriginalFilename());
		}
		return new JsonResult(message);
	}

	@RequestMapping("/springUploadtest2")
	@ResponseBody
	public JsonResult springUploadtest2(HttpServletRequest request) throws IllegalStateException, IOException {
		String message = "upload failed";
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			File file1 = new File("E:/springUpload");
			if (!file1.exists()) {
				file1.mkdirs();
			}
			while (iter.hasNext()) {
				String name = iter.next().toString();
				System.out.println(name);
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(name);
				if (file != null) {
					System.out.println(file.getOriginalFilename());
					String path = "E:/springUpload/" + file.getOriginalFilename();
					// 上传
					// file.transferTo(new File(path));
				}

			}
			message = "upload succeed";
		}

		return new JsonResult(message);
	}

	@RequestMapping("/springUpload")
	@ResponseBody
	@ApiOperation("图片上传")
	public JsonResult springUpload(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
		/*
		 * MultipartHttpServletRequest: 继承于HttpServletRequest以及MultipartRequest.
		 * 其中MultipartRequest中定义了相关的访问操作. MultipartHttpServletRequest重写
		 * 了HttpServletRequest中的方法, 并进行了扩展. 如果以HttpServletRequest来接收参数,
		 * 则需要先将其转为MultipartHttpServletRequest类型 
		 * MultipartHttpServletRequest request = (MultipartHttpServletRequest) HttpServletRequest;
		 */
		
		/*
		 * 再说回刚才的form, 假设我们在单个文件选框中上传了文件1, 多个文件选框中上传了文件2, 3, 4. 
		 * 那么对于后台接收到的, 可以这么理解,就是一个Map的形式(实际上它后台真的是以Map来存储的). 
		 * 这个Map的Key是什么呢? 就是上面<input>标签中的name=""属性.
		 * Value则是我们刚才上传的文件, 通过下面的示例可以看出每一个Value就是一个包含对应文件集合的List
		 *          
		 * 传到后台接收到的Map就是这样: 
		 * fileTest: 文件1 
		 * fileList: 文件2, 文件3, 文件4
		 *          
		 * 虽然从方法名的表面意义来看是得到文件名, 但实际上这个文件名跟上传的文件本身并没有什么关系.
		 * 刚才说了这个Map的Key就是<input>标签中的name=""属性, 所以得到的也就是这个属性的值
		 */
		String message = "upload failed";
		Iterator<String> fileNames = request.getFileNames();
		while (fileNames.hasNext()) {
			//把fileName集合中的值打出来
			String fileName = fileNames.next();
			System.out.println("fileName"+fileName);
			/*
			 * request.getFiles(fileName)方法即通过fileName这个Key, 得到对应的文件 
			 * 集合列表. 只是在这个Map中,文件被包装成MultipartFile类型
			 */
			List<MultipartFile> fileList = request.getFiles(fileName);
			if (fileList.size() > 0) {
				Iterator<MultipartFile> fileIte = fileList.iterator();
				while (fileIte.hasNext()) {
					//获得每一个文件
					MultipartFile multipartFile = fileIte.next();
					//获得原文件名
					String originalFilename = multipartFile.getOriginalFilename();
					System.out.println("originalFilename"+originalFilename);
					
					//设置保存路径
					String sp1 = System.getProperty("user.dir");
					File file = new File(sp1);
					System.out.println("path"+sp1);
					String parentPath = file.getParent();
					//String path1 = sp1 + "/springUpload/";
					String path = parentPath + "/springUpload/";
					System.out.println("path"+path);
					//String path = "E:/springUpload/";
					
					//检查该路径对应的目录是否存在，如果不存在则创建目录
					File dir = new File(path);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					
					String filePath = path + originalFilename;
					System.out.println("filePath"+filePath);
					
					//保存文件
					File dest = new File(filePath);
					if (!dest.exists()) {
						/*
						 * MultipartFile提供了void transferTo(File dest)方法, 
						 * 将获取到的文件以File形式传输至指定路径.
						 */
						multipartFile.transferTo(dest);
					}
					message = "upload succeed";
				}
			}
		}

		return new JsonResult(message);
	}
	
	
	
	
}
