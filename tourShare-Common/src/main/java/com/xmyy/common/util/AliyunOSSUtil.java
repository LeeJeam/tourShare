package com.xmyy.common.util;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.xmyy.common.vo.FileUploadInfo;
import com.xmyy.common.vo.OSSConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import top.ibase4j.core.support.HttpCode;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;


/**
 * @class:AliyunOSSClientUtil
 * @descript:java使用阿里云OSS存储对象上传图片
 * @date:2017年3月16日 下午5:58:08
 * @author sang
 */
public class AliyunOSSUtil {
    //log日志
    private static Logger logger = Logger.getLogger(AliyunOSSUtil.class);
    //上传的文件不能超过8M
    private final static Long MAX_SIZE=8388608L; //8M
    //图片的正则表达式
    private final static String IMAGE_REGEX=".+(\\.JPEG|\\.jpeg|\\.JPG|\\.jpg|\\.GIF|\\.gif|\\.BMP|\\.bmp|\\.PNG|\\.png)$";
    //路径分隔符号
    private final static String PATH_SEPARATOR="/";
    //保存的过期时间
    private final static long EXPIRE_TIME=3600l * 1000 * 24 * 365 * 10;
    //阿里云的配置
    private static OSSConfig ossConfig=null;


    /**
     * 初始化阿里云的配置
     */
    public static void initOssConfig(){
        ossConfig = ossConfig==null?new OSSConfig():ossConfig;
    }

    /**
     * 获取阿里云OSS客户端对象
     * @return ossClient
     */
    public static OSSClient getOSSClient(){
        initOssConfig();
        return new OSSClient(ossConfig.getEndpoint(),ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
    }

    /**
     * 创建存储空间
     * @param ossClient      OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public  static String createBucketName(OSSClient ossClient,String bucketName){
        //存储空间
        final String bucketNames=bucketName;
        if(!ossClient.doesBucketExist(bucketName)){
            //创建存储空间
            Bucket bucket=ossClient.createBucket(bucketName);
            logger.info("创建存储空间成功");
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     * @param ossClient  oss对象
     * @param bucketName  存储空间
     */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){
        ossClient.deleteBucket(bucketName);
        logger.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 创建模拟文件夹
     * @param ossClient oss连接
     * @param bucketName 存储空间
     * @param folder   模拟文件夹名如"qj_nanjing/"
     * @return  文件夹名
     */
    public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
        //文件夹名
        final String keySuffixWithSlash =folder;
        //判断文件夹是否存在，不存在则创建
        if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            logger.info("创建文件夹成功");
            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir=object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param ossClient  oss连接
     * @param bucketName  存储空间
     * @param folder  模拟文件夹名 如"qj_nanjing/"
     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){
        ossClient.deleteObject(bucketName, folder + key);
        logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
    }

    /**
     * 检查上传的目录是不是有权限的
     * return true 有权限，false没有权限
     */
    public static boolean isHasPemessionForDirectory(boolean isImage,String uploadDirName){
        if(uploadDirName==null||uploadDirName.trim().equals("")){
            return false;
        }
        String pemessionDirs=isImage?ossConfig.getPemessionImageLocation():ossConfig.getPemessionFileLocation();
        if(StringUtils.isBlank(pemessionDirs)){
            throw new RuntimeException("ali-oss.properties没有配置上传目录权限");
        }
        String[] pemessionDirArr = pemessionDirs.split(",");
        return pemessionDirs.contains(uploadDirName);
    }


