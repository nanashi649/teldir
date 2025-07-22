package jp.co.ridi.teldir.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;

public class BeanUtil {

    public static <T> T createProperties(Object src, Class<T> clazz) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            getBeanUtilsBean().copyProperties(t, src);
            return t;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T createProperties(Map<String, ? extends Object> src, Class<T> clazz) {
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            getBeanUtilsBean().populate(t, src);
            return t;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object copyProperties(Object src, Object dest) {
        try {
            getBeanUtilsBean().copyProperties(dest, src);
            return dest;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object copyProperties(Map<String, ? extends Object> src, Object dest) {
        try {
            getBeanUtilsBean().populate(dest, src);
            return dest;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> describe(Object bean) {
        try {
            return getBeanUtilsBean().describe(bean);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object cloneBean(Object bean) {
        try {
            return getBeanUtilsBean().cloneBean(bean);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static BeanUtilsBean getBeanUtilsBean() {
        BeanUtilsBean b = new BeanUtilsBean(new ConvertUtilsBean(), BeanUtilsBean.getInstance().getPropertyUtils());
        // defaultNullをtrueに設定
        b.getConvertUtils().register(false, true, 0);
        // LocalDate用Converter
        b.getConvertUtils().register(new LocalDateConverter(), LocalDate.class);
        // LocalDateTime用Converter
        b.getConvertUtils().register(new LocalDateTimeConverter(), LocalDateTime.class);
        // Date用Converter
        b.getConvertUtils().register(new DateConverter(), Date.class);
        return b;
    }

}
