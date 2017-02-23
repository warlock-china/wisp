package cn.com.warlock.wisp.test.support.utils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestUtils {

    protected static Log LOG = LogFactory.getLog(TestUtils.class);

    private static final URL CLASS_PATH = Thread.currentThread().getContextClassLoader().getResource("");
    private static final String CLASSES = "/classes/";
    private static Map<Class, Object> instanceMap = new HashMap<Class, Object>();
    private static Map<Class, Map<Type, Map<Boolean, Object>>> colletionInstanceMap =
            new HashMap<Class, Map<Type, Map<Boolean, Object>>>();

    public static void testAllClassUnderPackage(String pack) {
        for (Class<?> clazz : getClassList(pack)) {
            testGetSet(clazz);
            testToString(clazz);
            testHashCodeAndEquals(clazz);
        }
    }

    private static List<Class> getClassList(String pack) {
        List<Class> classList = null;
        String packagePath = pack.replace(".", "/");
        if (CLASS_PATH != null) {
            String type = CLASS_PATH.getProtocol();
            if (type.equals("file")) {
                File file = new File(CLASS_PATH.getPath());
                String path = file.getParent() + CLASSES + packagePath;
                classList = getClassListByFilter(new File(path), pack);
            }
        }
        return classList;
    }

    private static List<Class> getClassListByFilter(File file, String pack) {
        List<Class> myClassList = new ArrayList<>();
        File[] childFiles = file.listFiles();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    myClassList.addAll(getClassListByFilter(childFile, pack + "." + childFile.getName()));
                } else {
                    String className = childFile.getName();

                    // only class test
                    if (className.contains(".class")) {
                        className = pack + "." + className.substring(0, className.length() - 6);
                        try {
                            Class clazz = Class.forName(className);
                            myClassList.add(clazz);
                        } catch (ClassNotFoundException e) {
                            LOG.info("getClassListByFilter Got illegal class, do nothing.");
                        }
                    }
                }
            }
        }
        return myClassList;
    }

    private static void testGetSet(Class<?> clz) {
        try {
            if (clz.getName().contains("mock")) {
                return;
            }
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                Object obj = clz.newInstance();
                try {
                    field.setAccessible(true);
                    String getMethod =
                            "get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                    String setMethod =
                            "set" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
                    Object value = clz.getDeclaredMethod(getMethod).invoke(obj);
                    clz.getDeclaredMethod(setMethod, field.getType()).invoke(obj, value);
                } catch (Throwable e) {
                    LOG.warn("testGetSet Catch exception, do nothing." + clz.getName());
                }
            }
        } catch (Throwable e) {
            LOG.warn("testGetSet Catch exception, do nothing." + clz.getName());
        }
        try {
            // call fields
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    // System.out.println(field.get(clz.newInstance()));
                } catch (Throwable e) {
                    LOG.warn("testGetSet Catch exception, do nothing." + clz.getName());
                }
            }
        } catch (Throwable e) {
            LOG.warn("testGetSet Catch exception, do nothing." + clz.getName());
        }
    }

    /**
     * test for toString
     *
     * @param clz
     */
    private static void testToString(Class<?> clz) {
        try {

            Method[] methods = clz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals("toString")) {
                    Method toStringMethod = clz.getMethod("toString", new Class[] {});
                    if (toStringMethod != null) {
                        try {
                            toStringMethod.invoke(clz.newInstance());
                        } catch (Exception e) {

                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.warn("testToString Catch exception, do nothing." + clz.getName());
        }
    }

    private static void testHashCodeAndEquals(Class<?> clz) {
        try {
            Object original = clz.newInstance();
            Object dupe = BeanUtils.cloneBean(original);
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if ((field.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) == 0) {
                    try {
                        Object defaultValue = getValue(field.get(original), field, true);
                        field.set(original, defaultValue);
                        field.set(dupe, defaultValue);
                    } catch (Throwable e) {
                        // System.out.println(e.getMessage());
                    }
                }
            }
            original.equals(original);
            original.equals(new Object());
            original.equals(null);
            original.equals(dupe);
            dupe.hashCode();
            // change value one by one
            for (Field field : fields) {
                Object orginField = field.get(original);
                Object dupeField = field.get(dupe);
                if ((field.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) != 0) {
                    continue;
                }
                try {
                    // set different value
                    field.set(dupe, getValue(field.get(original), field, false));
                    original.equals(dupe);
                } catch (Throwable e) {
                    LOG.warn("equals Catch exception, do nothing." + clz.getName());
                }
                // orgin 的字段set null
                try {
                    field.set(original, null);
                    original.equals(dupe);
                    dupe.equals(original);
                    dupe.hashCode();
                } catch (Throwable e) {
                    LOG.warn("equals Catch exception, do nothing." + clz.getName());
                }
                // dupe 的字段set null
                try {
                    field.set(dupe, null);
                    original.equals(dupe);
                    dupe.hashCode();
                } catch (Throwable e) {
                    LOG.warn("equals Catch exception, do nothing." + clz.getName());
                }
                // set back
                try {
                    field.set(original, orginField);
                    field.set(dupe, dupeField);
                } catch (Throwable e) {
                    LOG.warn("equals Catch exception, do nothing." + clz.getName());
                }
            }
        } catch (Throwable e) {
            LOG.warn("testHashCodeAndEquals Catch exception, do nothing." + clz.getName());
        }
    }

    private static Object getValue(Object defaultValue, Field field,
                                   boolean isDefault) {
        if (field.getType().equals(Byte.class)
                || field.getType().equals(byte.class)
                || field.getType().equals(Short.class)
                || field.getType().equals(short.class)
                || field.getType().equals(Integer.class)
                || field.getType().equals(int.class)
                || field.getType().equals(Float.class)
                || field.getType().equals(float.class)
                || field.getType().equals(Double.class)
                || field.getType().equals(double.class)) {
            return isDefault ? 1 : 2;
        }
        if (field.getType().equals(Long.class)
                || field.getType().equals(long.class)) {
            return isDefault ? 1L : 2L;
        }

        if (field.getType().equals(Boolean.class)
                || field.getType().equals(boolean.class)) {
            return isDefault;
        }
        if (field.getType().equals(String.class)) {
            return isDefault ? "default" : "fake";
        }
        if (field.getType().equals(char.class)) {
            return isDefault ? 'a' : 'b';
        }

        Class clz = field.getType();
        if (clz.isAssignableFrom(List.class)) {
            clz = ArrayList.class;
            return getCollectionValueOfClass(clz, field.getGenericType(), isDefault);
        } else if (clz.isAssignableFrom(Set.class)) {
            clz = HashSet.class;
            return getCollectionValueOfClass(clz, field.getGenericType(), isDefault);
        } else if (clz.isAssignableFrom(Map.class)) {
            clz = HashMap.class;
            return getCollectionValueOfClass(clz, field.getGenericType(), isDefault);
        }

        return getValueOfClass(clz, isDefault);
    }

    private static Object getCollectionValueOfClass(Class clz, Type genericType, boolean isDefault) {
        try {
            if (!colletionInstanceMap.containsKey(clz)) {
                colletionInstanceMap.put(clz, new HashMap<Type, Map<Boolean, Object>>());
            }
            if (!colletionInstanceMap.get(clz).containsKey(genericType)) {
                colletionInstanceMap.get(clz).put(genericType, new HashMap<Boolean, Object>());
            }
            if (!colletionInstanceMap.get(clz).get(genericType).containsKey(isDefault)) {
                colletionInstanceMap.get(clz).get(genericType).put(isDefault,
                        getDefaultCollectionValueOfClass(clz, genericType, isDefault));

            }
            return colletionInstanceMap.get(clz).get(genericType).get(isDefault);
        } catch (Exception e) {
            LOG.warn("getCollectionValueOfClass Catch exception, do nothing.");
        }
        return null;
    }

    private static Object getDefaultCollectionValueOfClass(Class clz, Type genericType, boolean isDefault) {
        try {
            Collection object = (Collection) clz.newInstance();
            object.add(getDefaultValue(genericType, isDefault));
            return object;
        } catch (Exception e) {
            LOG.warn("getDefaultCollectionValueOfClass Catch exception, do nothing.");
        }
        return null;
    }

    private static Object getDefaultValue(Type genericType, boolean isDefault) {
        String type = genericType.getClass().getSimpleName();

        if (type.contains("Byte")
                || type.contains("Short")
                || type.contains("Integer")
                || type.contains("Float")
                || type.contains("Double")) {
            return isDefault ? 1 : 2;
        }
        if (type.contains("Long")) {
            return isDefault ? 1L : 2L;
        }
        if (type.contains("Boolean")) {
            return isDefault;
        }
        if (type.contains("String")) {
            return isDefault ? "default" : "fake";
        }
        return null;
    }

    /**
     * @param clz
     * @param isDefault
     *
     * @return
     */
    private static Object getValueOfClass(Class clz, boolean isDefault) {
        try {
            if (!isDefault) {
                return null;
            } else {

                if (!instanceMap.containsKey(clz)) {
                    instanceMap.put(clz, clz.newInstance());
                }
                return instanceMap.get(clz);
            }
        } catch (Exception e) {
            LOG.warn("getValue Catch exception, do nothing.");
        }
        return null;
    }
}
