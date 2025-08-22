package springDemo;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleContext {
    private Map<Class<?>, Object> beans = new HashMap<>();

    public void registerBean(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructors()[0];

            if (constructor.getParameterCount() == 0) {
                Object instance = constructor.newInstance();
                beans.put(clazz, instance);
            } else {
                Class<?>[] paramTypes = constructor.getParameterTypes();
                Object[] params = new Object[paramTypes.length];

                for (int i = 0; i < paramTypes.length; i++) {
                    if (!beans.containsKey(paramTypes[i])) {
                        registerBean(paramTypes[i]);
                    }
                    params[i] = beans.get(paramTypes[i]);
                }
                Object instance = constructor.newInstance(params);
                beans.put(clazz, instance);
            }
        } catch (Exception e) {
            throw new RuntimeException("ERROR! Bean creation error" + clazz, e);
        }
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }
}
