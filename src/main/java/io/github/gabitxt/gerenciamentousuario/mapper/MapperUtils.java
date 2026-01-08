package io.github.gabitxt.gerenciamentousuario.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;

/**
 * Utilitário de conversão via reflection.
 */
public final class MapperUtils {

    private static final Logger log = LoggerFactory.getLogger(MapperUtils.class);

    private MapperUtils() {}

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object source, Class<T> targetClass) {
        if (source == null) {
            log.debug("Conversão ignorada: source é null");
            return null;
        }

        String sourceClassName = source.getClass().getSimpleName();
        String targetClassName = targetClass.getSimpleName();

        log.debug("Iniciando conversão: {} -> {}", sourceClassName, targetClassName);

        try {
            Method builderMethod = targetClass.getMethod("builder");
            Object builder = builderMethod.invoke(null);
            Class<?> builderClass = builder.getClass();

            // Suporta tanto classes normais quanto records
            if (source.getClass().isRecord()) {
                processRecordFields(source, targetClass, builder, builderClass);
            } else {
                processClassFields(source, targetClass, builder, builderClass);
            }

            T result = (T) builderClass.getMethod("build").invoke(builder);
            log.debug("Conversão concluída: {} -> {}", sourceClassName, targetClassName);
            return result;

        } catch (Exception e) {
            log.error("Falha ao converter {} -> {}: {}", sourceClassName, targetClassName, e.getMessage());
            throw new MappingException("Falha ao converter para " + targetClassName, e);
        }
    }

    private static void processRecordFields(Object source, Class<?> targetClass, Object builder, Class<?> builderClass) throws Exception {
        for (RecordComponent component : source.getClass().getRecordComponents()) {
            String name = component.getName();
            Method accessor = component.getAccessor();
            Object value = accessor.invoke(source);

            processField(name, value, targetClass, builder, builderClass);
        }
    }

    private static void processClassFields(Object source, Class<?> targetClass, Object builder, Class<?> builderClass) throws Exception {
        for (Field field : source.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            Object value = field.get(source);

            processField(name, value, targetClass, builder, builderClass);
        }
    }

    private static void processField(String name, Object value, Class<?> targetClass, Object builder, Class<?> builderClass) throws Exception {
        Class<?> targetFieldType = findFieldType(targetClass, name);
        if (targetFieldType == null) {
            log.trace("Campo '{}' não existe em {}, ignorando", name, targetClass.getSimpleName());
            return;
        }

        // Se o valor for um objeto complexo e os tipos forem diferentes, converte recursivamente
        if (value != null && !targetFieldType.isAssignableFrom(value.getClass())) {
            if (hasBuilder(targetFieldType)) {
                log.debug("Convertendo campo aninhado '{}': {} -> {}",
                        name, value.getClass().getSimpleName(), targetFieldType.getSimpleName());
                value = convert(value, targetFieldType);
            }
        }

        Method setter = findBuilderMethod(builderClass, name, value, targetFieldType);
        if (setter != null && value != null) {
            try {
                setter.invoke(builder, value);
                log.trace("Campo '{}' mapeado com sucesso", name);
            } catch (IllegalArgumentException e) {
                log.warn("Campo '{}': tipo incompatível. Esperado={}, Recebido={}",
                        name, setter.getParameterTypes()[0].getSimpleName(),
                        value.getClass().getSimpleName());
            }
        }
    }

    private static boolean hasBuilder(Class<?> clazz) {
        try {
            clazz.getMethod("builder");
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private static Class<?> findFieldType(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private static Method findBuilderMethod(Class<?> builderClass, String name, Object value, Class<?> expectedType) {
        for (Method method : builderClass.getMethods()) {
            if (method.getName().equals(name) && method.getParameterCount() == 1) {
                Class<?> paramType = method.getParameterTypes()[0];
                if (value != null && paramType.isAssignableFrom(value.getClass())) {
                    return method;
                }
                if (paramType.isAssignableFrom(expectedType)) {
                    return method;
                }
            }
        }
        return null;
    }
}
