package network.base.com.basedialog.picker.common.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author : cuu
 * date    : 2019/4/8
 * desc    :
 */
public class JavaBean implements Serializable {
    private static final long serialVersionUID = -6111323241670458039L;

    /**
     * 反射出所有字段值
     */
    @Override
    public String toString() {
        ArrayList<Field> list = new ArrayList<>();
        Class<?> clazz = getClass();
        //得到自身的所有字段
        list.addAll(Arrays.asList(clazz.getDeclaredFields()));
        StringBuilder sb = new StringBuilder();
        while (clazz != Object.class) {
            //得到继承自父类的字段
            clazz = clazz.getSuperclass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int modifier = field.getModifiers();
                if (Modifier.isPublic(modifier) || Modifier.isProtected(modifier)) {
                    list.add(field);
                }
            }
        }
        Field[] fields = list.toArray(new Field[list.size()]);
        for (Field field : fields) {
            String fieldName = field.getName();
            try {
                Object obj = field.get(this);
                sb.append(fieldName);
                sb.append("=");
                sb.append(obj);
                sb.append("\n");
            } catch (IllegalAccessException e) {

            }
        }
        return sb.toString();
    }

}
