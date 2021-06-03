package com.ron.easydoc.basic.element;


import com.ron.easydoc.basic.config.GenConfig;
import lombok.Getter;
import lombok.Setter;
import com.ron.easydoc.basic.utils.StringUtils;
import org.springframework.util.CollectionUtils;


import java.util.List;

/**
 * @author ron
 * @date 2021年05月20日
 */
@Getter
@Setter
public class Member {

    private GenConfig genConfig;

    private List<String> imports;

    private Member parentMember;

    private List<Class> innerClass;

    private String packageName;

    private String name;

    private String type;

    private Comment comment;

    private List<Annotation> annotations;

    private List<String> modifier;

    public String getPackageName() {

        return packageName!=null?packageName:(parentMember!=null?parentMember.getPackageName():null);
    }

    public String getCommentName(){
        String apiName = "";
        Comment comment = getComment();
        if(comment != null && StringUtils.isNotEmpty(comment.getDescription())){
            apiName = comment.getDescription().split("\n", 2)[0];
        }

        return apiName;
    }

    public String getCommentDesc(){
        Comment comment = getComment();
        if(comment != null && StringUtils.isNotEmpty(comment.getDescription())){
            String[] strArr = comment.getDescription().split("\n", 2);
            return strArr.length > 1?strArr[1].trim():"";
        }

        return "";
    }


    public boolean ignore(){
        if(getAnnotationByName("Deprecated") != null){
            return true;
        }

        if(comment != null && comment.getDescription() != null){
           return comment.getDescription().contains("#ignore#");
        }

        return false;
    }

    public boolean isPublic() {
        return !CollectionUtils.isEmpty(modifier) && modifier.contains("public");
    }

    public boolean isNotPrivate() {
        return CollectionUtils.isEmpty(modifier) || !modifier.contains("private");
    }

    public Annotation getAnnotationByName(String name){
        List<Annotation> annotations = getAnnotations();

        if(!CollectionUtils.isEmpty(annotations)){
            for(Annotation annotation:annotations){
                if(annotation.getName().equals(name)){
                    return annotation;
                }
            }
        }

        return null;
    }

    public GenConfig getGenConfig() {
        if (genConfig == null) {
            return parentMember.getGenConfig();
        }
        return genConfig;
    }

    public List<String> getImports() {
        if (imports == null) {
            return parentMember.getImports();
        }
        return imports;
    }

    public List<Class> getInnerClass() {
        if (innerClass == null) {
            return parentMember.getInnerClass();
        }
        return innerClass;
    }

}