    /**
     * 上传图片至OSS
     * @param ossClient  oss连接
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return URL 路径
     * */
    public static  URL  uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
        URL  resultStr = null;
        try (InputStream is = new FileInputStream(file)){
            //以输入流的形式上传文件
            //文件名
            String fileName = file.getName();
            //文件大小
            Long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            //上传的文件的长度
            metadata.setContentLength(is.available());
            //指定该Object被下载时的网页的缓存行为
            metadata.setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata.setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata.setContentType(getContentType(fileName));
            //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
            metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
            //解析结果
            resultStr = getUrl(ossClient,putResult.getETag());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * 上传文件到指定的目录
     * @param fileData
     * @param fileTypeDirName
     * @param uploadDirName
     * @param fileName
     * @return
     */
    public static String uploadFile2OSS(byte[] fileData,String fileTypeDirName,String uploadDirName,String fileName) {
        String resultUrl=null;
        OSSClient ossClient = getOSSClient();
        // 以输入流的形式上传文件
        StringBuffer finalUploadUrl= new StringBuffer();
        finalUploadUrl.append(fileTypeDirName).append(PATH_SEPARATOR);
        if(uploadDirName!=null&&!uploadDirName.trim().equals("")){
            finalUploadUrl.append(uploadDirName).append(PATH_SEPARATOR);
        }
        finalUploadUrl.append(fileName);
        logger.info("上传到路径" + finalUploadUrl.toString());

        // 创建上传Object的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileData.length);
        // 指定该Object被下载时的网页的缓存行为
        metadata.setCacheControl("no-cache");
        // 指定该Object下设置Header
        metadata.setHeader("Pragma", "no-cache");
        // 指定该Object被下载时的内容编码格式
        metadata.setContentEncoding("utf-8");
        // 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
        // 如果没有扩展名则填默认值application/octet-stream
        metadata.setContentType(getContentType(fileName));
        // 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
        metadata.setContentDisposition("inline;filename=" + fileName);
        PutObjectResult putResult = getOSSClient().putObject(ossConfig.getBucketName(),
                finalUploadUrl.toString(),
                new ByteArrayInputStream(fileData),
                metadata);

        URL uploadUrl = getUrl(getOSSClient(),putResult.getETag());
        if(uploadUrl!=null){
            StringBuffer uploadResultUrl=new StringBuffer();
            uploadResultUrl.append(uploadUrl.getProtocol()).append("://");
            uploadResultUrl.append(uploadUrl.getHost());
            uploadResultUrl.append(PATH_SEPARATOR).append(finalUploadUrl);
            resultUrl=uploadResultUrl.toString();
        }
        return resultUrl;
    }

    /**
     * 获取url
     * @param ossClient
     * @param key
     * @return
     */
    public static URL getUrl(OSSClient ossClient,String key) {
        // 设置URL过期时间为10年
        Date expiration = new Date(new Date().getTime() + EXPIRE_TIME);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(ossConfig.getBucketName(), key, expiration, HttpMethod.GET);
        return url;
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static  String getContentType(String fileName){
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if(".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if(".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
            return "image/jpeg";
        }
        if(".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if(".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if(".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if(".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }


    /**
     *上传图片
     * @param image
     * @param uploadDirName
     * @return
     */
    public static FileUploadInfo commonUploadImg(MultipartFile image, String uploadDirName,boolean requiredUploadDir){
        FileUploadInfo fileUploadInfo=new FileUploadInfo();
        initOssConfig();
        try {
            long fileSize = image.getSize();
            String fileName = image.getOriginalFilename();
            fileName=fileName==null||fileName.equals("")?image.getName():fileName;
            byte[] bytes = image.getBytes();
            String fileExtName=fileName.substring(fileName.lastIndexOf("."));

            if(requiredUploadDir&&!isHasPemessionForDirectory(true,uploadDirName)){
                fileUploadInfo.setReturnCode(HttpCode.FORBIDDEN.value().toString());
                fileUploadInfo.setFailCause(String.format("当前目录%s,上传的目录没有权限",uploadDirName));
                return fileUploadInfo;
            }

            if(!fileName.matches(IMAGE_REGEX)){
                fileUploadInfo.setReturnCode(HttpCode.UNSUPPORTED_MEDIA_TYPE.value().toString());
                fileUploadInfo.setFailCause("上传的文件不是图片");
                return fileUploadInfo;
            }

            if(fileSize>MAX_SIZE){
                fileUploadInfo.setReturnCode(HttpCode.ENTITY_TOO_LARGE.value().toString());
                fileUploadInfo.setFailCause("上传图片过大,不能超过8M");
                return fileUploadInfo;
            }

            Random random = new Random();
            String imageName = random.nextInt(10000) + System.currentTimeMillis() + fileExtName;
            String imageUrl= AliyunOSSUtil.uploadFile2OSS(bytes,
                    ossConfig.getImagesSaveLocation(),
                    uploadDirName, imageName);

            if (imageUrl != null) {
                fileUploadInfo.setImageUrl(imageUrl);
            }else{
                fileUploadInfo.setFailCause("图片上传失败，请重新上传");
            }

        } catch (IOException e) {
            fileUploadInfo.setFailCause("图片上传失败，请重新上传");
            logger.error("图片上传失败,失败的原因：{}",e.getCause());
            e.printStackTrace();
        }
        return fileUploadInfo;
    }

    /**
     *上传文件
     * @param image
     * @param uploadDirName
     * @return
     */
    public static FileUploadInfo commonUploadFile(MultipartFile image, String uploadDirName,boolean requiredUploadDir){
        FileUploadInfo fileUploadInfo=new FileUploadInfo();
        initOssConfig();
        try {
            long fileSize = image.getSize();
            String fileName = image.getOriginalFilename();
            fileName=StringUtils.isBlank(fileName)?image.getName():fileName;
            byte[] bytes = image.getBytes();
            String fileExtName=fileName.substring(fileName.lastIndexOf("."));

            if(requiredUploadDir&&!isHasPemessionForDirectory(true,uploadDirName)){
                fileUploadInfo.setReturnCode(HttpCode.FORBIDDEN.value().toString());
                fileUploadInfo.setFailCause(String.format("当前目录%s,上传的目录没有权限",uploadDirName));
                return fileUploadInfo;
            }

            if(fileName.matches(IMAGE_REGEX)){
                fileUploadInfo.setReturnCode(HttpCode.UNSUPPORTED_MEDIA_TYPE.value().toString());
                fileUploadInfo.setFailCause("上传的文件是图片");
                return fileUploadInfo;
            }

            if(fileSize>MAX_SIZE){
                fileUploadInfo.setReturnCode(HttpCode.ENTITY_TOO_LARGE.value().toString());
                fileUploadInfo.setFailCause("上传文件过大,不能超过8M");
                return fileUploadInfo;
            }

            Random random = new Random();
            String imageName = random.nextInt(10000) + System.currentTimeMillis() + fileExtName;
            String imageUrl= AliyunOSSUtil.uploadFile2OSS(bytes,
                    ossConfig.getImagesSaveLocation(),
                    uploadDirName, imageName);

            if (imageUrl != null) {
                fileUploadInfo.setImageUrl(imageUrl);
            }else{
                fileUploadInfo.setFailCause("文件上传失败，请重新上传");
            }

        } catch (IOException e) {
            fileUploadInfo.setFailCause("文件上传失败，请重新上传");
            logger.error("文件上传失败,失败的原因：{}",e.getCause());
            e.printStackTrace();
        }
        return fileUploadInfo;
    }

    public static void main(String[] args)throws IOException {
        File file = new File("f:/test.jpg");
        FileInputStream imgInput = new FileInputStream(file);
        MultipartFile images = new MockMultipartFile("test.jpg",imgInput);
        FileUploadInfo fileUploadInfo = commonUploadImg(images, "testUpload",true);
    }

}