package pers.missionlee.utils;

import com.sun.xml.internal.bind.v2.TODO;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @description: scan the implementation of the certain class under the given package
 * @author: Mission Lee
 * @Origin: https://blog.csdn.net/u013871439/article/details/70231288
 * @create: 2018-12-27 14:29
 */
public class ImplementationClassScanner {
    public static List<Class<?>> scan(Class theInterface, Package thePackage) {
        List<Class<?>> returnList = null;
        // check theInterface is an interface
        if (theInterface.isInterface()) {

            List<Class<?>> allClassUnderPackage = scan(thePackage,true);
            if(allClassUnderPackage!=null){
                returnList = new ArrayList();
                for(Class<?> cls:allClassUnderPackage){
                    if(theInterface.isAssignableFrom(cls)){
                        if(!cls.isInterface())
                            returnList.add(cls);
                    }
                }
            }
        }
        return returnList;
    }
    public  static List<Class<?>> scan(Package thePackage,Boolean recursive){
        List<Class<?>> classes = new ArrayList<>();

        String packageName = thePackage.getName();
        String packageDirName = packageName.replace(".","/");
        Enumeration<URL> dirs;
        try{
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if("file".equals(protocol)){
                    String filePath = URLDecoder.decode(url.getFile(),"UTF-8");
                    // TODO: 2018/12/27 findAndAddClassesInPackageByFile
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);

                }else if("jar".equals(protocol)){
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
