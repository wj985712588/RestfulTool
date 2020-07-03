package cn.mhonor.utils.scanner;

import cn.mhonor.annotation.JarsHttpMethodAnnotation;
import cn.mhonor.beans.HttpMethod;
import cn.mhonor.beans.Request;
import cn.mhonor.utils.RestUtil;
import com.intellij.lang.jvm.annotation.JvmAnnotationAttribute;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.java.stubs.index.JavaAnnotationIndex;
import com.intellij.psi.impl.java.stubs.index.JavaShortClassNameIndex;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author mhonor
 * @version 1.0
 */
public class JarsHelper {

    @NotNull
    public static List<Request> getJarsRequestByModule(@NotNull Project project, @NotNull Module module) {
        List<Request> requests = new ArrayList<>();
        for (PsiClassBean psiClassBean : scanHasPathFiles(project, module)) {
            requests.addAll(getRequestsFromClass(psiClassBean.rootPath, psiClassBean.psiClass));
        }
        return requests;
    }

    @NotNull
    private static List<PsiClassBean> scanHasPathFiles(@NotNull Project project, @NotNull Module module) {
        Set<PsiClassBean> classSets = new HashSet<>();

        XmlFile applicationContext = findConfigXmlFile(project, module);
        if (applicationContext != null) {
            classSets.addAll(parseApplicationContextXml(project, module, applicationContext));
        }
        Collection<PsiAnnotation> pathList = JavaAnnotationIndex.getInstance().get(
                Control.Path.getName(),
                project,
                RestUtil.getModuleScope(module)
        );
        for (PsiAnnotation psiAnnotation : pathList) {
            PsiElement psiElement = psiAnnotation.getParent().getParent();

            if (!(psiElement instanceof PsiClass)) {
                continue;
            }

            PsiClass psiClass = (PsiClass) psiElement;
            classSets.add(new PsiClassBean(getRootPathOfClass(psiClass), psiClass));
        }
        return new ArrayList<>(classSets);
    }

    @NotNull
    private static List<Request> getRequestsFromClass(@Nullable String rootPath, @NotNull PsiClass psiClass) {
        String rootPathOfClass = getRootPathOfClass(psiClass);
        if (rootPathOfClass != null) {
            rootPath = rootPathOfClass;
        }

        List<Request> childrenRequests = new ArrayList<>();

        PsiMethod[] psiMethods = psiClass.getMethods();
        for (PsiMethod psiMethod : psiMethods) {
            String path = "/";
            List<HttpMethod> methods = new ArrayList<>();

            PsiAnnotation[] annotations = RestUtil.getMethodAnnotations(psiMethod).toArray(new PsiAnnotation[0]);
            for (PsiAnnotation annotation : annotations) {
                Control controlPath = Control.getPathByQualifiedName(annotation.getQualifiedName());
                if (controlPath != null) {
                    List<JvmAnnotationAttribute> attributes = annotation.getAttributes();
                    Object value = RestUtil.getAttributeValue(attributes.get(0).getAttributeValue());
                    if (value != null) {
                        path = (String) value;
                    }
                }

                JarsHttpMethodAnnotation jaxrs = JarsHttpMethodAnnotation.getByQualifiedName(
                        annotation.getQualifiedName()
                );
                if (jaxrs != null) {
                    methods.add(jaxrs.getMethod());
                }
            }

            for (HttpMethod method : methods) {
                String tempPath = path;
                if (!tempPath.startsWith("/")) {
                    tempPath = "/" + tempPath;
                }
                if (rootPath != null) {
                    tempPath = rootPath + tempPath;
                    if (!tempPath.startsWith("/")) {
                        tempPath = "/" + tempPath;
                    }
                    tempPath = tempPath.replaceAll("//", "/");
                }
                Request request = new Request(method, tempPath, psiMethod);
                childrenRequests.add(request);
            }
        }
        return childrenRequests;
    }

    @Nullable
    private static String getRootPathOfClass(@NotNull PsiClass psiClass) {
        PsiAnnotation psiAnnotation = RestUtil.getClassAnnotation(
                psiClass,
                Control.Path.getQualifiedName()
        );
        if (psiAnnotation != null) {
            return (String) RestUtil.getAttributeValue(psiAnnotation.getAttributes().get(0).getAttributeValue());
        }
        return null;
    }

    /**
     * 查找xml配置文件
     *
     * @param project project
     * @param module  module
     * @return xmlFile
     */
    @Nullable
    private static XmlFile findConfigXmlFile(@NotNull Project project, @NotNull Module module) {
        PsiFile[] files = FilenameIndex.getFilesByName(project, "applicationContext.xml", module.getModuleScope());
        for (PsiFile file : files) {
            if (file instanceof XmlFile) {
                return (XmlFile) file;
            }
        }
        return null;
    }

    @NotNull
    private static List<PsiClassBean> parseApplicationContextXml(@NotNull Project project, @NotNull Module module,
                                                                 @NotNull XmlFile applicationContext) {
        List<PsiClassBean> list = new ArrayList<>();
        if (applicationContext.getRootTag() == null) {
            return Collections.emptyList();
        }
        for (XmlTag xmlTag : applicationContext.getRootTag().getSubTags()) {
            if (!"jaxrs:server".equals(xmlTag.getName())) {
                continue;
            }
            String rootPath = xmlTag.getAttributeValue("address");
            String serviceClass = xmlTag.getAttributeValue("serviceClass");
            if (serviceClass == null) {
                continue;
            }
            serviceClass = serviceClass.substring(serviceClass.lastIndexOf(".") + 1);
            Collection<PsiClass> psiClasses = JavaShortClassNameIndex.getInstance().get(serviceClass, project, module.getModuleScope());
            for (PsiClass psiClass : psiClasses) {
                list.add(new PsiClassBean(rootPath, psiClass));
            }
        }
        return list;
    }

    enum Control {

        /**
         * Javax.ws.rs.Path
         */
        Path("Path", "javax.ws.rs.Path");

        private final String name;
        private final String qualifiedName;

        Control(String name, String qualifiedName) {
            this.name = name;
            this.qualifiedName = qualifiedName;
        }

        @Nullable
        public static Control getPathByQualifiedName(String qualifiedName) {
            for (Control annotation : Control.values()) {
                if (annotation.getQualifiedName().equals(qualifiedName)) {
                    return annotation;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public String getQualifiedName() {
            return qualifiedName;
        }
    }

    private static class PsiClassBean {

        public String rootPath;
        public PsiClass psiClass;

        public PsiClassBean(String rootPath, PsiClass psiClass) {
            this.rootPath = rootPath;
            this.psiClass = psiClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PsiClassBean that = (PsiClassBean) o;
            String name = psiClass.getName();
            if (name == null) {
                return false;
            }
            return name.equals(that.psiClass.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(psiClass.getName());
        }
    }
}
