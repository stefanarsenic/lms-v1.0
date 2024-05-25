package rs.ac.singidunum.novisad.server.generic;

import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityDtoMapper {
    // Convert entity to DTO
    public static <T, D> D convertToDto(T entity, Class<D> dtoClass) throws IllegalAccessException, InstantiationException {
        D dto = dtoClass.newInstance();

        Set<Field> entityFields = ReflectionUtils.getAllFields(entity.getClass());
        Set<Field> dtoFields = ReflectionUtils.getAllFields(dtoClass);

        Map<String, Field> dtoFieldMap = new HashMap<>();
        for (Field dtoField : dtoFields) {
            dtoFieldMap.put(dtoField.getName(), dtoField);
        }

        for (Field entityField : entityFields) {
            entityField.setAccessible(true);
            Field correspondingDtoField = dtoFieldMap.get(entityField.getName());
            if (correspondingDtoField != null && entityField.getType().equals(correspondingDtoField.getType())) {
                correspondingDtoField.setAccessible(true);
                correspondingDtoField.set(dto, entityField.get(entity));
            }
        }

        return dto;
    }

    // Convert DTO to entity
    public static <D, T> T convertToEntity(D dto, Class<T> entityClass) throws IllegalAccessException, InstantiationException {
        T entity = entityClass.newInstance();

        Set<Field> dtoFields = ReflectionUtils.getAllFields(dto.getClass());
        Set<Field> entityFields = ReflectionUtils.getAllFields(entityClass);

        Map<String, Field> entityFieldMap = new HashMap<>();
        for (Field entityField : entityFields) {
            entityFieldMap.put(entityField.getName(), entityField);
        }

        for (Field dtoField : dtoFields) {
            dtoField.setAccessible(true);
            Field correspondingEntityField = entityFieldMap.get(dtoField.getName());
            if (correspondingEntityField != null && dtoField.getType().equals(correspondingEntityField.getType())) {
                correspondingEntityField.setAccessible(true);
                correspondingEntityField.set(entity, dtoField.get(dto));
            }
        }

        return entity;
    }
}
