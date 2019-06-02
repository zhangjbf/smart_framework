package org.smart4j.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public final class ClassUtil {

    /**
     * 获取类加载器（直接从当前线程上下文中获取）
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Class<?> loadClass(String className) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    /**
     * 加载类
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> aClass = null;
        try {
            aClass = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    /**
     * 加载指定包路径下的所有类
     *
     * @param
     * @return
     * @author jiabin.zhang 张佳宾
     * @date 2019/6/2
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        Set<Class<?>> classSet = new HashSet<>();
        try {
            Enumeration<URL> resources = getClassLoader().getResources(packageName.replace(".", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if (null != url) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet, packagePath, packageName);
                    } else if ("jar".equals(protocol)) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (null != jarURLConnection) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (null != jarFile) {
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while (entries.hasMoreElements()) {
                                    JarEntry jarEntry = entries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.endsWith(".class")) {
                                        String className = jarEntryName.substring(0, jarEntryName.indexOf("."))
                                            .replaceAll("/", ".");
                                        doAddClass(classSet, className);

                                    }
                                }

                            }

                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classSet;
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        classSet.add(loadClass(className, false));
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        if (null != files && files.length > 0) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile()) {
                    String className = fileName.substring(0, fileName.indexOf(".")).replaceAll("/", ".");
                    doAddClass(classSet, packageName + "." + className);
                } else {
                    String subPackagePath = fileName;
                    if (StringUtil.isNotEmpty(packagePath)) {
                        subPackagePath = packagePath + "/" + subPackagePath;
                    }
                    String subPackageName = fileName;
                    if (StringUtil.isNotEmpty(packageName)) {
                        subPackageName = packageName + "." + subPackageName;
                    }
                    addClass(classSet, subPackagePath, subPackageName);
                }
            }
        }

    }

}
